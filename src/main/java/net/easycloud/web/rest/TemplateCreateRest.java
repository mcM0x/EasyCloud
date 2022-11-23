package net.easycloud.web.rest;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.easycloud.template.TemplateManager;
import net.easycloud.template.TemplateMemoryRange;
import net.easycloud.template.TemplateServerRange;
import net.easycloud.template.factory.TemplateFactory;
import org.jetbrains.annotations.NotNull;

public class TemplateCreateRest implements Handler {

    private TemplateManager templateManager;

    public TemplateCreateRest(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        System.out.println(context.formParamMap());
        if(validate(context)){
            System.out.println("saved");
            String name = context.formParam("inputName");
            int minServers = Integer.parseInt(context.formParam("inputMinServers"));
            int maxServers = Integer.parseInt(context.formParam("inputMaxServers"));
            int minRam = Integer.parseInt(context.formParam("inputMinRam"));
            int maxRam = Integer.parseInt(context.formParam("inputMaxRam"));
            float threshold = (Float.parseFloat(context.formParam("inputThreshold")) / 100.0f);

            templateManager.registerTemplate(TemplateFactory.create(

                    name,
                    new TemplateServerRange(minServers, maxServers),
                    new TemplateMemoryRange(minRam, maxRam),
                    threshold

            ));
        }
        context.redirect("/");



    }

    private boolean validate(Context context) {
        return context.formParamMap().containsKey("inputName") &&
                context.formParamMap().containsKey("inputMinServers") &&
                context.formParamMap().containsKey("inputMaxServers") &&
                context.formParamMap().containsKey("inputMinRam") &&
                context.formParamMap().containsKey("inputMaxRam") &&
                context.formParamMap().containsKey("inputThreshold");
    }
}
