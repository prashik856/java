package configmap.model;

public class Data {
    String baseConfigFile;
    String logFile;

    public Data(String baseConfigFile, String logFile) {
        this.baseConfigFile = baseConfigFile;
        this.logFile = logFile;
    }

    public Data() {}

    public String getBaseConfigFile() {
        return baseConfigFile;
    }

    public void setBaseConfigFile(String baseConfigFile) {
        this.baseConfigFile = baseConfigFile;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public void printInformation() {
        System.out.println("baseConfig.yaml: " + baseConfigFile);
        System.out.println("log4j.yaml: " + logFile);
    }
}
