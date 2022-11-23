package net.easycloud.template;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.easycloud.template.factory.TemplateFactory;
import net.easycloud.template.factory.TemplateMemoryRangeFactory;
import net.easycloud.template.factory.TemplateServerRangeFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TemplateManager {

    private List<Template> templates = new CopyOnWriteArrayList<>();

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();

    public void loadTemplates() throws IOException {

        File templateFile = new File("templates.json");

        if (!templateFile.exists()) {

            templateFile.createNewFile();
            FileWriter writer = new FileWriter(templateFile);
            GSON.toJson(templates, writer);
            writer.close();

        } else {
            FileReader json = new FileReader(templateFile);
            templates = new CopyOnWriteArrayList<>(List.of(GSON.fromJson(json, Template[].class)));

            json.close();


            System.out.println("laoded " + this.templates.size() + " templates");
            for (Template template : templates) {
                System.out.println(template);
            }

        }

    }

    public void registerTemplate(Template template) throws IOException {
        File templateFile = new File("templates.json");

        new File("templates/" + template.getName()).mkdirs();

        templates.add(template);
        templateFile.createNewFile();
        FileWriter writer = new FileWriter(templateFile);
        GSON.toJson(templates, writer);
        writer.close();
    }

    public List<Template> getTemplates() {
        return templates;
    }

}
