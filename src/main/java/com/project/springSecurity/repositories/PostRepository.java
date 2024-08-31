package com.project.springSecurity.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springSecurity.entities.PostEntity;


@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
