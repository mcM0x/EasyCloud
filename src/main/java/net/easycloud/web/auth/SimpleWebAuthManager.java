package net.easycloud.web.auth;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import net.easycloud.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleWebAuthManager implements AccessManager {

    private final Map<String, WebAccount> sessionMap = new ConcurrentHashMap<>();

    public void manage(@NotNull Handler handler, Context context, @NotNull Set<? extends RouteRole> set) {
        String path = context.path().trim();
        System.out.println(path);
        if (path.equals("/") || path.startsWith("/rest/login") || path.startsWith("/setup") || path.startsWith("/rest/setup/")) {
            System.out.println("handle");
            try {
                handler.handle(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            if (context.cookieStore().get("sessionId") != null) {
                String sessionId = context.cookieStore().get("sessionId");
                if (sessionMap.containsKey(sessionId)) {
                    System.out.println("handled successful login with cookie");
                    try {
                        handler.handle(context);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
            }

            System.out.println("unable to login with cookie.. .redirect");

            context.redirect("/");
        }
    }

    public String generateSessionId() {
        return StringUtil.generateString(32);
    }

    public WebAccount getBySessionId(String s){
        return this.sessionMap.get(s);
    }

    public boolean sessionExists(String s){
        return this.sessionMap.containsKey(s);
    }

    public void setSessionId(String sessionId, WebAccount webAccount) {
        System.out.println("session id " + sessionId + " set for account " + webAccount.getName());
        this.sessionMap.put(sessionId, webAccount);
    }

}
