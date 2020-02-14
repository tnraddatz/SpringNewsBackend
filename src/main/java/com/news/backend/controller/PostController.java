package com.news.backend.controller;

import com.news.backend.dao.model.CommentThread;
import com.news.backend.dao.model.Post;
import com.news.backend.dao.repository.CommentThreadRepository;
import com.news.backend.dao.repository.PostRepository;
import com.news.backend.exception.PostCreationFailedException;
import com.news.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentThreadRepository commentThreadRepository;

    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) throws PostCreationFailedException{
        postRepository.save(post);
        CommentThread plusThread = new CommentThread();
        CommentThread minusThread = new CommentThread();
        try {
            plusThread.setPost(post);
            plusThread.setThreadName(post.getTitle() + " plus");
            minusThread.setPost(post);
            minusThread.setThreadName(post.getTitle() + " minus");
            commentThreadRepository.save(plusThread);
            commentThreadRepository.save(minusThread);
        } catch (Exception e) {
            throw new PostCreationFailedException("Post Creation Failed:  Cannot create plus and minus thread");
        }
        return post;
    }

    @PutMapping("/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

}
