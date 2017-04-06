package tvprogram.infrastructure;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import tvprogram.infrastructure.config.AppConfig;
import tvprogram.resource.WhatsNewResource;
import tvprogram.service.BroadcastService;
import tvprogram.service.ProgrammeServiceImpl;


public class App extends Service<AppConfig> {

    public static void main(String[] args) throws Exception {
        new App().run(new String[]{"server", "prod.yaml"});
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.setName("programmes");
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) {
        //configure services
        BroadcastService broadcastService = new BroadcastServiceRestClient(appConfig.getBroadcastClientConfig());
        ProgrammeServiceImpl whatsNewService = new ProgrammeServiceImpl(broadcastService);

        //configure REST
        environment.addResource(new WhatsNewResource(whatsNewService));
    }
}

