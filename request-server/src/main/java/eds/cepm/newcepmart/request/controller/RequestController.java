package eds.cepm.newcepmart.request.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
//@RefreshScope
public class RequestController {

    private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping(value = "client" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object discoveryClient(){
        return discoveryClient.getInstances("test-resource-service");

    }

    @GetMapping(value = "request/resources/async" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getResourcesAsync(){
        RestTemplate temp = restTemplate;

        threadPoolTaskExecutor.submit(new Thread(()->{
            String response = temp.getForObject("http://localhost:9002/resource/1", String.class);
//            System.out.println("*****************   " + response);
            LOG.info("*****************   " + response);
        }));
        String response = temp.getForObject("http://localhost:9002/resources", String.class);
        LOG.info("*****************   " + response);
        return response;
    }

    @GetMapping(value = "request/resources/404" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getResources404(){
        RestTemplate temp = restTemplate;

        return temp.getForObject("http://localhost:9002/resourcex/1", String.class);
    }

    @GetMapping(value = "request/resources/500" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getResources500(){
        RestTemplate temp = restTemplate;

        return temp.getForObject("http://localhost:9002/resource/1111", String.class);
    }

    @GetMapping(value = "request/resources" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getResources(){
        return resourceService.getResources();
    }

    @Autowired
    private ResourceService resourceService;


    @FeignClient("${service-name.resource-service}")
    public interface ResourceService {
        @RequestMapping("/resources")
        public String getResources();
    }


}