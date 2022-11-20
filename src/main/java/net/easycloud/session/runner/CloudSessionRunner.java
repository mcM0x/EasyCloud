package net.easycloud.session.runner;

import net.easycloud.session.packet.Packet;
import net.easycloud.session.packet.PacketManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CloudSessionRunner implements Runnable {

    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    private boolean isRunning = false;

    private Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();

    private PacketManager packetManager;

    public CloudSessionRunner(DataOutputStream outputStream, DataInputStream inputStream, PacketManager packetManager) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.packetManager = packetManager;
    }

    @Override
    public void run() {
        this.isRunning = true;
        try {
            while (this.isRunning) {

                if (!this.packetQueue.isEmpty()) {
                    Packet poll = this.packetQueue.poll();
                    int packetId = this.packetManager.getPacketId(poll.getClass());
                    this.outputStream.writeInt(packetId);
                    poll.write(this.outputStream);
                    this.outputStream.flush();
                }

                if (this.inputStream.available() > 0) {
                    int packetId = this.inputStream.readInt();
                    if (this.packetManager.existsPacketById(packetId)) {
                        Packet packet = this.packetManager.getPacketById(packetId).newInstance();
                        packet.read(this.inputStream);
                    }
                }

                Thread.sleep(5);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.isRunning = false;
    }

    public void sendPacket(Packet packet) {
        this.packetQueue.add(packet);
    }
}
