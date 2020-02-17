package com.news.backend.controller;

import com.news.backend.dao.model.CommentThread;
import com.news.backend.dao.repository.CommentRepository;
import com.news.backend.dao.repository.CommentThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentThreadController {

    private final CommentRepository commentRepository;
    private final CommentThreadRepository commentThreadRepository;

    @GetMapping("/posts/{postId}/commentThreads")
    public Page<CommentThread> getAllCommentThreadsByPostId(@PathVariable (value = "postId") Long postId,
                                                         Pageable pageable) {
        return commentThreadRepository.findByPostId(postId, pageable);
    }
}