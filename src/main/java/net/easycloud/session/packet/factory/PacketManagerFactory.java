package net.easycloud.session.packet.factory;

import net.easycloud.session.packet.PacketManager;

public class PacketManagerFactory {

    public static PacketManager create(){
        return new PacketManager();
    }

}
