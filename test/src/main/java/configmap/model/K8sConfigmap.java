package configmap.model;

public class K8sConfigmap {
    private String apiVersion;
    private String kind;
    private Metadata metadata;
    private Data data;

    public K8sConfigmap(String apiVersion, String kind, Metadata metadata, Data data) {
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.data = data;
    }

    public K8sConfigmap() {}

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void printInformation() {
        System.out.println("apiVersion: " + this.apiVersion);
        System.out.println("kind: " + this.kind);
        System.out.println("Metadata: ");
        metadata.printInformation();
        data.printInformation();
    }
}

