package eds.cepm.newcepmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigDataController {

    @Autowired
    private ConfigData configData;

    @Autowired
    private AutoRefreshConfigData autoRefreshConfigData;

    @GetMapping(value = "freshData")
    public Object freshData(){
        return autoRefreshConfigData;
    }

    @GetMapping(value = "data")
    public Object data(){
        return configData;
    }

    @GetMapping(value = "attr1")
    public Object attr1(){
        return attr1;
    }

    @GetMapping(value = "attr2")
    public Object attr2(){
        return attr2;
    }

    @GetMapping(value = "attr3")
    public Object attr3(){
        return attr3;
    }

    @GetMapping(value = "password")
    public Object password(){
        return password;
    }


    @Value("${test.attr1}")
    private String attr1;


    @Value("${test.attr2}")
    private String attr2;

    @Value("${test.attr3}")
    private String attr3;

    @Value("${data.password}")
    private String password;


}