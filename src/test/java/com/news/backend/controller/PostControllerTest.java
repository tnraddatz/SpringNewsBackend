package com.news.backend.controller;

import com.news.backend.dao.model.CommentThread;
import com.news.backend.dao.model.Post;
import com.news.backend.dao.repository.CommentThreadRepository;
import com.news.backend.dao.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private CommentThreadRepository commentThreadRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PostController postController;
    private Pageable page;

    @BeforeEach
    void initTestCase() {
        postController = new PostController(postRepository, commentThreadRepository);
        page = Pageable.unpaged();
    }

    @Test
    @Transactional
    void getAllPostsTest(){
        for (int i =0; i < 5; i++) {
            Post postRequest = new Post();
            postRequest.setTitle("Test Title" + i);
            postRequest.setDescription("Test Description" + i);
            postRequest.setOutletName("TestNetwork");
            postController.createPost(postRequest);
        }
        List<Post> posts = postRepository.findAll();
        List<CommentThread> commentThreads = commentThreadRepository.findAll();
        assertEquals(5, posts.size());
        assertEquals(10, commentThreads.size());
    }

    @Test
    @Transactional
    void createPostTest() {
        Post postRequest = new Post();
        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setOutletName("TestNetwork");
        Post postResponse = postController.createPost(postRequest);
        assertThat(postResponse.getVotes()).isEqualTo(0);
        assertThat(postResponse.getTitle()).isEqualTo(postRequest.getTitle());
    }

    @Test
    @Transactional
    void deletePostTest(){
        Post postRequest = new Post();
        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setOutletName("TestNetwork");
        Post postCreateResponse = postController.createPost(postRequest);
        postController.deletePost(postCreateResponse.getId());

    }

//    @Test
//    void createPostTest2() throws Exception {
//        Post postRequest = new Post();
//        postRequest.setTitle("Test Post");
//        postRequest.setDescription("Test Description");
//
//        MvcResult mvcResult = mockMvc.perform(post("/posts")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(postRequest)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = mvcResult.getResponse().getContentAsString();
//        System.out.println(response);
//    }
}

