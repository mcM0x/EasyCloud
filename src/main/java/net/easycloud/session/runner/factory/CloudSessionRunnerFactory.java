package net.easycloud.session.runner.factory;

import net.easycloud.packet.PacketManager;
import net.easycloud.session.runner.CloudSessionRunner;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class CloudSessionRunnerFactory {

    public static CloudSessionRunner create(DataOutputStream outputStream, DataInputStream inputStream, PacketManager packetManager, Socket socket) {
        return new CloudSessionRunner(outputStream, inputStream, packetManager, socket);

    }

}
