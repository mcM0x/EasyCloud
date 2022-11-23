package net.easycloud.web.views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.template.TemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CreateTemplateView implements Handler {
    private TemplateManager templateManager;

    public CreateTemplateView(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();

        context.render("/web/templates_create.html", model);

    }
}
