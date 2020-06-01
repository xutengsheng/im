package com.xts.im.bean;

public class TencentBean {
    public String url;
    public String pic;
    public String title;

    public TencentBean(){}

    public TencentBean(String url, String pic, String title) {
        this.url = url;
        this.pic = pic;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TencentBean{" +
                "url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
