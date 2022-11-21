package net.easycloud.listener;

import net.easycloud.gameserver.GameServer;
import net.easycloud.gameserver.GameServerManager;
import net.easycloud.packet.PacketManager;
import net.easycloud.packet.list.JsonPacket;
import net.easycloud.packet.list.RegisterPacket;
import net.easycloud.packet.list.payload.RegisterPacketPayload;
import net.easycloud.session.CloudSession;
import net.easycloud.session.packet.SinglePacketListener;

public class RegisterPacketListener implements SinglePacketListener {
    private GameServerManager gameServerManager;
    private PacketManager packetManager;

    public RegisterPacketListener(GameServerManager gameServerManager, PacketManager packetManager) {
        this.gameServerManager = gameServerManager;
        this.packetManager = packetManager;
    }

    @Override
    public void listen(CloudSession session, JsonPacket<?> packet) {

        RegisterPacketPayload payload = (RegisterPacketPayload) packet.getPayload();

        for (GameServer gameServer : this.gameServerManager.getGameServers()) {
            if (gameServer.getName().equals(payload.getName())) {
                gameServer.setSession(session);
            }
        }


    }

    @Override
    public int listenTo() {
        return this.packetManager.getPacketId(RegisterPacket.class);
    }
}
