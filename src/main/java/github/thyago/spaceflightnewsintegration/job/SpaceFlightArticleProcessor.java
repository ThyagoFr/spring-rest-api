package github.thyago.spaceflightnewsintegration.job;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import github.thyago.spaceflightnewsintegration.web.mapper.ArticleMapper;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class SpaceFlightArticleProcessor implements ItemProcessor<List<ArticleResponse>, List<Article>> {

    private final ArticleMapper articleMapper;

    public SpaceFlightArticleProcessor(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public List<Article> process(List<ArticleResponse> items) throws Exception {
        return this.articleMapper.dtoListToModelList(items);
    }

}
