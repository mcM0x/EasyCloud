package net.easycloud.listener;

import net.easycloud.packet.PacketManager;
import net.easycloud.packet.list.JsonPacket;
import net.easycloud.packet.list.PingPacket;
import net.easycloud.session.CloudSession;
import net.easycloud.session.packet.SinglePacketListener;

public class PingListener implements SinglePacketListener {

    private PacketManager packetManager;

    public PingListener(PacketManager packetManager) {
        this.packetManager = packetManager;
    }

    @Override
    public void listen(CloudSession session, JsonPacket<?> packet) {
        Long payload = (Long) packet.getPayload();

        System.out.println("ping " + (System.currentTimeMillis() - payload) + "ms from session:" + session);

        session.setPing(payload);


    }

    @Override
    public int listenTo() {
        return packetManager.getPacketId(PingPacket.class);
    }
}
