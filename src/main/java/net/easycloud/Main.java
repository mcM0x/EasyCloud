package net.easycloud;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        CloudBootstrap bootstrap = new CloudBootstrap();

        bootstrap.setupPackets();
        bootstrap.createServer();


        bootstrap.start();

    }
}