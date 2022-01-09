package github.thyago.spaceflightnewsintegration.util;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.domain.model.Event;
import github.thyago.spaceflightnewsintegration.domain.model.Launch;

import java.util.List;

public class ArticlesMock {

    public static List<Article> articlesMock() {
        var article1 = new Article();
        article1.setTitle("Article 1");
        article1.setUrl("github.com/thy/article1.pdf");
        article1.setLaunches(List.of(new Launch("id1","provider1")));
        article1.setEvents(List.of(new Event("id1","provider1")));

        var article2 = new Article();
        article2.setTitle("Article 2");
        article2.setUrl("github.com/thy/article2.pdf");
        article2.setLaunches(List.of(new Launch("id2","provider2")));
        article2.setEvents(List.of(new Event("id2","provider3")));

        var article3 = new Article();
        article3.setTitle("Article 3");
        article3.setUrl("github.com/thy/article3.pdf");
        article3.setLaunches(List.of(new Launch("id3","provider3")));
        article3.setEvents(List.of(new Event("id3","provider3")));

        return List.of(article1, article2, article3);
    }

}