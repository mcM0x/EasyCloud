package net.easycloud.session.runner.factory;

import net.easycloud.session.packet.PacketManager;
import net.easycloud.session.runner.CloudSessionRunner;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class CloudSessionRunnerFactory {

    public static CloudSessionRunner create(DataOutputStream outputStream, DataInputStream inputStream, PacketManager packetManager) {
        return new CloudSessionRunner(outputStream, inputStream, packetManager);

    }

}
