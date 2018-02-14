package com.ejemplo.grupo.model;

import com.facebook.internal.LockOnGetVariable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by digitalhouse on 14/06/16.
 */
public class News implements Comparable<News>, Serializable{

    private String title;

    private String description;

    private String link;

    private String guid;

    private String pubDate;

    private Date date;

    private String author;

    private String image;

    private String category;

    private String feedIconLink;

    public String getFeedIconLink() {
        return feedIconLink;
    }

    public void setFeedIconLink(String feedIconLink) {
        this.feedIconLink = feedIconLink;
    }

    private Boolean saved;

    private long dateInMilliseconds;

    public String getDateToShow() {
        return dateToShow;
    }

    private String dateToShow;

    public void setDateToShow(String dateToShow) {
        this.dateToShow = dateToShow;
    }

    public long getDateInMilliseconds() {
        return dateInMilliseconds;
    }

    public News() {
        this.saved = false;
    }

    public News(String title, String description, String link, String guid, String pubDate, String author, String image, String category, Boolean saved) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.guid = guid;
        this.pubDate = pubDate;
        this.author = author;
        this.image = image;
        this.category = category;
        this.saved = saved;
    }


    @Override
    public int compareTo(News anotherNews) {

        Long compareDateInMilisecconds = ((News) anotherNews).getDateInMilliseconds();

        Long currenteDateInMiliseconds = this.getDateInMilliseconds();

        return compareDatesInMS(compareDateInMilisecconds, currenteDateInMiliseconds);

    }

    public void setDateInMilliseconds(long dateInMilliseconds) {
        this.dateInMilliseconds = dateInMilliseconds;
    }

    public Integer compareDatesInMS(Long dateInMilliseconds1, Long dateInMilliseconds2){

        Long result = dateInMilliseconds1 - dateInMilliseconds2;

        if (result > 2147483647) {
            return 2147483647; }
         else if (result <= -214783647) {

            return -2147483647;
        }


        return result.intValue();


    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) throws ParseException {
        this.pubDate = pubDate;
        setDate(pubDate);
    }

    public void setDate(String pubDate) throws ParseException {

        SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        this.date = fmt.parse(pubDate);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - MMM dd, yyyy");
        dateToShow = formatter.format(date);
        dateInMilliseconds = date.getTime();

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", author='" + author + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
