package net.easycloud.template;

public class TemplateMemoryRange {

    private int minRam;
    private int maxRam;

    public TemplateMemoryRange(int minRam, int maxRam) {
        this.minRam = minRam;
        this.maxRam = maxRam;
    }

    public int getMaxRam() {
        return maxRam;
    }

    public int getMinRam() {
        return minRam;
    }

    @Override
    public String toString() {
        return "TemplateMemoryRange{" +
                "minRam=" + minRam +
                ", maxRam=" + maxRam +
                '}';
    }
}


