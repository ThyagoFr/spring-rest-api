package github.thyago.spaceflightnewsintegration.domain.entity;

import java.io.Serializable;

public class Launch implements Serializable {

    private static final long serialVersionUID = 2025789295116038751L;

    private String id;

    private String provider;

    public Launch() {
    }

    public Launch(String id, String provider) {
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
