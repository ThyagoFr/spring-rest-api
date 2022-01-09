package github.thyago.spaceflightnewsintegration.service.spaceflight;

import github.thyago.spaceflightnewsintegration.service.spaceflightapi.APIClient;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class APIClientTest {

    @Autowired
    private APIClient apiClient;

    @Test
    public void countArticlesMustBeGreaterThanZero() {
        var response = this.apiClient.countArticles();

        assertNotNull(response);
        assertNotEquals(0, response.getCount());
    }

    @Test
    public void getArticlesWithLimit10() {
        var limit = 10;
        var start = 0;
        var response = this.apiClient.getArticlesPaginated(limit, start);

        assertNotNull(response);
        assertEquals(limit, response.size());
        for (ArticleResponse article : response) {
            assertNotNull(article.getId());
            assertNotNull(article.getTitle());
        }
    }

}