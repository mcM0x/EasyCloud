package net.easycloud;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.JavalinRenderer;
import net.easycloud.gameserver.GameServerManager;
import net.easycloud.gameserver.factory.GameServerManagerFactory;
import net.easycloud.listener.PingListener;
import net.easycloud.listener.RegisterPacketListener;
import net.easycloud.packet.PacketManager;
import net.easycloud.packet.factory.PacketManagerFactory;
import net.easycloud.packet.list.JsonPacket;
import net.easycloud.server.CloudServer;
import net.easycloud.gameserver.creator.ServerCreator;
import net.easycloud.server.factory.CloudServerFactory;
import net.easycloud.session.packet.SinglePacketListener;
import net.easycloud.template.TemplateManager;
import net.easycloud.template.factory.TemplateManagerFactory;
import net.easycloud.util.FileUtil;
import net.easycloud.web.auth.SimpleWebAuthManager;
import net.easycloud.web.auth.WebAccountManager;
import net.easycloud.web.auth.factory.WebAccountManagerFactory;
import net.easycloud.web.rest.LoginRest;
import net.easycloud.web.rest.SetupRest;
import net.easycloud.web.rest.TemplateCreateRest;
import net.easycloud.web.rest.TemplateUploadRest;
import net.easycloud.web.template.BasicTemplateRenderer;
import net.easycloud.web.views.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CloudBootstrap {

    private CloudServer cloudServer;
    private PacketManager packetManager;
    private TemplateManager templateManager;
    private GameServerManager gameServerManager;
    private WebAccountManager webAccountManager;

    private List<SinglePacketListener> packetListenerList = new CopyOnWriteArrayList<>();


    public void createServer() throws IOException {

        this.cloudServer = CloudServerFactory.create(this.packetManager);

    }

    public void start() {
        new Thread(this.cloudServer, "APIServer-Thread").start();
    }

    public void stop() {
        this.cloudServer.stop();
    }

    public void setupPackets() {
        this.packetManager = PacketManagerFactory.create();
        this.packetManager.setGlobalPacketListener((socket, packet) -> {


            if (packet instanceof JsonPacket<?>) {
                JsonPacket<?> jsonPacket = (JsonPacket<?>) packet;

                for (SinglePacketListener singlePacketListener : packetListenerList) {
                    if (singlePacketListener.listenTo() == this.packetManager.getPacketId(packet.getClass())) {
                        singlePacketListener.listen(cloudServer.getSessionMap().get(socket), jsonPacket);
                    }
                }

            }

        });

        this.addSinglePacketListener(new PingListener(this.packetManager));
        this.addSinglePacketListener(new RegisterPacketListener(this.gameServerManager, this.packetManager));

    }

    public void addSinglePacketListener(SinglePacketListener listener) {
        packetListenerList.add(listener);
    }

    public void setupTemplates() {
        this.templateManager = TemplateManagerFactory.create();
        try {
            this.templateManager.loadTemplates();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startServerCreator() {
        new Thread(new ServerCreator(templateManager, gameServerManager, cloudServer)).start();
    }

    public void setupGameServerManager() {
        this.gameServerManager = GameServerManagerFactory.create();
    }

    public void cleanTmpDirectory() {
        File file = new File("tmp");
        //del file if exists
        if (file.exists()) {
            System.out.println("cleaning tmp");
            FileUtil.deleteDirectory(file);
        }
        file.mkdirs();
    }

    public void startWebInterface() {
        Javalin app = Javalin.create();

        this.webAccountManager = WebAccountManagerFactory.create();

        SimpleWebAuthManager authManager = new SimpleWebAuthManager();
        app.updateConfig(config -> {
            config.staticFiles.add("/web/static/", Location.CLASSPATH);
            config.accessManager(authManager);
        });


        JavalinRenderer.register(new BasicTemplateRenderer(), ".html");

        app.get("/", new LoginView(webAccountManager, authManager));
        app.get("/dashboard", new DashboardView(templateManager, gameServerManager));
        app.get("/templates", new TemplateView(templateManager));
        app.get("/templates/create", new CreateTemplateView(templateManager));
        app.get("/templates/upload", new UploadSpigotFileView());

        app.get("/setup", new SetupView(webAccountManager));

        app.post("/rest/login", new LoginRest(webAccountManager, authManager));
        app.post("/rest/setup", new SetupRest(webAccountManager));
        app.post("/rest/templates/upload", new TemplateUploadRest());
        app.post("/rest/templates/create", new TemplateCreateRest(templateManager));

        app.start(42069);


        System.out.println("starting WebInterface at *:42069");

    }
}
