package com.news.backend.dao.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.sql.Timestamp;
@Data
@Entity
@Table(name = "COMMENT_THREAD")
public class CommentThread {

    private @Id @GeneratedValue Long id;
    private String threadName;
    private @CreationTimestamp Timestamp createdDate;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;

}
