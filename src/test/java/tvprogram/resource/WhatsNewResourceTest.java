package tvprogram.resource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static java.util.Arrays.asList;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static tvprogram.infrastructure.BroadcastServiceRestClient.ERROR_PARSING_JSON_FROM_BROADCAST_SERVICE;
import static tvprogram.infrastructure.BroadcastServiceRestClient.PROGRAMME_WEEKLY_ENDPOINT;
import static tvprogram.infrastructure.BroadcastServiceRestClient.WRONG_RESPONSE_CODE_FROM_BROADCAST_SERVICE;
import static tvprogram.resource.WhatsNewResource.PROGRAMME_NEW_ENDPOINT;
import static tvprogram.resource.WhatsNewResource.QUERY_PARAM_NOT_SHOWN;
import static tvprogram.resource.WhatsNewResource.QUERY_PARAM_SYMBOL;
import static tvprogram.test.TestUtils.assertWeeklyNewProgrammesAreEqual;
import static tvprogram.test.Tests.BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO;
import static tvprogram.test.Tests.BROADCASTING_TOMORROW_FIRST_BROADCASTING_TOMORROW;
import static tvprogram.test.Tests.BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY;
import static tvprogram.test.Tests.SERIES_1;
import static tvprogram.test.Tests.SERIES_2;
import static tvprogram.test.Tests.SERIES_3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import tvprogram.domain.Programme;
import tvprogram.domain.WeeklyNewProgramme;
import tvprogram.infrastructure.App;

public class WhatsNewResourceTest {

    public static final boolean ONLY_NOT_SHOWN = true;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8082);

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void beforeClass() throws Exception {
        String[] args = new String[]{"server", "test.yaml"};
        new App().run(args);
    }

    @Test
    public void testWhatsNewReturnsNewShownAndNotShownWhenNewAll() throws JsonProcessingException {
        givenBroadcastServiceWillReturnJsonFor(
                new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_TOMORROW),
                new Programme(SERIES_2, BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY),
                new Programme(SERIES_3, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO));

        Response response = whenRequestSentToWeeklyNewService();

        WeeklyNewProgramme[] weeklyNewProgrammes = response.getBody().as(WeeklyNewProgramme[].class);

        assertThat(weeklyNewProgrammes).hasSize(2);
        assertWeeklyNewProgrammesAreEqual(weeklyNewProgrammes[0], new WeeklyNewProgramme(SERIES_1));
        assertWeeklyNewProgrammesAreEqual(weeklyNewProgrammes[1], new WeeklyNewProgramme(SERIES_2));
    }

    @Test
    public void testWhatsNewReturnsNewNonShownOnlyWhenNewAndNotShown() throws JsonProcessingException {
        givenBroadcastServiceWillReturnJsonFor(
                new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_TOMORROW),
                new Programme(SERIES_2, BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY),
                new Programme(SERIES_3, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO));

        Response response = whenRequestSentToWeeklyNewService(ONLY_NOT_SHOWN);

        WeeklyNewProgramme[] weeklyNewProgrammes = response.getBody().as(WeeklyNewProgramme[].class);

        assertThat(weeklyNewProgrammes).hasSize(1);
        assertWeeklyNewProgrammesAreEqual(weeklyNewProgrammes[0], new WeeklyNewProgramme(SERIES_1));
    }

    @Test
    public void testWhatsNewReturnsEmptyWhenNotNew() throws JsonProcessingException {
        givenBroadcastServiceWillReturnJsonFor(
                new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO),
                new Programme(SERIES_2, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO));

        Response response = whenRequestSentToWeeklyNewService();

        WeeklyNewProgramme[] weeklyNewProgrammes = response.getBody().as(WeeklyNewProgramme[].class);

        assertThat(weeklyNewProgrammes).hasSize(0);
    }

    @Test
    public void testWhatsNewReturnsEmptyWhenNewButNotShown() throws JsonProcessingException {
        givenBroadcastServiceWillReturnJsonFor(
                new Programme(SERIES_1, BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY),
                new Programme(SERIES_2, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO));

        Response response = whenRequestSentToWeeklyNewService(ONLY_NOT_SHOWN);

        WeeklyNewProgramme[] weeklyNewProgrammes = response.getBody().as(WeeklyNewProgramme[].class);

        assertThat(weeklyNewProgrammes).hasSize(0);
    }

    @Test
    public void testBroadcastServiceResponseCodeError404WhenStubIsNotConfiguredForEndpoint() throws JsonProcessingException {
        Response response = whenRequestSentToWeeklyNewService();

        assertThat(response.getStatusCode()).isEqualTo(SERVICE_UNAVAILABLE.getStatusCode());
        assertThat(response.getBody().asString()).contains(WRONG_RESPONSE_CODE_FROM_BROADCAST_SERVICE);
    }

    @Test
    public void testBroadcastServiceResponseCodeErrorParsingJson() throws JsonProcessingException {
        givenBroadcastServiceWillReturnUnexpectedJson();

        Response response = whenRequestSentToWeeklyNewService();

        assertThat(response.getStatusCode()).isEqualTo(SERVICE_UNAVAILABLE.getStatusCode());
        assertThat(response.getBody().asString()).contains(ERROR_PARSING_JSON_FROM_BROADCAST_SERVICE);
    }

    private void givenBroadcastServiceWillReturnJsonFor(Programme... programmes) throws JsonProcessingException {
        String jsonForProgrammes = objectMapper.writeValueAsString(asList(
                programmes));

        givenThat(get(urlMatching(PROGRAMME_WEEKLY_ENDPOINT)).willReturn(aResponse()
                .withStatus(200)
                .withBody(jsonForProgrammes)
                .withHeader("Content-Type", "application/json")));
    }

    private void givenBroadcastServiceWillReturnUnexpectedJson() {
        givenThat(get(urlMatching(PROGRAMME_WEEKLY_ENDPOINT)).willReturn(aResponse()
                .withStatus(200)
                .withBody("wrong json")
                .withHeader("Content-Type", "application/json")));
    }

    private Response whenRequestSentToWeeklyNewService() {
        return whenRequestSentToWeeklyNewService(false);

    }

    private Response whenRequestSentToWeeklyNewService(boolean onlyNotShown) {
        return RestAssured.given().when().get(onlyNotShown ?
                PROGRAMME_NEW_ENDPOINT + QUERY_PARAM_SYMBOL + QUERY_PARAM_NOT_SHOWN + "=true" :
                PROGRAMME_NEW_ENDPOINT);
    }

}
