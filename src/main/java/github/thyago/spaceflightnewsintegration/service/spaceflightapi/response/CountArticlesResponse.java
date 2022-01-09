package github.thyago.spaceflightnewsintegration.service.spaceflightapi.response;

import java.io.Serializable;

public class CountArticlesResponse implements Serializable {

    private static final long serialVersionUID = 2876593153537305741L;

    private Integer count;

    public CountArticlesResponse(Integer count) {
        this.count = count;
    }

    public CountArticlesResponse() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}