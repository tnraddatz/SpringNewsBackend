package com.news.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.backend.dao.model.Comment;
import com.news.backend.dao.model.CommentThread;
import com.news.backend.dao.model.Post;
import com.news.backend.dao.repository.CommentRepository;
import com.news.backend.dao.repository.CommentThreadRepository;
import com.news.backend.dao.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private CommentThreadRepository commentThreadRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostController postController;

    @Autowired
    private CommentController commentController;

    private Pageable page;

    @BeforeEach
    void initTestCase() {
        CommentController commentController = new CommentController(commentRepository, commentThreadRepository);
        page = Pageable.unpaged();
    }

    @Test
    @Transactional
    void createCommentTest(){
        Post postRequest = new Post();
        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setOutletName("TestNetwork");
        Post post = postController.createPost(postRequest);
        Page<CommentThread> commentThread = commentThreadRepository.findByPostId(post.getId(), page);
        List<CommentThread> commentThreadList = commentThread.getContent();
        CommentThread plusCommentThread;
        CommentThread minusCommentThread;
        if (commentThreadList.get(0).getThreadName().contains("plus")) {
            plusCommentThread = commentThreadList.get(0);
            minusCommentThread = commentThreadList.get(1);
        } else {
            plusCommentThread = commentThreadList.get(1);
            minusCommentThread = commentThreadList.get(0);
        }
        Comment plusCommentResponse = commentController.createComment(plusCommentThread.getId(), new Comment("Plus Comment Text"));
        Comment minusCommentResponse = commentController.createComment(minusCommentThread.getId(), new Comment("Minus Comment Text"));

        assertEquals(plusCommentResponse.getCommentThread().getId(), plusCommentThread.getId());
        assertEquals(minusCommentResponse.getCommentThread().getId(), minusCommentThread.getId());
        assertEquals(plusCommentThread.getPost().getId(), post.getId());
        assertEquals(minusCommentThread.getPost().getId(), post.getId());
    }

    @Test
    @Transactional
    void deleteComment (){
        Post postRequest = new Post();
        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setOutletName("TestNetwork");
        Post post = postController.createPost(postRequest);
        Page<CommentThread> commentThread = commentThreadRepository.findByPostId(post.getId(), page);
        List<CommentThread> commentThreadList = commentThread.getContent();
        CommentThread plusCommentThread;
        CommentThread minusCommentThread;
        if (commentThreadList.get(0).getThreadName().contains("plus")) {
            plusCommentThread = commentThreadList.get(0);
            minusCommentThread = commentThreadList.get(1);
        } else {
            plusCommentThread = commentThreadList.get(1);
            minusCommentThread = commentThreadList.get(0);
        }
        Comment plusCommentResponse = commentController.createComment(plusCommentThread.getId(), new Comment("Plus Comment Text"));
        Comment minusCommentResponse = commentController.createComment(minusCommentThread.getId(), new Comment("Minus Comment Text"));

        commentController.deleteComment(plusCommentThread.getId(), plusCommentResponse.getId());
        commentController.deleteComment(minusCommentThread.getId(), minusCommentResponse.getId());

        assertEquals(commentRepository.findByCommentThreadId(plusCommentThread.getId(), page).getContent().size(), 0);
        assertEquals(commentRepository.findByCommentThreadId(minusCommentThread.getId(), page).getContent().size(), 0);
    }

    @Test
    @Transactional
    void updateComment (){
        Post postRequest = new Post();
        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setOutletName("TestNetwork");
        Post post = postController.createPost(postRequest);
        Page<CommentThread> commentThread = commentThreadRepository.findByPostId(post.getId(), page);
        List<CommentThread> commentThreadList = commentThread.getContent();
        CommentThread plusCommentThread;
        CommentThread minusCommentThread;
        if (commentThreadList.get(0).getThreadName().contains("plus")) {
            plusCommentThread = commentThreadList.get(0);
            minusCommentThread = commentThreadList.get(1);
        } else {
            plusCommentThread = commentThreadList.get(1);
            minusCommentThread = commentThreadList.get(0);
        }
        Comment plusCommentResponse = commentController.createComment(plusCommentThread.getId(), new Comment("Plus Comment Text"));
        Comment minusCommentResponse = commentController.createComment(minusCommentThread.getId(), new Comment("Minus Comment Text"));

        commentController.updateComment(plusCommentThread.getId(), plusCommentResponse.getId(), new Comment("Plus Plus Comment Text"));
        commentController.updateComment(minusCommentThread.getId(), minusCommentResponse.getId(), new Comment("Minus Minus Comment Text"));
        assertEquals(commentRepository.findByIdAndCommentThreadId(plusCommentResponse.getId(), plusCommentThread.getId()).orElse(null).getCommentText(), "Plus Plus Comment Text");
        assertEquals(commentRepository.findByIdAndCommentThreadId(minusCommentResponse.getId(), minusCommentThread.getId()).orElse(null).getCommentText(), "Minus Minus Comment Text");

    }

}
