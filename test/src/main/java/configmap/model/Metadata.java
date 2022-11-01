package configmap.model;

public class Metadata {
    private Labels labels;
    private String name;
    private String namespace;

    public Metadata(Labels labels, String name, String namespace) {
        this.labels = labels;
        this.name = name;
        this.namespace = namespace;
    }

    public Metadata() {}

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void printInformation() {
        System.out.println("labels: ");
        labels.printInformation();
        System.out.println("name: " + this.name);
        System.out.println("namespace: " + this.namespace);
    }
}
