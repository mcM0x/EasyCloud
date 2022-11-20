package net.easycloud.session.packet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PacketManager {

    private List<Class<? extends Packet>> packetList = new CopyOnWriteArrayList<>();

    public void registerPacket(Class<? extends Packet> packet) {
        packetList.add(packet);
    }

    public int getPacketId(Class<? extends Packet> packet) {
        return packetList.indexOf(packet);
    }

    public boolean existsPacketById(int id){
        return getPacketById(id) != null;
    }


    public Class<? extends Packet> getPacketById(int packetId) {
        return packetList.get(packetId);
    }

}
