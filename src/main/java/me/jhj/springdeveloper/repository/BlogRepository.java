package me.jhj.springdeveloper.repository;

import me.jhj.springdeveloper.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
    //findById() , findAll() , save() , saveAll() , deleteById() , deleteALl()
}
