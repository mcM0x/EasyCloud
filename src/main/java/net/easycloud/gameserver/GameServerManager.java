package net.easycloud.gameserver;

import net.easycloud.template.Template;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServerManager {

    private List<GameServer> gameServers = new CopyOnWriteArrayList<>();

    public void addGameServer(GameServer gameServer) {
        gameServers.add(gameServer);
    }

    public List<GameServer> getGameServers() {
        return gameServers;
    }

    public List<GameServer> getGameServersByTemplate(Template template) {
        List<GameServer> gameServers = new CopyOnWriteArrayList<>();
        for (GameServer gameServer : this.gameServers) {
            if (gameServer.getTemplate().equals(template)) {
                gameServers.add(gameServer);
            }
        }
        return gameServers;
    }

    public boolean isProxy(GameServer gameServer) {
        return gameServer.getTemplate().getName().equals("proxy");
    }

    public List<GameServer> getProxies() {
        List<GameServer> gameServers = new CopyOnWriteArrayList<>();
        for (GameServer gameServer : getGameServers()) {
            if (gameServer.getTemplate().getName().equals("proxy")) {
                gameServers.add(gameServer);
            }
        }
        return gameServers;
    }

    public void removeGameServer(GameServer gameServer) {
        gameServers.remove(gameServer);
    }
}
