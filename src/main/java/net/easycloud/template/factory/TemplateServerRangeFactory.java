package net.easycloud.template.factory;

import net.easycloud.template.TemplateServerRange;

public class TemplateServerRangeFactory {

    public static TemplateServerRange create(int minServers, int maxServers) {
        return new TemplateServerRange(minServers, maxServers);

    }

}
