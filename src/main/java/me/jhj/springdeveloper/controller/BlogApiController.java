package me.jhj.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.jhj.springdeveloper.dao.Article;
import me.jhj.springdeveloper.dto.ArticleCreationRequest;
import me.jhj.springdeveloper.dto.ArticleResponse;
import me.jhj.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest articleCreationRequest) {
        //서비스에게 블로글 글 등록 의뢰
        Article savedArticle = blogService.save(articleCreationRequest);
        //성공하면 ResponseEntity 객체 반환

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);


    }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
//        List<Article> savedArticles = blogService.findAll();
//        List<ArticleResponse> articleResponseList = new ArrayList<>();
//        for (Article article : savedArticles) {
//            articleResponseList.add(new ArticleResponse(article));
//        }
//        return ResponseEntity.ok().body(articleResponseList);
        List<ArticleResponse> articleResponseList = blogService.findAll().stream().map(ArticleResponse::new).toList();
        return ResponseEntity.ok().body(articleResponseList);
    }
}




