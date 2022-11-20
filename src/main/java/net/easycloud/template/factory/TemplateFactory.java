package net.easycloud.template.factory;

import net.easycloud.template.Template;
import net.easycloud.template.TemplateMemoryRange;
import net.easycloud.template.TemplateServerRange;

public class TemplateFactory {

    public static Template create(String name, TemplateServerRange serverRange, TemplateMemoryRange range, float thresholdToStartANewServer) {
        return new Template(name, serverRange, range, thresholdToStartANewServer);
    }

}
