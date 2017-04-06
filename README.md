**Restful application example with service, retrying client and mocked remote service.**

**Libs used:**
```
hamcrest
rest-assured
wiremock
mockito
assertj
jackson
jersey-client
dropwizard
```


If you run: mvn test
this would start actual jetty server and test all classes, as well as main end point WhatsNewResource through
WhatsNewResourceTest which is kind of acceptance test class. That class is using RestAssured to send http request to main endpoint and then
the service will send actual http request through jersey client to hypothetical BroadcastService to retrieve
weekly programs to be filtered. As BroadcastService is a remote service, we mock it with WireMockRule in WhatsNewResourceTest.

As BroadcastServiceRestClient is connecting to remote Http service, many things can go wrong potentially, so we have thorough
tests covering most of cases like 404 and parsing json returned form remote service.
So in case remote ENDPOINT is down, or json has changed, our service would handle it gracefully and we have tests covering that.

Please also note that BroadcastServiceRestClient is using retry mechanism which also has tests covering its behaviour.

