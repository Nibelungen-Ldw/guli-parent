import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author: Niebelungen
 * @create: 2022/4/14-11:32
 * @VERSION: 1.0
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
public class CmsMain {
    public static void main(String[] args) {
        SpringApplication.run(CmsMain.class,args);
    }
}
