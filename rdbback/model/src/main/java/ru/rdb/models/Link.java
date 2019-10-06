package ru.rdb.models;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "links")
public class Link {

    @EmbeddedId
    private DbId id;
    @Column(name = "url")
    private String url;
    @Column(name = "create_date")
    private Long createDate;
    @Column(name = "read_date")
    private Long readDate;

    public Link(DbId id, String url, Long createDate, Long readDate) {
        this.id = id;
        this.url = url;
        this.createDate = createDate;
        this.readDate = readDate;
    }

    public Link() {
    }

    public DbId getId() {
        return id;
    }

    public void setId(DbId id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getReadDate() {
        return readDate;
    }

    public void setReadDate(Long readDate) {
        this.readDate = readDate;
    }
}
