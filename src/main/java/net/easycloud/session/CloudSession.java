package net.easycloud.session;

import net.easycloud.packet.list.JsonPacket;
import net.easycloud.session.runner.CloudSessionRunner;

import java.io.IOException;
import java.net.Socket;

public class CloudSession {
    private Socket socket;

    private CloudSessionRunner runner;

    private int sessionId;

    public CloudSession(Socket socket, CloudSessionRunner runner, int sessionId) {
        this.socket = socket;
        this.runner = runner;


        this.sessionId = sessionId;
    }

    public void close() throws IOException {
        this.runner.close();
        this.sessionId = -1;
        this.socket.close();
        this.socket = null;

    }

    public void sendPacket(JsonPacket<?> packet) throws IOException {

        this.runner.sendPacket(packet);
    }

    public void listen() {
        new Thread(this.runner, "Session-Worker-" + this.sessionId).start();
    }

    @Override
    public String toString() {
        return "CloudSession{" +
                "socket=" + socket +
                ", sessionId=" + sessionId +
                '}';
    }
}
