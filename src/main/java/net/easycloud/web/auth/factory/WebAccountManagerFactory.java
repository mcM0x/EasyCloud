package net.easycloud.web.auth.factory;

import net.easycloud.web.auth.WebAccountManager;

public class WebAccountManagerFactory {

    public static WebAccountManager create(){
        return new WebAccountManager();
    }

}
