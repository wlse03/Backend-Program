package me.jhj.springdeveloper.dto;


import lombok.Getter;
import me.jhj.springdeveloper.dao.Article;

@Getter
public class ArticleResponse {
    private String title;
    private String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
