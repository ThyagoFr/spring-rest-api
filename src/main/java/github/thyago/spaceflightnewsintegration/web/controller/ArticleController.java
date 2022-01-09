package github.thyago.spaceflightnewsintegration.web.controller;

import github.thyago.spaceflightnewsintegration.service.ArticleService;
import github.thyago.spaceflightnewsintegration.web.dto.ArticleDTO;
import github.thyago.spaceflightnewsintegration.web.mapper.ArticleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    private final ArticleMapper articleMapper;

    private final ArticleService articleService;

    public ArticleController(ArticleMapper articleMapper, ArticleService articleService) {
        this.articleMapper = articleMapper;
        this.articleService = articleService;
    }

    @GetMapping(produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(OK)
    public Page<ArticleDTO> findAll(Pageable pageable) {
        return this.articleService.findAll(pageable).map(a -> this.articleMapper.modelToDto(a));
    }

    @GetMapping(
            value = "/{id}"
    )
    @ResponseStatus(OK)
    public ArticleDTO findByID(@PathVariable("id") String id) {
        return this.articleMapper.modelToDto(this.articleService.findByID(id));
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(CREATED)
    public ArticleDTO create(@Valid @RequestBody ArticleDTO articleDTO) {
        var article = this.articleMapper.dtoToModel(articleDTO);
        return this.articleMapper.modelToDto(this.articleService.create(article));
    }

    @PutMapping(
            value = "/{id}",
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(ACCEPTED)
    public ArticleDTO update(@PathVariable("id") String id, @RequestBody ArticleDTO articleDTO) {
        var article = this.articleMapper.dtoToModel(articleDTO);
        return this.articleMapper.modelToDto(this.articleService.update(id, article));
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        this.articleService.delete(id);
    }

}
