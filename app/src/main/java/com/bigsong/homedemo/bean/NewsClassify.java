package com.bigsong.homedemo.bean;

/**
 * Created by Administrator on 2016/7/14.
 */
public class NewsClassify {
    public int id;
    public String type;
    public String title;
    public Boolean is_myinterest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIs_myinterest() {
        return is_myinterest;
    }

    public void setIs_myinterest(Boolean is_myinterest) {
        this.is_myinterest = is_myinterest;
    }
}
