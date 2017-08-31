package singledevapps.wvstask.model;

/**
 * Created by prakash on 11/6/17.
 */

public class News {
    private String title;
    private String urlToImage;
    private String description;
    private String sourceUrl;

    public News(String title,String urlToImage,String description,String sourceUrl){
        this.title = title;
        this.urlToImage = urlToImage;
        this.description = description;
        this.sourceUrl = sourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
