package github.thyago.spaceflightnewsintegration.web.mapper;

import github.thyago.spaceflightnewsintegration.domain.entity.Article;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.response.ArticleResponse;
import github.thyago.spaceflightnewsintegration.web.dto.ArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel= "spring")
public interface ArticleMapper {

    Article dtoToModel(ArticleDTO dto);

    List<Article> dtoListToModelList(List<ArticleResponse> dtos);

    @Mapping(target = "id", source = "article.id")
    ArticleDTO modelToDto(Article article);

}
