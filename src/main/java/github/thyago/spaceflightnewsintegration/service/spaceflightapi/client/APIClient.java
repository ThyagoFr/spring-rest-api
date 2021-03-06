package github.thyago.spaceflightnewsintegration.service.spaceflightapi.client;

import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.CountArticlesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "${space.flight.server.url}", name = "space.flight.news-api.client")
public interface APIClient {

    @GetMapping("/articles/count")
    CountArticlesResponse countArticles();

    @GetMapping("/articles")
    List<ArticleResponse> getArticlesPaginated(@RequestParam("_limit") Integer limit, @RequestParam("_start") Integer start);

}