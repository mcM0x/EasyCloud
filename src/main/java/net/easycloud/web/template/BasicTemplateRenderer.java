package net.easycloud.web.template;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BasicTemplateRenderer implements FileRenderer {

    private TemplateLoader templateManage = new TemplateLoader.ClasspathTemplateLoader();


    @Override
    public String render(@NotNull String filePath, @NotNull Map<String, Object> model, Context ctx) throws Exception {
        Template template = templateManage.load(filePath);
        TemplateContext context = new TemplateContext();
        for (String s : model.keySet()) {
            context.set(s, model.get(s));
        }
        return template.render(context);
    }
}
