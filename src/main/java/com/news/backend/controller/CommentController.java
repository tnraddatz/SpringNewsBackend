package com.news.backend.controller;

import com.news.backend.dao.model.Comment;
import com.news.backend.dao.repository.CommentRepository;
import com.news.backend.dao.repository.CommentThreadRepository;
import com.news.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentThreadRepository commentThreadRepository;

    @GetMapping("/posts/thread/{commentThreadId}/comments")
    public Page<Comment> getAllCommentsByCommendThreadId(@PathVariable (value = "commentThreadId") Long commentThreadId,
                                                Pageable pageable) {
        return commentRepository.findByCommentThreadId(commentThreadId, pageable);
    }

    @PostMapping("/posts/thread/{commentThreadId}/comments")
    public Comment createComment(@PathVariable (value = "commentThreadId") Long commentThreadId,
                                 @Valid @RequestBody Comment comment) {
        return commentThreadRepository.findById(commentThreadId).map(commentThread -> {
            comment.setCommentThread(commentThread);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Comment Thread Id " + commentThreadId + " not found"));
    }

    @PutMapping("/posts/thread/{commentThreadId}/comments/{commentId}")
    public Comment updateComment(@PathVariable (value = "commentThreadId") Long commentThreadId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if(!commentThreadRepository.existsById(commentThreadId)) {
            throw new ResourceNotFoundException("Comment Thread Id " + commentThreadId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setCommentText(commentRequest.getCommentText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping("/posts/thread/{commentThreadId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "commentThreadId") Long commentThreadId,
                                           @PathVariable (value = "commentId") Long commentId) {
        return commentRepository.findByIdAndCommentThreadId(commentId, commentThreadId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and Comment Thread Id " + commentThreadId));
    }
}

