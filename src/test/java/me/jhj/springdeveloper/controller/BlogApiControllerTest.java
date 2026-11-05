package me.jhj.springdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jhj.springdeveloper.dao.Article;
import me.jhj.springdeveloper.dto.ArticleCreationRequest;
import me.jhj.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogRepository blogRepository;

    @AfterEach
    public void cleanUp() {
        blogRepository.deleteAll();
    }

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

    @DisplayName("모든 블로그글 조회에 성공")
    @Test
    public void getArticle() throws Exception {
        //준비 given url, data 준비
        String url = "/api/articles";
        String title = "11월 5일";
        String content = "모든 블로그글 조회 테스트 코드 작성";
        blogRepository.save(Article.builder().title(title).content(content).build());

        //실행 when
        ResultActions resultActions= mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        //검증 then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }
}