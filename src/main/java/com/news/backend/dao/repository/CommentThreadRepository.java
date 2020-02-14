package com.news.backend.dao.repository;

import com.news.backend.dao.model.Comment;
import com.news.backend.dao.model.CommentThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentThreadRepository extends JpaRepository<CommentThread, Long> {
    Page<CommentThread> findByPostId(Long postId, Pageable pageable);
    Optional<CommentThread> findByIdAndPostId(Long id, Long postId);
}
