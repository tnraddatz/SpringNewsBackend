package com.news.backend.dao.repository;

import com.news.backend.dao.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

//    @Override
//    @SuppressWarnings("unchecked")
//    default Post save(Post entity)
//    {
//        return saveSafely(entity);
//    }

}
