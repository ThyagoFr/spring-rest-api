package github.thyago.spaceflightnewsintegration.job;

import github.thyago.spaceflightnewsintegration.service.ArticleService;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.APIClient;
import github.thyago.spaceflightnewsintegration.web.mapper.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@ConditionalOnProperty(
        value = "scheduling.enable",
        havingValue = "true",
        matchIfMissing = true
)
@EnableScheduling
public class ExtractorArticlesJob {

    private final ArticleService articleService;

    private final APIClient apiClient;

    private final ArticleMapper articleMapper;

    private final int LIMIT_PER_REQUEST = 1000;

    private final Logger LOGGER = LoggerFactory.getLogger(ExtractorArticlesJob.class);

    public ExtractorArticlesJob(ArticleService articleService, APIClient apiClient, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.apiClient = apiClient;
        this.articleMapper = articleMapper;
    }

    @Scheduled(fixedRateString = "${scheduling.fix-rate}")
    public void run() {
        this.LOGGER.info("Extracting articles from Space Flight News API...");
        var totalArticles = this.apiClient.countArticles().getCount();
        this.LOGGER.info("Total articles found : {}", totalArticles);
        if (totalArticles > 0){
            var start = 0;
            while (totalArticles != 0) {
                this.LOGGER.info("Requesting articles using start as : {}", start);
                var articles = this.apiClient.getArticlesPaginated(this.LIMIT_PER_REQUEST, start);
                this.articleService.updateInBatch(this.articleMapper.dtoListToModelList(articles));
                var totalExtracted = articles.size();
                totalArticles -= totalExtracted;
                start += totalExtracted;
            }
        }
        this.LOGGER.info("End of extract process");
    }
}
