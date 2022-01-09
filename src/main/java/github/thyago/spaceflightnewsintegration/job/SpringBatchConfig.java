package github.thyago.spaceflightnewsintegration.job;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.notifier.IntegrationErrorNotifier;
import github.thyago.spaceflightnewsintegration.repository.ArticleRepository;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.client.APIClient;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import github.thyago.spaceflightnewsintegration.web.mapper.ArticleMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private static final Integer LIMIT_PER_REQUEST = 1000;

    @Bean
    public ItemReader<List<ArticleResponse>> itemReader(APIClient apiClient, IntegrationErrorNotifier notifier) {
        return new SpaceFlightArticleReader(apiClient, notifier, LIMIT_PER_REQUEST);
    }

    @Bean
    public ItemProcessor<List<ArticleResponse>, List<Article>> itemProcessor(ArticleMapper articleMapper) {
        return new SpaceFlightArticleProcessor(articleMapper);
    }

    @Bean
    public ItemWriter<List<Article>> itemWriter(ArticleRepository articleRepository) {
        return new SpaceFlightArticleWriter(articleRepository);
    }

    @Bean
    public Step extractStep(ItemReader<List<ArticleResponse>> reader,
                            ItemProcessor<List<ArticleResponse>, List<Article>> processor,
                            ItemWriter<List<Article>> writer,
                            StepBuilderFactory stepBuilderFactory){
        return stepBuilderFactory.get("extractStep").<List<ArticleResponse>,List<Article>>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job extractJob(Step extractStep, JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("extractJob")
                .incrementer(new RunIdIncrementer())
                .flow(extractStep)
                .end()
                .build();
    }

}