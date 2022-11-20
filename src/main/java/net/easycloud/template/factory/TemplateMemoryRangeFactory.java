package net.easycloud.template.factory;

import net.easycloud.template.TemplateMemoryRange;

public class TemplateMemoryRangeFactory {

    public static TemplateMemoryRange create(int minRam, int maxRam) {
        return new TemplateMemoryRange(minRam, maxRam);

    }

}
