package tvprogram.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class BroadcastClientConfig extends Configuration {
    @NotEmpty
    @JsonProperty
    private Integer port;

    @NotEmpty
    @JsonProperty
    private String host;

    @NotEmpty
    @JsonProperty
    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
