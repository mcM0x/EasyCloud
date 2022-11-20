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
            templates = new ArrayList<Template>();

            templates.add(TemplateFactory.create("lobby",
                    TemplateServerRangeFactory.create(1, 10),
                    TemplateMemoryRangeFactory.create(128, 512),
                    0.7f
            ));


            templates.add(TemplateFactory.create("proxy",
                    TemplateServerRangeFactory.create(1, 1),
                    TemplateMemoryRangeFactory.create(128, 512),
                    1.1f
            ));

            templateFile.createNewFile();
            FileWriter writer = new FileWriter(templateFile);
            GSON.toJson(templates, writer);
            writer.close();

        } else {
            FileReader json = new FileReader(templateFile);
            templates = List.of(GSON.fromJson(json, Template[].class));

            json.close();


            System.out.println("laoded " + this.templates.size() + " templates");
            for (Template template : templates) {
                System.out.println(template);
            }

        }

    }

    public List<Template> getTemplates() {
        return templates;
    }

}
