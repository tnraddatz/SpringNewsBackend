package com.news.backend.controller;

import com.news.backend.dao.model.CommentThread;
import com.news.backend.dao.model.Post;
import com.news.backend.dao.repository.CommentThreadRepository;
import com.news.backend.dao.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private CommentThreadRepository commentThreadRepository = Mockito.mock(CommentThreadRepository.class);
    private PostRepository postRepository = Mockito.mock(PostRepository.class);
    PostController postController;
    Pageable page;

    @BeforeEach
    void initTestCase() {
        postController = new PostController(postRepository, commentThreadRepository);
        when(commentThreadRepository.save(any(CommentThread.class))).then(returnsFirstArg());
        when(postRepository.save(any(Post.class))).then(returnsFirstArg());
        page = Pageable.unpaged();
    }

    @Test
    void getAllPostsTest(){

    }

//    @Test
//    void createPostTest2() throws Exception {
//        Post postRequest = new Post();
//        postRequest .setTitle("Test Post");
//        postRequest .setDescription("Test Description");
//
//        MvcResult mvcResult = mockMvc.perform(post("/posts")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(postRequest)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = mvcResult.getResponse().getContentAsString();
//        System.out.println(response);
//
//
//    }

    @Test
    void createPostTest() {
        Post postRequest = new Post();
        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setOutletName("Fox");
        Post postResponse = postController.createPost(postRequest);
        assertThat(postResponse.getTitle()).isEqualTo(postRequest.getTitle());
    }

}

