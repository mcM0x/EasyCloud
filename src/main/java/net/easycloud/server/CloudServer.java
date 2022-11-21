package net.easycloud.server;

import net.easycloud.packet.PacketManager;
import net.easycloud.session.CloudSession;
import net.easycloud.session.factory.CloudSessionFactory;
import net.easycloud.session.runner.factory.CloudSessionRunnerFactory;
import net.easycloud.session.sessionid.SessionIdFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CloudServer implements Runnable {

    private ServerSocket serverSocket;
    private String host;

    private int port;
    private PacketManager packetManager;

    private boolean isRunning = false;

    private Map<Socket, CloudSession> sessionMap = new ConcurrentHashMap<>();

    public CloudServer(ServerSocket serverSocket, String host, int port, PacketManager packetManager) {
        this.serverSocket = serverSocket;
        this.host = host;
        this.port = port;
        this.packetManager = packetManager;
    }

    //bind the server
    private void bind() throws IOException {
        this.serverSocket.bind(new InetSocketAddress(this.host, this.port));
    }

    public void run() {
        this.isRunning = true;
        try {
            this.bind();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("open server socket at *:" + this.port);

        new Thread(() -> {

        }, "").start();

        while (isRunning) {

            try {
                //wait for new client
                Socket socket = this.serverSocket.accept();

                //create cloud session
                CloudSession session = CloudSessionFactory
                        .create(socket,
                                CloudSessionRunnerFactory.create(
                                        new DataOutputStream(socket.getOutputStream()),
                                        new DataInputStream(socket.getInputStream()),
                                        this.packetManager,
                                        socket
                                ),
                                SessionIdFactory.create(socket)
                        );

                sessionMap.put(socket, session);

                System.out.println("incomming connection to cloud... listening");

                //listen to session
                session.listen();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void stop() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void processSession(CloudSession session) {

    }

    public Map<Socket, CloudSession> getSessionMap() {
        return sessionMap;
    }
}
