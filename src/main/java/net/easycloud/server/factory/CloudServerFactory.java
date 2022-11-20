package net.easycloud.server.factory;

import net.easycloud.packet.PacketManager;
import net.easycloud.server.CloudServer;

import java.io.IOException;
import java.net.ServerSocket;

public class CloudServerFactory {


    public static CloudServer create(String host, int port, PacketManager packetManager) throws IOException {
        return new CloudServer(new ServerSocket(), host, port, packetManager);
    }

    public static CloudServer create(PacketManager packetManager) throws IOException {
        return create("0.0.0.0", 8869, packetManager);
    }



}
