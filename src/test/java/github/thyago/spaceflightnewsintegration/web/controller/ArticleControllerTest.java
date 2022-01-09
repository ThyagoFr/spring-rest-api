package github.thyago.spaceflightnewsintegration.web.controller;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.repository.ArticleRepository;
import github.thyago.spaceflightnewsintegration.web.dto.ArticleDTO;
import github.thyago.spaceflightnewsintegration.web.dto.ErrorResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static github.thyago.spaceflightnewsintegration.util.ArticlesMock.articlesMock;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@TestInstance(PER_CLASS)
public class ArticleControllerTest {

    private static String MONGODB_IMAGE = "mongo:4.4.2";

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGODB_IMAGE);

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private final List<Article> articles = articlesMock();

    private final String ARTICLE_BASE_URL = "/articles";

    @BeforeAll
    void setup() {
        this.articleRepository.saveAll(this.articles);
    }

    @AfterAll
    void teardown() {
        this.articleRepository.deleteAll();
    }

    @Test
    @DisplayName("Test 1 - Search for article must return valid article")
    public void searchValidArticle() {
        // Given
        var article = this.articles.get(0);

        // Act
        var response = this.restTemplate.getForEntity(this.ARTICLE_BASE_URL + "/{id}", ArticleDTO.class, article.getId());

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        var body = response.getBody();
        assertNotNull(body);
        assertEquals(article.getTitle(), body.getTitle());
        assertEquals(article.getId(), body.getId());
    }

    @Test
    @DisplayName("Test 2 - Search for invalid article must return not found status")
    public void searchInvalidArticle() {
        // Given
        var articleID = "0";

        // Act
        var response = this.restTemplate.getForEntity(this.ARTICLE_BASE_URL + "/{id}", ArticleDTO.class, articleID);

        // Assert
        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    }

    @Test
    @DisplayName("Test 3 - Delete valid article must return no content")
    public void deleteValidArticle() {
        // Given
        var article = this.articles.get(0);

        // Act
        var response = this.restTemplate.exchange(
                this.ARTICLE_BASE_URL + "/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                article.getId());

        // Assert
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test 4 - Delete invalid article must return not found")
    public void deleteInvalidArticle() {
        // Given
        var articleID = "0";

        // Act
        var response = this.restTemplate.exchange(
                this.ARTICLE_BASE_URL + "/{id}",
                HttpMethod.DELETE,
                null,
                ErrorResponse.class,
                articleID);

        // Assert
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Test 5 - Create a new article must return created status")
    public void createValidArticle() {
        // Given
        var article = new ArticleDTO();
        article.setTitle("New Article");
        article.setFeatured(true);
        article.setImageUrl("imageURL");
        article.setNewsSite("new_site");
        article.setPublishedAt(now());

        // Act
        var response = this.restTemplate.postForEntity(this.ARTICLE_BASE_URL, article, ArticleDTO.class);

        // Assert
        assertEquals(CREATED, response.getStatusCode());

        var body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(article.getTitle(), body.getTitle());
        assertEquals(article.isFeatured(), body.isFeatured());
        assertEquals(article.getImageUrl(), body.getImageUrl());
        assertEquals(article.getPublishedAt(), body.getPublishedAt());
    }

    @Test
    @DisplayName("Test 5 - Create an invalid article(empty title) must return bad request")
    public void createInvalidArticle() {
        // Given
        var article = new ArticleDTO();
        article.setTitle("");

        // Act
        var response = this.restTemplate.postForEntity(this.ARTICLE_BASE_URL, article, ArticleDTO.class);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

}
