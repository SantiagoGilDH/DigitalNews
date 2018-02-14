package com.ejemplo.grupo.model;

import java.util.Date;

/**
 * Created by lab on 19-Jun-16.
 */
public class Feed {

    private String category;
    private Date createdAt;
    private String feedLink;
    private String iconLink;
    private String id;
    private String searchableTitle;
    private String title;
    private Date updatedAt;
    private Integer subscribedTo;


    @Override
    public String toString() {
        return "Feed{" +
                "category=" + category +
                ", createdAt=" + createdAt +
                ", feedLink='" + feedLink + '\'' +
                ", iconLink='" + iconLink + '\'' +
                ", id='" + id + '\'' +
                ", searchableTitle='" + searchableTitle + '\'' +
                ", title='" + title + '\'' +
                ", updatedAt=" + updatedAt +
                ", subscribedTo=" + subscribedTo +
                '}';
    }

  /*  public Feed (String category, String feedLink, String iconLink, Boolean suscribedTo, String title){
        this.category = new FeedCategory();
    }*/

    public void setSubscribedTo(Integer subscribedTo) {
        this.subscribedTo = subscribedTo;
    }

    public Integer getSubscribedTo() {
        return subscribedTo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFeedLink() {
        return feedLink;
    }

    public String getIconLink() {
        return iconLink;
    }

    public String getId() {
        return id;
    }

    public String getSearchableTitle() {
        return searchableTitle;
    }

    public String getTitle() {
        return title;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFeedLink(String feedLink) {
        this.feedLink = feedLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}