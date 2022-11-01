package configmap.model;

public class Labels {
    private String app;
    private String component;

    public Labels(String app, String component) {
        this.app = app;
        this.component = component;
    }

    public Labels() {}

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void printInformation() {
        System.out.println("app: " + this.app);
        System.out.println("component: " + this.component);
    }
}
