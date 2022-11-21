package net.easycloud.gameserver.creator;

import net.easycloud.gameserver.GameServer;
import net.easycloud.gameserver.GameServerManager;
import net.easycloud.gameserver.factory.GameServerFactory;
import net.easycloud.server.CloudServer;
import net.easycloud.template.Template;
import net.easycloud.template.TemplateManager;
import net.easycloud.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.UUID;

public class ServerCreator implements Runnable {

    private TemplateManager templateManager;
    private GameServerManager gameServerManager;
    private CloudServer server;

    public ServerCreator(TemplateManager templateManager, GameServerManager gameServerManager, CloudServer server) {
        this.templateManager = templateManager;
        this.gameServerManager = gameServerManager;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (server.isRunning()) {

            for (Template template : this.templateManager.getTemplates()) {
                List<GameServer> gameServers = gameServerManager.getGameServersByTemplate(template);

                if (template.getServerRange().getMinServers() > gameServers.size()) {
                    startServer(template);
                }

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void startServer(Template template) {
        GameServer gameServer = GameServerFactory.create(template, UUID.randomUUID().toString(), "127.0.0.1", randomPort(), 100);
        System.out.println("starting server " + gameServer);
        gameServerManager.addGameServer(gameServer);

        //TODO copy template directory to tmp folder and start server.jar

        File targetDir = new File("tmp/" + gameServer.getName());
        File templateDir = new File("templates/" + template.getName());

        try {
            FileUtil.copyDirectory(templateDir.getAbsolutePath(), targetDir.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private int randomPort() {

        try (ServerSocket ss = new ServerSocket()) {
            return ss.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
