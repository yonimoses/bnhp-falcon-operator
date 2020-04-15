package example.smallest;

import com.github.containersolutions.operator.api.Controller;
import com.github.containersolutions.operator.api.ResourceController;
import io.fabric8.kubernetes.api.model.ServicePort;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * A very simple sample controller that creates a service with a label.
 */

@Controller(customResourceClass = CustomService.class,
        crdName = "customservices.sample.javaoperatorsdk")

//        kind = CustomServiceController.KIND,
//                group = CustomServiceController.GROUP,
//                customResourceListClass = CustomServiceList.class,
//        customResourceDonebaleClass = CustomServiceDoneable.class
public class CustomServiceController implements ResourceController<CustomService> {

    public static final String KIND = "CustomService";
    private final static Logger log = LoggerFactory.getLogger(CustomServiceController.class);
    public static final String GROUP = "sample.javaoperatorsdk";

    private final KubernetesClient kubernetesClient;

    public CustomServiceController(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @Override
    public boolean deleteResource(CustomService resource) {
        log.info("Execution deleteResource for: {}", resource.getMetadata().getName());
        kubernetesClient.services().inNamespace(resource.getMetadata().getNamespace())
                .withName(resource.getMetadata().getName()).delete();
        return true;
    }
//
    @Override
    public Optional<CustomService> createOrUpdateResource(CustomService resource) {
        log.info("Execution createOrUpdateResource for: {}", resource.getMetadata().getName());

        ServicePort servicePort = new ServicePort();
        servicePort.setPort(8080);
        ServiceSpec serviceSpec = new ServiceSpec();
        serviceSpec.setPorts(Arrays.asList(servicePort));

        kubernetesClient.services().inNamespace(resource.getMetadata().getNamespace()).createOrReplaceWithNew()
                .withNewMetadata()
                .withName(resource.getSpec().getName())
                .addToLabels("testLabel", resource.getSpec().getLabel())
                .endMetadata()
                .withSpec(serviceSpec)
                .done();
        return Optional.of(resource);
    }
}
