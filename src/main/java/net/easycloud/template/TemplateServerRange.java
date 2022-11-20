package net.easycloud.template;

public class TemplateServerRange {

    private int minServers;
    private int maxServers;

    public TemplateServerRange(int minServers, int maxServers) {
        this.minServers = minServers;
        this.maxServers = maxServers;
    }

    public int getMinServers() {
        return minServers;
    }

    public int getMaxServers() {
        return maxServers;
    }

    @Override
    public String toString() {
        return "TemplateServerRange{" +
                "minServers=" + minServers +
                ", maxServers=" + maxServers +
                '}';
    }
}
