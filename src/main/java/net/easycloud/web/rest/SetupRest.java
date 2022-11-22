package net.easycloud.web.rest;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.web.auth.WebAccount;
import net.easycloud.web.auth.WebAccountManager;
import org.jetbrains.annotations.NotNull;

public class SetupRest implements Handler {
    private WebAccountManager webAccountManager;

    public SetupRest(WebAccountManager webAccountManager) {
        this.webAccountManager = webAccountManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        System.out.println(context.formParamMap());
        if (context.formParamMap().containsKey("username") && context.formParamMap().containsKey("password") && context.formParamMap().containsKey("passwordRepeat")) {
            webAccountManager.registerNewAccount(new WebAccount(context.formParam("username"), context.formParam("password")));
            System.out.println("account created");
            context.redirect("/dashboard");
            return;
        }
        System.out.println("redirect to error page");
        context.redirect("/setup/?error=notfound&message=Passwords does not match");

    }
}
