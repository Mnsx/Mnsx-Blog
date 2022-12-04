package top.mnsx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Mnsx_x
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "top.mnsx.mapper")
public class MnsxBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MnsxBlogApplication.class, args);
    }
}
