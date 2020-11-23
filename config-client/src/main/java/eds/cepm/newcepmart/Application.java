package eds.cepm.newcepmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("MYSQL_DB_PASSWORD", "0ae7f0522935b0ceee3027d1fd86cef63df28c6f60e0e3bca97870d87560ab9f");
        SpringApplication.run(Application.class, args);
    }

}
