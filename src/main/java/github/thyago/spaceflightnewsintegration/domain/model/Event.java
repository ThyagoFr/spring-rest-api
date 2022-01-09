package github.thyago.spaceflightnewsintegration.domain.model;

import java.io.Serializable;

public class Event implements Serializable {

    private static final long serialVersionUID = -2042989300326167656L;

    private String id;

    private String provider;

    public Event() {
    }

    public Event(String id, String provider) {
        this.id = id;
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}