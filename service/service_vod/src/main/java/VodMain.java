import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author: Niebelungen
 * @create: 2022/4/12-10:12
 * @VERSION: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
public class  VodMain {
    public static void main(String[] args) {
        SpringApplication.run(VodMain.class,args);
    }
}
