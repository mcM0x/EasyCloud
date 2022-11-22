package net.easycloud.web.auth;

public class WebAccount {

    private String name;
    //TODO encrypt password
    private String password;

    public WebAccount(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

