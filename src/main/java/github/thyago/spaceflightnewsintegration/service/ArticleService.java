package github.thyago.spaceflightnewsintegration.service;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.domain.exception.ArticleNotFound;
import github.thyago.spaceflightnewsintegration.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<Article> findAll(Pageable pageable) {
        return this.articleRepository.findAll(pageable);
    }

    public Article findByID(String ID) {
        return this.articleRepository.findById(ID).orElseThrow( () -> {
            throw new ArticleNotFound(format("Article with ID %s not found", ID));
        });
    }

    public Article create(Article article){
        return this.articleRepository.save(article);
    }

    public Article update(String ID, Article article){
        var foundArticle = this.findByID(ID);
        return this.articleRepository.save(article);
    }

    public void updateInBatch(List<Article> articles){
        this.articleRepository.saveAll(articles);
    }

    public void delete(String ID) {
        var article = this.findByID(ID);
        this.articleRepository.delete(article);
    }

}
