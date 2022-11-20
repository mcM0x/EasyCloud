package net.easycloud;

import net.easycloud.server.CloudServer;
import net.easycloud.server.factory.CloudServerFactory;
import net.easycloud.session.packet.PacketManager;
import net.easycloud.session.packet.factory.PacketManagerFactory;
import net.easycloud.session.packet.list.HelloPacket;

import java.io.IOException;

public class CloudBootstrap {

    private CloudServer cloudServer;
    private PacketManager packetManager;

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
        this.packetManager.registerPacket(HelloPacket.class);
    }
}
