package me.jhj.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jhj.springdeveloper.dao.Article;
import me.jhj.springdeveloper.dto.ArticleCreationRequest;
import me.jhj.springdeveloper.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest articleCreationRequest) {
        return blogRepository.save(articleCreationRequest.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }
}
