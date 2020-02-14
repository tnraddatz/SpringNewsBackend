package com.news.backend.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

import java.sql.Timestamp;
@Data
@Entity
@Table(name = "COMMENT")
public class Comment {

    private @Id @GeneratedValue Long id;
    private Long parentCommentId; // Foreign Key
    private String commentText;
    private Integer votes;
    private @CreationTimestamp Timestamp createdDate;
    private @LastModifiedDate Timestamp updatedAt;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_thread_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CommentThread commentThread;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "parent_comment_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Comment parent_comment;

}
