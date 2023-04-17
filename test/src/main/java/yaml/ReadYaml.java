package yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ReadYaml {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        BaseYml baseYml = mapper.readValue(new File("src/main/resources/base-config.yaml"),
                BaseYml.class);
        System.out.println("Version: " + baseYml.getVersion());
        System.out.println("IOT: " + baseYml.getRequirements().getHttp().isExternal());
        System.out.println("Kafka: " + baseYml.getRequirements().getQueue().isKafka());
    }
}


class BaseYml {
    String contextRoot;
    String version;
    Self self;
    Requirements requirements;

    public BaseYml() {
    }

    public BaseYml(String contextRoot, String version, Self self, Requirements requirements) {
        this.contextRoot = contextRoot;
        this.version = version;
        this.self = self;
        this.requirements = requirements;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        return "BaseYml{" +
                "contextRoot='" + contextRoot + '\'' +
                ", version='" + version + '\'' +
                ", self=" + self +
                ", requirements=" + requirements +
                '}';
    }
}

class Requirements {
    Database database;
    Http http;
    Queue queue;
    Service service;

    public Requirements() {
    }

    public Requirements(Database database, Http http, Queue queue, Service service) {
        this.database = database;
        this.http = http;
        this.queue = queue;
        this.service = service;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Requirements{" +
                "database=" + database +
                ", http=" + http +
                ", queue=" + queue +
                ", service=" + service +
                '}';
    }
}

class Database {
    boolean shared;
    boolean tenant;

    public Database() {
    }

    public Database(boolean shared, boolean tenant) {
        this.shared = shared;
        this.tenant = tenant;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isTenant() {
        return tenant;
    }

    public void setTenant(boolean tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Database{" +
                "shared=" + shared +
                ", tenant=" + tenant +
                '}';
    }
}

class Http {
    boolean external;
    boolean idcs;

    public Http() {
    }

    public Http(boolean external, boolean idcs) {
        this.external = external;
        this.idcs = idcs;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public boolean isIdcs() {
        return idcs;
    }

    public void setIdcs(boolean idcs) {
        this.idcs = idcs;
    }

    @Override
    public String toString() {
        return "Http{" +
                "external=" + external +
                ", idcs=" + idcs +
                '}';
    }
}

class Queue {
    boolean kafka;

    public Queue() {
    }

    public Queue(boolean kafka) {
        this.kafka = kafka;
    }

    public boolean isKafka() {
        return kafka;
    }

    public void setKafka(boolean kafka) {
        this.kafka = kafka;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "kafka=" + kafka +
                '}';
    }
}

class Service {
    String type;

    public Service() {
    }

    public Service(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Service{" +
                "type='" + type + '\'' +
                '}';
    }
}

class Self {
    String name;

    public Self() {
    }

    public Self(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Self{" +
                "name='" + name + '\'' +
                '}';
    }
}
