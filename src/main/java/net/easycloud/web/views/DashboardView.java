package net.easycloud.web.views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.gameserver.GameServerManager;
import net.easycloud.template.TemplateManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DashboardView implements Handler {
    private TemplateManager templateManager;
    private GameServerManager gameServerManager;

    public DashboardView(TemplateManager templateManager, GameServerManager gameServerManager) {
        this.templateManager = templateManager;
        this.gameServerManager = gameServerManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();

        model.put("playerchart_labels", generatePlayerChartLabels());
        model.put("playerchart_data", generatePlayerChartData());
        model.put("templates_size", templateManager.getTemplates().size());
        model.put("gameservers_size", gameServerManager.getGameServers().size());

        boolean warning = false;
        boolean success = false;
        File serverFile = new File("templates/server.jar");

        if(!serverFile.exists()){
            warning = true;
            model.put("message", "You did not have uploaded your spigot.jar file! Go to the Templates Page and upload your spigot.jar");
        }

        if(!warning){
            success = true;
            model.put("message", "EasyCloud is up and running normaly.");
        }


        model.put("success", success);
        model.put("warning", warning);

        context.render("/web/dashboard.html", model);

    }

    private String generatePlayerChartData() {
        String s = "";

        for (int i = 120; i > 0; i--) {
            s += "" + ThreadLocalRandom.current().nextInt(60) + ",";
        }

        return s;
    }

    private String generatePlayerChartLabels() {

        String s = "";

        for (int i = 120; i > 0; i--) {
            s += "'-" + i + " Minutes' ,";
        }

        s = s.substring(0, s.length() - 1);

        return s;
    }
}
