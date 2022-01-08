package github.thyago.spaceflightnewsintegration.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTest {

    private static String POSTGRESQL_IMAGE = "postgres:latest";

    private static String DATABASE_NAME = "test_db";

    private static String DATABASE_USERNAME = "user_test";

    private static String DATABASE_PASSWORD = "password_test";

    @Container
    private static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer(POSTGRESQL_IMAGE)
            .withDatabaseName(DATABASE_NAME)
            .withPassword(DATABASE_PASSWORD)
            .withUsername(DATABASE_USERNAME);

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Autowired
    private ArticleRepository articleRepository;

    private final List<Long> VALID_IDS = List.of(1L, 2L, 3L);

    @Test
    @DisplayName("Test JSONB use mapping")
    public void testJSONBMapping() {
        var article = this.articleRepository.findById(VALID_IDS.get(0)).get();

        assertNotNull(article);
        assertNotNull(article.getLaunches());
        assertEquals("launchID1", article.getLaunches().get(0).getId());
        assertEquals("launch1", article.getLaunches().get(0).getProvider());
        assertNotNull(article.getEvents());
    }

}