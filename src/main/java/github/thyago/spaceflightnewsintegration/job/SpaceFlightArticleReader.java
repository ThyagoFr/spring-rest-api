package github.thyago.spaceflightnewsintegration.job;

import github.thyago.spaceflightnewsintegration.domain.model.ErrorIntegrationMessage;
import github.thyago.spaceflightnewsintegration.notifier.IntegrationErrorNotifier;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.client.APIClient;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.exception.UnavailableAPIException;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

import java.util.List;

import static java.lang.Thread.sleep;
import static java.time.LocalDateTime.now;

public class SpaceFlightArticleReader implements ItemReader<List<ArticleResponse>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceFlightArticleReader.class);

    private static final int SLEEP_IN_MILLISECONDS = 10000;

    private final APIClient apiClient;

    private final IntegrationErrorNotifier notifier;

    private Integer limit;

    private Integer start;

    private Integer totalArticles;

    public SpaceFlightArticleReader(APIClient apiClient, IntegrationErrorNotifier notifier, Integer limit) {
        this.apiClient = apiClient;
        this.notifier = notifier;
        this.limit = limit;
        this.start = 0;
        this.totalArticles = 0;
    }

    @Override
    public List<ArticleResponse> read() throws Exception {
        LOGGER.info("Reading the information of articles");
        List<ArticleResponse> articles = null;
        try {
            if (firstRead()) {
                this.totalArticles = this.getTotalArticles();
                LOGGER.info("Total articles found : {}", this.totalArticles);
            }
            LOGGER.info("Requesting articles using start as : {}", start);
            articles = this.extractArticles(start);

            var extracted = articles.size();

            if (extracted == 0) {
                articles = null;
            }
            this.start += extracted;

            LOGGER.info("{} articles extracted with success", this.start);
        }catch (Exception e){
            this.sendNotification(e);
            LOGGER.error(e.getMessage());
        }
        return articles;

    }

    private void sendNotification(Exception exception) {
        var message = new ErrorIntegrationMessage(exception.getMessage(), now());
        this.notifier.notify(message);
    }

    private boolean firstRead() {
        return this.start == 0;
    }

    private Integer getTotalArticles() throws InterruptedException {
        var tentatives = 3;
        while (tentatives > 0) {
            LOGGER.info("Trying extract articles from Space Flight News API...");
            try {
                return this.apiClient.countArticles().getCount();
            } catch (UnavailableAPIException unavailableAPIException) {
                tentatives--;
                LOGGER.error(unavailableAPIException.getMessage());
                sleep(SLEEP_IN_MILLISECONDS);
            } catch (Exception exception) {
                throw exception;
            }
        }
        return 0;
    }

    private List<ArticleResponse> extractArticles(Integer start) throws InterruptedException {
        var tentatives = 3;
        while (tentatives > 0) {
            try {
                return this.apiClient.getArticlesPaginated(this.limit, start);
            } catch (UnavailableAPIException unavailableAPIException) {
                tentatives--;
                LOGGER.error(unavailableAPIException.getMessage());
                sleep(SLEEP_IN_MILLISECONDS);
            } catch (Exception exception) {
                throw exception;
            }
        }
        return null;
    }

}
