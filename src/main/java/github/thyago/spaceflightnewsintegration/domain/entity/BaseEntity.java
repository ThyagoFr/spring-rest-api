package github.thyago.spaceflightnewsintegration.domain.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 318778470028309639L;

    @Id
    private String id;

    private LocalDateTime createdAt = now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
