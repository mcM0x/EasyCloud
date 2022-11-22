package net.easycloud;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        CloudBootstrap bootstrap = new CloudBootstrap();

        bootstrap.cleanTmpDirectory();

        bootstrap.setupGameServerManager();
        bootstrap.setupTemplates();
        bootstrap.setupPackets();


        bootstrap.createServer();
        bootstrap.startServerCreator();

        bootstrap.startWebInterface();

        bootstrap.start();

    }
}