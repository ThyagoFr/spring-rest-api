package github.thyago.spaceflightnewsintegration.web.controller;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.repository.ArticleRepository;
import github.thyago.spaceflightnewsintegration.web.dto.ArticleDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static github.thyago.spaceflightnewsintegration.util.ArticlesMock.articlesMock;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static java.time.LocalDateTime.now;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

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

    private static final List<Article> ARTICLES = articlesMock();

    private static final String ARTICLE_BASE_URL = "/articles";

    @LocalServerPort
    private int serverPort;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    void setup() {
        port = this.serverPort;
        basePath = ARTICLE_BASE_URL;
        this.articleRepository.saveAll(ARTICLES);
    }

    @AfterAll
    void teardown() {
        this.articleRepository.deleteAll();
    }

    @Test
    @DisplayName("Test 1 - Search for article must return valid article")
    public void searchValidArticle() {
        // Given
        var article = ARTICLES.get(0);

        // Act - Assert
        given()
                .when()
                .get("/{id}", article.getId())
                .then()
                .statusCode(OK.value())
                .contentType(ContentType.JSON)
                .body("id", equalTo(article.getId()))
                .body("title", equalTo(article.getTitle()));
    }

    @Test
    @DisplayName("Test 2 - Search for invalid article must return not found status")
    public void searchInvalidArticle() {
        // Given
        var articleID = "0";

        // Act - Assert
        given()
                .when()
                .get("/{id}", articleID)
                .then()
                .statusCode(NOT_FOUND.value())
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Test 3 - Delete valid article must return no content")
    public void deleteValidArticle() {
        // Given
        var article = ARTICLES.get(0);

        // Act - Assert
        given()
                .when()
                .delete("/{id}", article.getId())
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    @DisplayName("Test 4 - Delete invalid article must return not found")
    public void deleteInvalidArticle() {
        // Given
        var articleID = "0";

        // Act - Assert
        given()
                .when()
                .delete("/{id}", articleID)
                .then()
                .statusCode(NOT_FOUND.value())
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Test 5 - Create a new article must return created status")
    public void createValidArticle() {
        // Given
        var article = new ArticleDTO();
        article.setTitle("New Article with more than 10 characters");
        article.setFeatured(true);
        article.setUrl("url.com");
        article.setNewsSite("newSite");
        article.setPublishedAt(now());

        // Act - Assert
        given()
                .contentType(ContentType.JSON)
                .body(article)
                .when()
                .post("")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(CREATED.value())
                .body("id", notNullValue())
                .body("title", equalTo(article.getTitle()))
                .body("featured", equalTo(article.isFeatured()))
                .body("url", equalTo(article.getUrl()))
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Test 6 - Create an invalid article(empty title) must return bad request")
    public void createInvalidArticle() {
        // Given
        var article = new ArticleDTO();
        article.setTitle("");

        // Act - Assert
        given()
                .contentType(ContentType.JSON)
                .body(article)
                .when()
                .post("")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(BAD_REQUEST.value());
    }

}
