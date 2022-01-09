package github.thyago.spaceflightnewsintegration.service.spaceflightapi.exception;

public class UnavailableAPIException extends RuntimeException {

    private final String message;

    public UnavailableAPIException(String message) {
        super(message);
        this.message = message;
    }

}