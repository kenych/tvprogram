package tvprogram.service;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tvprogram.infrastructure.retry.ClientRetryFilter;
import tvprogram.infrastructure.retry.RetriableRunner;

import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class ClientRetryFilterTest {

    private static final int OK = 200;
    private ClientRetryFilter clientRetryFilter;

    @Mock
    private RetriableRunner<ClientResponse> retrierMockMock;

    @Mock
    private ClientRequest clientRequestMock;

    @Mock
    private ClientResponse clientResponseMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        clientRetryFilter = new ClientRetryFilter(retrierMockMock);

        when(clientResponseMock.getStatus()).thenReturn(OK);
    }

    @Test(expected = ClientHandlerException.class)
    public void testHandleThrowsException() throws Exception {
        when(retrierMockMock.run(anyObject())).thenReturn(Optional.<ClientResponse>empty());

        clientRetryFilter.handle(clientRequestMock);
    }

    @Test
    public void testHandleReturns() throws Exception {
        when(retrierMockMock.run(anyObject())).thenReturn(Optional.of(clientResponseMock));

        assertThat(clientRetryFilter.handle(clientRequestMock).getStatus()).isEqualTo(OK);
    }
}
