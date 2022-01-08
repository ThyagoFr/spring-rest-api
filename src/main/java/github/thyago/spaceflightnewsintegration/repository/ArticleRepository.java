package github.thyago.spaceflightnewsintegration.repository;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}