package net.easycloud;

import net.easycloud.gameserver.GameServerManager;
import net.easycloud.gameserver.factory.GameServerFactory;
import net.easycloud.gameserver.factory.GameServerManagerFactory;
import net.easycloud.listener.PingListener;
import net.easycloud.listener.RegisterPacketListener;
import net.easycloud.packet.PacketManager;
import net.easycloud.packet.factory.PacketManagerFactory;
import net.easycloud.packet.list.JsonPacket;
import net.easycloud.server.CloudServer;
import net.easycloud.gameserver.creator.ServerCreator;
import net.easycloud.server.factory.CloudServerFactory;
import net.easycloud.session.packet.SinglePacketListener;
import net.easycloud.template.TemplateManager;
import net.easycloud.template.factory.TemplateManagerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CloudBootstrap {

    private CloudServer cloudServer;
    private PacketManager packetManager;
    private TemplateManager templateManager;
    private GameServerManager gameServerManager;

    private List<SinglePacketListener> packetListenerList = new CopyOnWriteArrayList<>();

    public void createServer() throws IOException {

        this.cloudServer = CloudServerFactory.create(this.packetManager);

    }

    public void start() {
        new Thread(this.cloudServer, "APIServer-Thread").start();
    }

    public void stop() {
        this.cloudServer.stop();
    }

    public void setupPackets() {
        this.packetManager = PacketManagerFactory.create();
        this.packetManager.setGlobalPacketListener((socket, packet) -> {


            if (packet instanceof JsonPacket<?>) {
                JsonPacket<?> jsonPacket = (JsonPacket<?>) packet;

                for (SinglePacketListener singlePacketListener : packetListenerList) {
                    if (singlePacketListener.listenTo() == this.packetManager.getPacketId(packet.getClass())) {
                        singlePacketListener.listen(cloudServer.getSessionMap().get(socket), jsonPacket);
                    }
                }

            }

        });

        this.addSinglePacketListener(new PingListener(this.packetManager));
        this.addSinglePacketListener(new RegisterPacketListener(this.gameServerManager, this.packetManager));

    }

    public void addSinglePacketListener(SinglePacketListener listener) {
        packetListenerList.add(listener);
    }

    public void setupTemplates() {
        this.templateManager = TemplateManagerFactory.create();
        try {
            this.templateManager.loadTemplates();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startServerCreator() {
        new Thread(new ServerCreator(templateManager, gameServerManager, cloudServer)).start();
    }

    public void setupGameServerManager() {
        this.gameServerManager = GameServerManagerFactory.create();
    }

    public void cleanTmpDirectory() {
        File file = new File("tmp");
        //del file if exists
        if (file.exists()) {
            file.delete();
        }
    }
}
