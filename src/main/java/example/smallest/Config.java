package example.smallest;

import com.github.containersolutions.operator.Operator;
import com.github.containersolutions.operator.api.ResourceController;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {
    private final static Logger log = LoggerFactory.getLogger(Config.class);

    @Bean
    public KubernetesClient kubernetesClient() {
        return new DefaultKubernetesClient();
    }

    @Bean
    public CustomServiceController customServiceController(KubernetesClient client) {
        log.debug("Config is loading ");
        return new CustomServiceController(client);
    }

    //  Register all controller beans
    @Bean
    public Operator operator(KubernetesClient client, List<ResourceController> controllers) {
        Operator operator = new Operator(client);
        log.debug("registerControllerForAllNamespaces --> {}",controllers);
        controllers.forEach(operator::registerControllerForAllNamespaces);



//        controllers.forEach(c -> operator.registerController(c));
        return operator;
    }

}
