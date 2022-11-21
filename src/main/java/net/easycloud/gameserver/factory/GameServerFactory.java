package net.easycloud.gameserver.factory;

import net.easycloud.gameserver.GameServer;
import net.easycloud.gameserver.GameServerStatus;
import net.easycloud.template.Template;

public class GameServerFactory {

    public static GameServer create(Template template, String name, String host, int port, int maxPlayers) {
        return new GameServer(template, name, host, port, maxPlayers, GameServerStatus.STARTING);
    }

}
