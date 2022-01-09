package github.thyago.spaceflightnewsintegration.service.spaceflightapi.response;

import github.thyago.spaceflightnewsintegration.domain.model.Event;
import github.thyago.spaceflightnewsintegration.domain.model.Launch;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleResponse implements Serializable {

    private static final long serialVersionUID = -4183333662322940728L;

    private String id;

    private String createdAt;

    private boolean featured;

    private String title;

    private String url;

    private String imageUrl;

    private String newsSite;

    private String summary;

    private LocalDateTime publishedAt;

    private List<Launch> launches;

    private List<Event> events;

    public ArticleResponse() {
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

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
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
