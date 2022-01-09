package github.thyago.spaceflightnewsintegration.service;

import github.thyago.spaceflightnewsintegration.domain.exception.ArticleNotFound;
import github.thyago.spaceflightnewsintegration.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    private static final String INVALID_ID = "0";

    @Test
    @DisplayName("Should throw ArticleNotFoundException when article repository return empty optional")
    public void shouldThrowArticleNotFoundException() {
        // Mock
        when(this.articleRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        // Act / Assert
        assertThrows(ArticleNotFound.class, () -> this.articleService.findByID(INVALID_ID));
    }

}
