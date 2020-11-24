package eds.cepm.newcepmart.controller;

import eds.cepm.newcepmart.ConfigData;
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

    @GetMapping(value = "data")
    public Object data(){
        return configData;
    }


    @GetMapping(value = "password")
    public Object password(){
        return password;
    }



    @Value("${data.password}")
    private String password;


}