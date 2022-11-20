package net.easycloud.template;

public class Template {

    private String name;
    private TemplateServerRange serverRange;
    private TemplateMemoryRange memoryRange;
    private float thresholdToStartANewServer;


    public Template(String name, TemplateServerRange serverRange, TemplateMemoryRange memoryRange, float thresholdToStartANewServer) {
        this.name = name;
        this.serverRange = serverRange;
        this.memoryRange = memoryRange;
        this.thresholdToStartANewServer = thresholdToStartANewServer;
    }

    public String getName() {
        return name;
    }

    public TemplateServerRange getServerRange() {
        return serverRange;
    }

    public TemplateMemoryRange getMemoryRange() {
        return memoryRange;
    }

    public float getThresholdToStartANewServer() {
        return thresholdToStartANewServer;
    }

    @Override
    public String toString() {
        return "Template{" +
                "name='" + name + '\'' +
                ", serverRange=" + serverRange +
                ", memoryRange=" + memoryRange +
                ", thresholdToStartANewServer=" + thresholdToStartANewServer +
                '}';
    }
}
