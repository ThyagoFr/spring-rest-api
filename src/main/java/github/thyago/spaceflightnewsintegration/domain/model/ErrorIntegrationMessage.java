package github.thyago.spaceflightnewsintegration.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorIntegrationMessage implements Serializable {

    private static final long serialVersionUID = 740051910489455508L;

    private String reason;

    private LocalDateTime timestamp;

    public ErrorIntegrationMessage(String reason, LocalDateTime timestamp) {
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}