package ru.rdb.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "raw_data")
public class RawData {

    @EmbeddedId
    private DbId id;

    @Type(type = "jsonb")
    private String json;
    @Column(name = "create_date")
    private Long createDate;
    @Column(name = "url")
    private String url;
    @Column(name = "err")
    private String err;

    public RawData() {
    }

    public RawData(DbId id, String json, Long createDate, String url, String err) {
        this.id = id;
        this.json = json;
        this.createDate = createDate;
        this.url = url;
        this.err = err;
    }

    public DbId getId() {
        return id;
    }

    public void setId(DbId id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
