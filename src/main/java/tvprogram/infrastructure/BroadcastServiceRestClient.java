package tvprogram.infrastructure;

import com.sun.jersey.api.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tvprogram.domain.CommunicationException;
import tvprogram.domain.Programme;
import tvprogram.infrastructure.config.BroadcastClientConfig;
import tvprogram.infrastructure.retry.ClientRetryFilter;
import tvprogram.infrastructure.retry.RetriableRunner;
import tvprogram.infrastructure.retry.SleepBeforeNextTryRetrier;
import tvprogram.service.BroadcastService;

import java.util.List;

public class BroadcastServiceRestClient implements BroadcastService {

    public static final String PROGRAMME_WEEKLY_ENDPOINT = "/programme/weekly";

    private static final int NUMBER_OF_RETRIES = 3;
    private static final int SLEEP_MS = 1000;

    private static final Logger logger = LoggerFactory.getLogger(BroadcastServiceRestClient.class);
    public static final String WRONG_RESPONSE_CODE_FROM_BROADCAST_SERVICE = "Wrong response code from BroadcastService: ";
    public static final String ERROR_PARSING_JSON_FROM_BROADCAST_SERVICE = "Error parsing Json from BroadcastService: ";
    public static final String ERROR_TALKING_TO_BROADCAST_SERVICE = "Error talking to BroadcastService: ";

    private String baseUrl;

    private Client client;

    private RetriableRunner<ClientResponse> retrier;

    public BroadcastServiceRestClient(BroadcastClientConfig config) {
        client = new Client();
        this.baseUrl = config.getProtocol() + "://" + config.getHost() + ":" + config.getPort();
        retrier = new SleepBeforeNextTryRetrier<>(NUMBER_OF_RETRIES, SLEEP_MS);
        logger.info("config for thisWeekClient url: {}", baseUrl);
    }

    @Override
    public List<Programme> weeklyProgrammes() throws CommunicationException {
        String url = baseUrl + PROGRAMME_WEEKLY_ENDPOINT;
        logger.debug("sending request to: " + url);

        WebResource webResource = client.resource(url);
        webResource.addFilter(new ClientRetryFilter(retrier));

        ClientResponse response = null;
        List<Programme> programmes = null;

        try {
            response = webResource.accept("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            throw new CommunicationException(ERROR_TALKING_TO_BROADCAST_SERVICE + response.getStatus());
        }

        if (response.getStatus() != 200) {
            throw new CommunicationException(WRONG_RESPONSE_CODE_FROM_BROADCAST_SERVICE + response.getStatus());
        }

        try {
            programmes = response.getEntity(new GenericType<List<Programme>>() {
            });
        } catch (Exception e) {
            throw new CommunicationException(ERROR_PARSING_JSON_FROM_BROADCAST_SERVICE + e.getMessage());
        }

        response.close();

        return programmes;
    }
}
