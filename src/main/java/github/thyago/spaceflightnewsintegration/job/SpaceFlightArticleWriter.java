package github.thyago.spaceflightnewsintegration.job;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class SpaceFlightArticleWriter implements ItemWriter<List<Article>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceFlightArticleWriter.class);

    private final ArticleRepository articleRepository;

    public SpaceFlightArticleWriter(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void write(List<? extends List<Article>> items) throws Exception {
        for (List<Article> articles: items) {
            LOGGER.info("Inserting/Updating {} items", articles.size());
            this.articleRepository.saveAll(articles);
        }
    }
}