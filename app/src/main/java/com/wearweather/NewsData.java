package com.wearweather;

import java.io.Serializable;

public class NewsData implements Serializable { // 직렬화, 데이터의 구조가 복잡할 때 하나의 데이터 구조로 바꿔서 넘겨줄 때 활용

    private String title;
    private String urlToImage;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}