package github.thyago.spaceflightnewsintegration.web.dto;

import github.thyago.spaceflightnewsintegration.domain.model.Event;
import github.thyago.spaceflightnewsintegration.domain.model.Launch;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = -4183333662322940728L;

    private String id;

    private String createdAt;

    @NotNull(message = "user must provide featured property")
    private Boolean featured;

    @NotBlank(message = "user must provide title property")
    @Min(value = 10, message = "user must provide a title with more than 10 characters")
    private String title;

    @NotBlank(message = "user must provide url property")
    private String url;

    private String imageUrl;

    private String newsSite;

    @NotBlank(message = "user must provide summary property")
    private String summary;

    private LocalDateTime publishedAt;

    private List<Launch> launches;

    private List<Event> events;

    public ArticleDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean isFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsSite() {
        return newsSite;
    }

    public void setNewsSite(String newsSite) {
        this.newsSite = newsSite;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<Launch> getLaunches() {
        return launches;
    }

    public void setLaunches(List<Launch> launches) {
        this.launches = launches;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
