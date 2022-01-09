package github.thyago.spaceflightnewsintegration.web.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 8557249152861864601L;

    private String message;

    private String path;

    private LocalDateTime timestamp;

    private Map<String, String> errors;

    public ErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}