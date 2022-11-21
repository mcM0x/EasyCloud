package net.easycloud.gameserver.factory;

import net.easycloud.gameserver.GameServerManager;

public class GameServerManagerFactory {

    public static GameServerManager create(){
        return new GameServerManager();
    }

}
