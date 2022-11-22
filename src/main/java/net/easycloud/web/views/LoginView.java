package net.easycloud.web.views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.web.auth.SimpleWebAuthManager;
import net.easycloud.web.auth.WebAccountManager;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginView implements Handler {

    private WebAccountManager accountManager;
    private SimpleWebAuthManager authManager;

    public LoginView(WebAccountManager accountManager, SimpleWebAuthManager authManager) {
        this.accountManager = accountManager;
        this.authManager = authManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        if (accountManager.getAccounts().isEmpty()) {
            context.redirect("/setup");
            return;
        }
        if (context.cookieStore().get("sessionId") != null) {
            String sessionId = context.cookieStore().get("sessionId");
            if (authManager.sessionExists(sessionId)) {

                context.redirect("/dashboard");
                return;
            }
        }

        Map<String, Object> model = new HashMap<>();

        model.put("time", new Date(System.currentTimeMillis()));
        model.put("error", context.queryParamMap().containsKey("error"));
        model.put("message", context.queryParam("message"));

        context.render("/web/login.html", model);

    }
}
