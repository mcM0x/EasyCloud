package net.easycloud.gameserver.creator;

import net.easycloud.gameserver.GameServer;
import net.easycloud.gameserver.GameServerManager;
import net.easycloud.gameserver.factory.GameServerFactory;
import net.easycloud.server.CloudServer;
import net.easycloud.template.Template;
import net.easycloud.template.TemplateManager;
import net.easycloud.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerCreator implements Runnable {

    private TemplateManager templateManager;
    private GameServerManager gameServerManager;
    private CloudServer server;

    private Executor executor = Executors.newCachedThreadPool();

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
                    executor.execute(() -> startServer(template));
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

        File targetDir = new File("tmp/" + gameServer.getName());
        File templateDir = new File("templates/" + template.getName());

        try {
            FileUtil.copyDirectory(templateDir.getAbsolutePath(), targetDir.getAbsolutePath());

            File serverFile = new File("templates/server.jar");
            Files.copy(serverFile.toPath(), new File(targetDir + "/server.jar").toPath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TODO start server.jar with given parameters
        try {
            Process p = new ProcessBuilder(("java -Dcom.mojang.eula.agree=true -jar server.jar --nogui -port " + gameServer.getPort()).split(" ")).directory(targetDir).start();
            System.out.println("starting server " + gameServer.getName() + " on port " + gameServer.getPort());
            p.waitFor();

            System.out.println("process finished - " + gameServer.getName());
            System.out.println("deleting...");
            FileUtil.deleteDirectory(targetDir);
            gameServerManager.removeGameServer(gameServer);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private int randomPort() {

        try (ServerSocket ss = new ServerSocket(0)) {
            return ss.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
