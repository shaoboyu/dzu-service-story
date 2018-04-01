package mblog;

import mblog.web.filter.RequestCostFilter;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * SprintBootApplication
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@ServletComponentScan
public class BootApplication extends SpringBootServletInitializer {
    private static Logger log = Logger.getLogger(BootApplication.class);
    /**
     * 加载配置文件
     * */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootApplication.class);
    }

    /**
     * 主程序入口
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
        log.info("=====================>BootApplication start finish!<=======================");
    }

}