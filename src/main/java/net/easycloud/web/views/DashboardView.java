package net.easycloud.web.views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.gameserver.GameServerManager;
import net.easycloud.template.TemplateManager;
import org.jetbrains.annotations.NotNull;

public class DashboardView implements Handler {
    private GameServerManager gameServerManager;

    public DashboardView(TemplateManager templateManager, GameServerManager gameServerManager) {
        this.gameServerManager = gameServerManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

    }
}
