package github.thyago.spaceflightnewsintegration.service.spaceflight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.client.APIClient;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.exception.UnavailableAPIException;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import github.thyago.spaceflightnewsintegration.util.WireMockServerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static github.thyago.spaceflightnewsintegration.util.ArticlesMock.articlesMock;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest
@ContextConfiguration(classes = {WireMockServerConfig.class})
public class APIClientTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private APIClient apiClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("When server return TOO_MANY_REQUESTS, client should throw exception UnavailableAPIException")
    public void countArticlesTooManyRequests() {
        // Mock
        mockCountArticlesResponseTooManyRequests(this.wireMockServer);

        // Act - Assert
        assertThrows(UnavailableAPIException.class, () -> this.apiClient.countArticles());
    }

    @Test
    @DisplayName("When server return REQUEST_TIMEOUT, client should throw exception UnavailableAPIException")
    public void countArticlesRequestTimeout() {
        // Mock
        mockCountArticlesResponseTimeout(this.wireMockServer);

        // Act - Assert
        assertThrows(UnavailableAPIException.class, () -> this.apiClient.countArticles());
    }

    @Test
    @DisplayName("When server return SERVICE_UNAVAILABLE, client should throw exception UnavailableAPIException")
    public void countArticlesServiceUnavailable() {
        // Mock
        mockCountArticlesResponseServiceUnavailable(this.wireMockServer);

        // Act - Assert
        assertThrows(UnavailableAPIException.class, () -> this.apiClient.countArticles());
    }

    @Test
    public void getArticlesWithLimit10() throws JsonProcessingException {
        // Given
        var limit = 2;
        var start = 0;

        // Mock
        var articles = articlesMock().stream().limit(limit);
        var body = this.objectMapper.writeValueAsString(articles);
        mockPaginatedArticlesResponse(this.wireMockServer, limit, start, body);

        var response = this.apiClient.getArticlesPaginated(limit, start);

        assertNotNull(response);
        assertEquals(limit, response.size());
        for (ArticleResponse article : response) {
            assertNotNull(article.getTitle());
        }
    }

    private static void mockPaginatedArticlesResponse(WireMockServer wireMockServer, Integer limit, Integer start, String body) {
        wireMockServer.stubFor(get(urlEqualTo(format("/articles?_limit=%d&_start=%d", limit, start)))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(body)
                ));
    }

    private static void mockCountArticlesResponseTooManyRequests(WireMockServer wireMockServer) {
        wireMockServer.stubFor(get(urlEqualTo("/articles/count"))
                .willReturn(aResponse().withStatus(TOO_MANY_REQUESTS.value())));
    }

    private static void mockCountArticlesResponseTimeout(WireMockServer wireMockServer) {
        wireMockServer.stubFor(get(urlEqualTo("/articles/count"))
                .willReturn(aResponse().withStatus(REQUEST_TIMEOUT.value())));
    }

    private static void mockCountArticlesResponseServiceUnavailable(WireMockServer wireMockServer) {
        wireMockServer.stubFor(get(urlEqualTo("/articles/count"))
                .willReturn(aResponse().withStatus(SERVICE_UNAVAILABLE.value())));
    }

}