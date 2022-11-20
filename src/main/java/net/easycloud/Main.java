package net.easycloud;

import net.easycloud.listener.PingListener;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        CloudBootstrap bootstrap = new CloudBootstrap();

        bootstrap.setupTemplates();
        bootstrap.setupPackets();


        bootstrap.createServer();


        bootstrap.start();

    }
}