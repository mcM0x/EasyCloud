package net.easycloud.web.rest;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.web.auth.SimpleWebAuthManager;
import net.easycloud.web.auth.WebAccountManager;
import org.jetbrains.annotations.NotNull;

public class LoginRest implements Handler {

    private WebAccountManager webAccountManager;
    private SimpleWebAuthManager authManager;

    public LoginRest(WebAccountManager webAccountManager, SimpleWebAuthManager authManager) {
        this.webAccountManager = webAccountManager;
        this.authManager = authManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        if (context.formParamMap().containsKey("username") && context.formParamMap().containsKey("password")) {
            if (webAccountManager.canLogin(context.formParam("username"), context.formParam("password"))) {
                String s = authManager.generateSessionId();
                context.cookieStore().set("sessionId", s);
                authManager.setSessionId(s, webAccountManager.getAccountByName(context.formParam("username")));
                context.redirect("/dashboard");
                return;
            }
        }
        context.redirect("/?error=notfound&message=Account not found");

    }
}
