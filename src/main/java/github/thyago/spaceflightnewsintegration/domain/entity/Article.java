package github.thyago.spaceflightnewsintegration.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "articles")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Article implements Serializable {

    private static final long serialVersionUID = 318778470028309639L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean featured;

    private String title;

    private String url;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "news_site")
    private String newsSite;

    private String summary;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Launch> launches;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Event> events;

    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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