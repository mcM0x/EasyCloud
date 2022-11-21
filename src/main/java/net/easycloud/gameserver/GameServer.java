package net.easycloud.gameserver;

import net.easycloud.packet.Packet;
import net.easycloud.packet.list.JsonPacket;
import net.easycloud.session.CloudSession;
import net.easycloud.template.Template;

import java.io.IOException;

public class GameServer {

    private Template template;
    private String name;
    private String host;
    private int port;
    private int maxPlayers;
    private GameServerStatus status;

    private CloudSession session;

    public GameServer(Template template, String name, String host, int port, int maxPlayers, GameServerStatus status) {
        this.template = template;
        this.name = name;
        this.host = host;
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.status = status;
    }

    public Template getTemplate() {
        return template;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public GameServerStatus getStatus() {
        return status;
    }

    public void setSession(CloudSession session) {
        this.session = session;
    }

    public CloudSession getSession() {
        return session;
    }

    public void sendPacket(JsonPacket<?> packet) throws IOException {
        session.sendPacket(packet);
    }

    @Override
    public String toString() {
        return "GameServer{" +
                "template=" + template +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", maxPlayers=" + maxPlayers +
                ", status=" + status +
                '}';
    }
}
