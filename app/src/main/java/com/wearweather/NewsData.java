package com.wearweather;

import java.io.Serializable;

public class NewsData implements Serializable { // 직렬화, 데이터의 구조가 복잡할 때 하나의 데이터 구조로 바꿔서 넘겨줄 때 활용

    private String title;
    private String link;
    private String content;
    private String description;
    private String pubDate;

    public NewsData() {

    }

    public NewsData(String title, String link, String content, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.content = content;
        this.description = description;
        this.pubDate = pubDate;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String urlToImage) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() { return pubDate; }

    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}