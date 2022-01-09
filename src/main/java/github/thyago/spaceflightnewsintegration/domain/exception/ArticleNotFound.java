package github.thyago.spaceflightnewsintegration.domain.exception;

public class ArticleNotFound extends RuntimeException {

    private String message;

    public ArticleNotFound(String message) {
        super(message);
        this.message = message;
    }

}