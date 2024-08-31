package com.project.springSecurity.services;


import java.util.List;

import com.project.springSecurity.dto.PostDTO;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);
}
