package net.easycloud;

import net.easycloud.packet.GlobalPacketListener;
import net.easycloud.packet.Packet;
import net.easycloud.packet.PacketManager;
import net.easycloud.packet.factory.PacketManagerFactory;
import net.easycloud.packet.list.HelloPacket;
import net.easycloud.packet.list.JsonPacket;
import net.easycloud.packet.list.PingPacket;
import net.easycloud.server.CloudServer;
import net.easycloud.server.factory.CloudServerFactory;
import net.easycloud.template.TemplateManager;
import net.easycloud.template.factory.TemplateManagerFactory;

import java.io.IOException;
import java.util.List;

public class CloudBootstrap {

    private CloudServer cloudServer;
    private PacketManager packetManager;
    private TemplateManager templateManager;

    public void createServer() throws IOException {

        this.cloudServer = CloudServerFactory.create(this.packetManager);

        new JsonPacket<>(List.of("hello world"));

    }

    public void start() {
        new Thread(this.cloudServer, "APIServer-Thread").start();
    }

    public void stop() {
        this.cloudServer.stop();
    }

    public void setupPackets() {
        this.packetManager = PacketManagerFactory.create();
        this.packetManager.setGlobalPacketListener(packet -> {

            System.out.println("incomming packet: " + packet.getClass().getSimpleName());

            if (packet instanceof JsonPacket<?>) {
                JsonPacket<?> jsonPacket = (JsonPacket<?>) packet;
                System.out.println("json:" + jsonPacket.toString());
            }

        });

    }

    public void setupTemplates() {
        this.templateManager = TemplateManagerFactory.create();
        try {
            this.templateManager.loadTemplates();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
