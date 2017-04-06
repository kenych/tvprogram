package tvprogram.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class AppConfig extends Configuration {
    @JsonProperty
    private BroadcastClientConfig broadcastClientConfig = new BroadcastClientConfig();

    public BroadcastClientConfig getBroadcastClientConfig() {
        return broadcastClientConfig;
    }


}
