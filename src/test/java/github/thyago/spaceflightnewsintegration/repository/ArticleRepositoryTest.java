package github.thyago.spaceflightnewsintegration.repository;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static github.thyago.spaceflightnewsintegration.util.ArticlesMock.articlesMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@TestInstance(PER_CLASS)
public class ArticleRepositoryTest {

    private static String MONGODB_IMAGE = "mongo:4.4.2";

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGODB_IMAGE);

    static {
        mongoDBContainer.start();
    }

    private final List<Article> articles = articlesMock();

    @Autowired
    private ArticleRepository articleRepository;

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    public void beforeAll() {
        this.articleRepository.saveAll(articles);
    }

    @AfterAll
    public void afterAll() {
        this.articleRepository.deleteAll();
    }

    @Test
    @DisplayName("Test 1 - If MongoDB container is running")
    public void testContainerIsRunning() {
        assertTrue(mongoDBContainer.isRunning());
    }

    @Test
    @DisplayName("Test 2 - Return 3 articles and their launches/events")
    public void mustReturn2Articles() {
        var articles = this.articleRepository.findAll();

        assertNotNull(articles);
        assertEquals(this.articles.size(), this.articleRepository.findAll().size());
        for (Article article : articles) {
            assertNotNull(article.getId());
            assertNotNull(article.getCreatedAt());
            assertNotNull(article.getLaunches());
            assertNotNull(article.getEvents());
        }
    }

}