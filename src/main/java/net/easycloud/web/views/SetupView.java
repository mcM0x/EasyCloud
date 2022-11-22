package net.easycloud.web.views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.web.auth.WebAccountManager;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SetupView implements Handler {
    private WebAccountManager webAccountManager;

    public SetupView(WebAccountManager webAccountManager) {
        this.webAccountManager = webAccountManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        Map<String, Object> model = new HashMap<>();

        model.put("time", new Date(System.currentTimeMillis()));
        model.put("error", context.queryParamMap().containsKey("error"));
        model.put("message", context.queryParam("message"));

        context.render("/web/setup.html", model);

    }
}
