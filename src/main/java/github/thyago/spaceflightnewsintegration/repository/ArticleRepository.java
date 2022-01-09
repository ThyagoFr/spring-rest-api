package github.thyago.spaceflightnewsintegration.repository;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
}