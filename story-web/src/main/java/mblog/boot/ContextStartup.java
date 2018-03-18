package mblog.boot;

import mblog.base.context.AppContext;
import mblog.base.lang.Consts;
import mblog.base.print.Printer;
import mblog.core.data.Config;
import mblog.core.persist.service.ChannelService;
import mblog.core.persist.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * 加载配置信息到系统
 *
 */
@Component
public class ContextStartup implements ApplicationRunner, Ordered, ServletContextAware {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AppContext appContext;

    private ServletContext servletContext;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Timer timer = new Timer("init config");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Printer.info("initialization ...");

                List<Config> configs = configService.findAll();
                Map<String, String> configMap = new HashMap<>();

//                if (configs.isEmpty()) {
//                    Printer.error("------------------------------------------------------------");
//                    Printer.error("- ERROR: No initialization data is imported (db_mblog.sql) -");
//                    Printer.error("-       Import the initial database and start again.       -");
//                    Printer.error("------------------------------------------------------------");
//                    System.exit(1);
//                } else {
//
//                    if (configs.size() < 13) {
//                        Printer.error("-----------------------------------------------------------------");
//                        Printer.error("- ERROR: The data is not complete, Please import (db_mblog.sql) -");
//                        Printer.error("-----------------------------------------------------------------");
//                    }
//                    configs.forEach(conf -> {
////						servletContext.setAttribute(conf.getKey(), conf.getValue());
//                        configMap.put(conf.getKey(), conf.getValue());
//                    });
//                }

                appContext.setServletContext(servletContext);
                appContext.setConfig(configMap);

                servletContext.setAttribute("channels", channelService.findAll(Consts.STATUS_NORMAL));

                Printer.info("OK, completed");
            }
        }, 1 * Consts.TIME_MIN);
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
