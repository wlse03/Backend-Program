package me.jhj.springdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jhj.springdeveloper.dao.Article;
import me.jhj.springdeveloper.dto.ArticleCreationRequest;
import me.jhj.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogRepository blogRepository;

    @DisplayName("addArticle : 블로그 글 추가 성공")
    @Test
    public void addArticle() throws Exception {
        // 준비 given : 저장할 데이터를 반환, 요청 url 준비
        String title = "junit 테스트를 통한 블로그 글 생성";
        String content = "수업 종료 10분전, 이테스트 성공후 점심식사";
        String url = "/api/articles";
        ArticleCreationRequest req = new ArticleCreationRequest(title,content);
        String articleStr = objectMapper.writeValueAsString(req);
        // 실행 when
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(articleStr));
        // 검증 then : DB애서 인출
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        Article article = articles.getFirst();
        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
    }
}