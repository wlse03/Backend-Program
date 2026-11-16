package me.jhj.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jhj.springdeveloper.dao.Article;
import me.jhj.springdeveloper.dto.ArticleCreationRequest;
import me.jhj.springdeveloper.dto.ArticleUpdateRequest;
import me.jhj.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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

    public Article getById(@PathVariable Long id) {
        return blogRepository.findById(id).get();
    }

    public void deleteById(@PathVariable Long id) {
        blogRepository.deleteById(id);
    }

//    public Article updateArticle(Long id, ArticleUpdateRequest req) {
//        Article article = blogRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Article not found"));
//        article.update(req.getTitle(), req.getContent());
//        return blogRepository.save(article);
//    }

    @Transactional
    public Article updateArticle(Long id, ArticleUpdateRequest req) {
        Article article = blogRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Article not found"));
        article.update(req.getTitle(), req.getContent());
        return article;
    }
}


