package net.easycloud.web.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.easycloud.template.Template;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebAccountManager {

    public List<WebAccount> accounts = new CopyOnWriteArrayList<>();
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();

    public WebAccountManager() {
        load();
    }

    public void registerNewAccount(WebAccount account) {
        accounts.add(account);
        saveAccounts();
    }

    public List<WebAccount> getAccounts() {
        return accounts;
    }

    public WebAccount getAccountByName(String name) {
        for (WebAccount account : accounts) {
            if (account.getName().equalsIgnoreCase(name)) {
                return account;
            }
        }
        return null;
    }

    public boolean canLogin(String username, String password) {
        if (getAccountByName(username) != null) {
            return getAccountByName(username).getPassword().equals(password);
        }
        return false;
    }

    public void load() {
        File accountsFile = new File("accounts.json");

        if (!accountsFile.exists()) {

            try {
                accountsFile.createNewFile();

                FileWriter writer = new FileWriter(accountsFile);
                GSON.toJson(accounts, writer);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {

            FileReader json = new FileReader(accountsFile);

            accounts = new CopyOnWriteArrayList<>(List.of(GSON.fromJson(json, WebAccount[].class)));
            json.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void saveAccounts() {
        File accountsFile = new File("accounts.json");
        try {
            FileWriter writer = new FileWriter(accountsFile);

            GSON.toJson(accounts, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
