package github.thyago.spaceflightnewsintegration.job;

import github.thyago.spaceflightnewsintegration.domain.model.ErrorIntegrationMessage;
import github.thyago.spaceflightnewsintegration.notifier.IntegrationErrorNotifier;
import github.thyago.spaceflightnewsintegration.service.ArticleService;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.client.APIClient;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.exception.UnavailableAPIException;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import github.thyago.spaceflightnewsintegration.web.mapper.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static java.time.LocalDateTime.now;

@Configuration
@ConditionalOnProperty(
        value = "scheduling.enable",
        havingValue = "true",
        matchIfMissing = true
)
@EnableScheduling
public class ExtractorArticlesJob {

    private static int sleepInMilliseconds = 10000;

    private final ArticleService articleService;

    private final APIClient apiClient;

    private final ArticleMapper articleMapper;

    private final IntegrationErrorNotifier notifier;

    private final int LIMIT_PER_REQUEST = 1000;

    private final Logger LOGGER = LoggerFactory.getLogger(ExtractorArticlesJob.class);

    private int tentatives = 3;

    public ExtractorArticlesJob(ArticleService articleService, APIClient apiClient, ArticleMapper articleMapper, IntegrationErrorNotifier notifier) {
        this.articleService = articleService;
        this.apiClient = apiClient;
        this.articleMapper = articleMapper;
        this.notifier = notifier;
    }

    @Scheduled(fixedRateString = "${scheduling.fix-rate}")
    public void run() throws InterruptedException {
        this.LOGGER.info("Start extract process");
        try {
            var totalArticles = this.getTotalArticles();
            this.LOGGER.info("Total articles found : {}", totalArticles);
            if (totalArticles > 0) {
                var start = 0;
                while (totalArticles != 0) {
                    this.LOGGER.info("Requesting articles using start as : {}", start);
                    var articles = this.extractArticles(start);
                    this.articleService.updateInBatch(this.articleMapper.dtoListToModelList(articles));
                    var totalExtracted = articles.size();
                    totalArticles -= totalExtracted;
                    start += totalExtracted;
                }
            }
        } catch (Exception exception) {
            this.sendNotification(exception);
            this.LOGGER.error(exception.getMessage());
            this.LOGGER.error("Stopping extract articles process");
            return;
        }
        this.LOGGER.info("End of extract process");
    }

    private List<ArticleResponse> extractArticles(Integer start) throws InterruptedException {
        while (this.tentatives > 0) {
            try {
                return this.apiClient.getArticlesPaginated(this.LIMIT_PER_REQUEST, start);
            } catch (UnavailableAPIException unavailableAPIException) {
                tentatives--;
                this.LOGGER.error(unavailableAPIException.getMessage());
                Thread.sleep(this.sleepInMilliseconds);
            } catch (Exception exception) {
                throw exception;
            }
        }
        return null;
    }

    private int getTotalArticles() throws InterruptedException {
        while (this.tentatives > 0) {
            this.LOGGER.info("Trying extract articles from Space Flight News API...");
            try {
                return this.apiClient.countArticles().getCount();
            } catch (UnavailableAPIException unavailableAPIException) {
                tentatives--;
                this.LOGGER.error(unavailableAPIException.getMessage());
                Thread.sleep(this.sleepInMilliseconds);
            } catch (Exception exception) {
                throw exception;
            }
        }
        return 0;
    }

    private void sendNotification(Exception exception) {
        var message = new ErrorIntegrationMessage(exception.getMessage(), now());
        this.notifier.notify(message);
    }

}