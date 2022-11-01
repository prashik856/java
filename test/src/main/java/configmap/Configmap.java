package configmap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import configmap.model.K8sConfigmap;

import java.io.File;
import java.io.IOException;

public class Configmap {
    public Configmap() {}

    public void loadConfigmap() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("configmap_post.yaml").getFile());

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        K8sConfigmap k8sConfigmap = objectMapper.readValue(file, K8sConfigmap.class);
        k8sConfigmap.printInformation();
    }
}
