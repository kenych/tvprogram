package tvprogram.infrastructure.retry;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

import java.util.Optional;

public class ClientRetryFilter extends ClientFilter {

    private RetriableRunner<ClientResponse> retrier;

    public ClientRetryFilter(RetriableRunner retrier) {
        this.retrier = retrier;
    }

    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {

        Optional<ClientResponse> clientResponse = retrier.run(() -> getNext().handle(request));

        if (clientResponse.isPresent()) {
            return clientResponse.get();
        } else {
            throw new ClientHandlerException("Can't connect to " + request.getURI());
        }
    }
}
