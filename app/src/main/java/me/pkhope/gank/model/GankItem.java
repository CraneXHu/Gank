package me.pkhope.gank.model;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pkhope on 16/11/24.
 */

@Table("gank_item")
public class GankItem implements Serializable{

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    public long id;
    public String _id;
    public Date createdAt;
    public String desc;
    public List<String> images;
    public Date publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void set_id(String id){
        _id = id;
    }

    public String get_id(){
        return _id;
    }

    public void setCreatedAt(Date createAt){
        this.createdAt = createAt;
    }

    public Date getCreatedAt(){
        return createdAt;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }

    public void setImages(List<String> images){
        this.images = images;
    }

    public List<String> getImages(){
        return images;
    }

    public void setPublishedAt(Date publishedAt){
        this.publishedAt = publishedAt;
    }

    public Date getPublishedAt(){
        return publishedAt;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getSource(){
        return source;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public void setUsed(boolean used){
        this.used = used;
    }

    public boolean getUsed(){
        return used;
    }

    public void setWho(String who){
        this.who = who;
    }

    public String getWho(){
        return who;
    }
}

