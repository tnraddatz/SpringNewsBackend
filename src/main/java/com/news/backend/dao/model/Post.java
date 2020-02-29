package com.news.backend.dao.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "POST")
public class Post {
    private @Id @GeneratedValue Long id;
    private @Size(max = 128) String title;
    private String description;
    private String outletName;
    private String externalUrl;
    private String urlToImage;
    private Integer votes;
    private @CreationTimestamp Timestamp createdDate;

    public Post(){
        this.votes = 0;
    }

}