package com.news.backend.dao.repository;

import com.news.backend.dao.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByCommentThreadId(Long commentThreadId, Pageable pageable);
    Optional<Comment> findByIdAndCommentThreadId(Long id, Long commentThreadId);

}
