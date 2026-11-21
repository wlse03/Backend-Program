package me.jhj.springdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jhj.springdeveloper.dao.Article;
import me.jhj.springdeveloper.dto.ArticleCreationRequest;
import me.jhj.springdeveloper.dto.ArticleUpdateRequest;
import me.jhj.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    // 용어- @BeforeEach: 각 @Test 메서드가 실행되기 "직전"마다 실행되는 메서드 표시
    // 한줄- 테스트가 시작될 때마다 아래 setUp()을 먼저 실행한다.
    void setUp() {
        // 용어- void: 돌려주는 값이 없다는 뜻
        // 한줄- 테스트 준비 메서드.
        blogRepository.deleteAll();
        // 용어- deleteAll(): JpaRepository가 주는 "전부 삭제" 메서드
        // 한줄- 이전 데이터가 남아 있지 않게 DB를 비운다. → 테스트끼리 서로 영향을 주지 않는다(독립성).
    }

    @DisplayName("addArticle : 블로그 글 생성에 성공한다")
    @Test
    public void addArticleWithStatus() throws Exception {
        // 용어- addArticleWithStatus: 위 원본 addArticle과 이름이 겹치면 안 돼서 With(~와 함께) + Status(상태 코드)를 붙인 이름
        // 한줄- 상태 코드까지 검증하는 글 생성 테스트 메서드.

        // Given
        String url = "/api/articles";
        // 한줄- 요청 보낼 주소.
        String title = "글 생성 테스트 제목";
        String content = "글 생성 테스트 내용";
        // 용어- String: 글자(문자열) 타입
        // 한줄- 요청에 보낼 제목과 내용.
        ArticleCreationRequest request = new ArticleCreationRequest(title, content);
        // 용어- new: 객체를 새로 만드는 키워드
        // 용어- ArticleCreationRequest(title, content): @AllArgsConstructor가 만든 생성자
        // 한줄- 제목과 내용을 담은 요청 DTO를 만든다.
        String requestBody = objectMapper.writeValueAsString(request);
        // 용어- writeValueAsString(): 객체를 JSON 문자열로 바꾸는 메서드
        // 한줄- 요청 DTO를 {"title":"...","content":"..."} 모양의 JSON 문자열로 바꾼다.

        // When
        ResultActions result = mockMvc.perform(post(url)
        // 용어- perform(): 가짜 요청을 실제로 보내는 메서드
        // 용어- post(url): POST 방식 요청을 만든다
                .contentType(MediaType.APPLICATION_JSON)
                // 용어- contentType(MediaType.APPLICATION_JSON): 본문이 JSON이라고 알림
                // 한줄- 요청 본문 형식을 JSON으로 지정한다.
                .content(requestBody));
                // 용어- content(): 요청 본문에 문자열을 넣음
                // 한줄- JSON을 담아 POST /api/articles 요청을 보낸다.

        // Then
        result.andExpect(status().isCreated());
        // 용어- andExpect(): 결과가 기대한 대로인지 검사
        // 용어- isCreated(): 상태 코드 201(새로 만들어짐). 컨트롤러가 HttpStatus.CREATED를 돌려주니까 201로 맞춘다
        // 한줄- ① 응답 상태 코드가 201인지 확인한다.
        List<Article> articles = blogRepository.findAll();
        // 용어- findAll(): DB의 글을 전부 가져오는 메서드
        // 한줄- 저장이 됐는지 DB에서 직접 꺼내 본다.
        assertThat(articles.size()).isEqualTo(1);
        // 용어- assertThat(A).isEqualTo(B): A가 B와 같아야 통과
        // 한줄- ③ DB에 글이 딱 1개인지 확인한다.
        Article article = articles.getFirst();
        // 용어- getFirst(): 리스트의 첫 번째 요소를 꺼냄 (Java 21부터)
        // 한줄- 저장된 글 하나를 꺼낸다.
        assertThat(article.getTitle()).isEqualTo(title);
        // 한줄- ③ DB에 저장된 제목이 보낸 제목과 같은지 확인한다.
        assertThat(article.getContent()).isEqualTo(content);
        // 한줄- ③ DB에 저장된 내용이 보낸 내용과 같은지 확인한다.
    }

    @DisplayName("findArticle : 블로그 글 하나 조회에 성공한다")
    @Test
    public void findArticle() throws Exception {
        // Given
        String url = "/api/articles/{id}";
        // 용어- {id}: 나중에 실제 값으로 바뀌는 자리 (URI 템플릿)
        // 한줄- 글 하나 조회 주소. {id}에는 아래에서 저장한 글의 id가 들어간다.
        String title = "글 하나 조회 테스트";
        String content = "findArticle 테스트 내용";
        // 한줄- 미리 저장할 제목과 내용.
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        // 용어- savedArticle: saved(저장된) + Article. save()는 id가 채워진 객체를 돌려준다
        // 한줄- 글 1건을 저장하고, id가 들어 있는 결과를 받아 둔다.

        // When
        ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()).accept(MediaType.APPLICATION_JSON));
        // 용어- get(url, 값): url의 {id} 자리에 두 번째 값을 넣어 준다
        // 용어- getId(): Article의 @Getter가 만든 id 꺼내기 메서드. 1L처럼 직접 적지 않고 이걸 쓴다(작성 규칙)
        // 한줄- GET /api/articles/{저장된 id} 요청을 보낸다.

        // Then
        resultActions.andExpect(status().isOk())
        // 한줄- ① 200 OK인지 확인한다.
                .andExpect(jsonPath("$.title").value(title))
                // 용어- $.title: 응답 JSON 객체의 title 값 (배열이 아니라 객체 하나라서 [0] 없이 바로 접근)
                // 한줄- ② 응답 제목이 저장한 제목과 같은지 확인한다.
                .andExpect(jsonPath("$.content").value(content));
                // 한줄- ② 응답 내용이 저장한 내용과 같은지 확인한다.
    }

    @DisplayName("updateArticle : 블로그 글 수정에 성공한다")
    @Test
    public void updateArticle() throws Exception {
        // Given
        String url = "/api/articles/{id}";
        // 한줄- 수정 요청 주소.
        Article savedArticle = blogRepository.save(Article.builder().title("수정 전 제목").content("수정 전 내용").build());
        // 한줄- 수정할 글 1건을 먼저 저장한다.
        String newTitle = "수정 후 제목";
        String newContent = "수정 후 내용";
        // 용어- newTitle: new(새) + Title. 바꿀 값이라는 뜻의 변수 이름
        // 한줄- 바꿀 새 제목과 새 내용.
        ArticleUpdateRequest request = new ArticleUpdateRequest(newTitle, newContent);
        // 용어- ArticleUpdateRequest: 이 프로젝트의 수정 요청 DTO (과제 문서의 UpdateArticleRequest와 같은 역할)
        // 한줄- 새 제목·내용을 담은 수정 요청 객체를 만든다.
        String requestBody = objectMapper.writeValueAsString(request);
        // 용어- requestBody: 요청 본문(body)에 넣을 문자열이라는 뜻의 변수 이름
        // 한줄- 수정 요청을 JSON 문자열로 바꾼다.

        // When
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
        // 용어- put(url, id): PUT 방식 요청을 만들고 {id} 자리에 저장한 글의 id를 넣는다
                .contentType(MediaType.APPLICATION_JSON)
                // 한줄- 본문이 JSON이라고 알린다.
                .content(requestBody));
                // 한줄- 본문에 수정 JSON을 넣어서 PUT /api/articles/{id} 요청을 보낸다.

        // Then
        result.andExpect(status().isOk());
        // 한줄- ① 200 OK인지 확인한다.
        Article article = blogRepository.findById(savedArticle.getId()).orElseThrow();
        // 용어- findById(): id로 찾아서 Optional(있을 수도 없을 수도 있는 상자)을 돌려줌
        // 용어- orElseThrow(): 상자가 비었으면 예외, 있으면 꺼냄
        // 한줄- ③ 응답만 믿지 않고 DB에서 글을 다시 꺼낸다. (@Transactional이 빠지면 여기서 잡힌다)
        assertThat(article.getTitle()).isEqualTo(newTitle);
        // 한줄- ③ DB의 제목이 새 제목으로 바뀌었는지 확인한다.
        assertThat(article.getContent()).isEqualTo(newContent);
        // 한줄- ③ DB의 내용이 새 내용으로 바뀌었는지 확인한다.
    }

    @DisplayName("deleteArticle : 블로그 글 삭제에 성공한다")
    @Test
    public void deleteArticle() throws Exception {
        // Given
        String url = "/api/articles/{id}";
        // 한줄- 삭제 요청 주소.
        Article savedArticle = blogRepository.save(Article.builder().title("삭제할 제목").content("삭제할 내용").build());
        // 한줄- 지울 글 1건을 먼저 저장한다.

        // When
        ResultActions result = mockMvc.perform(delete(url, savedArticle.getId()));
        // 용어- delete(url, id): DELETE 방식 요청을 만든다. 본문이 없어서 contentType이 필요 없다
        // 한줄- DELETE /api/articles/{저장된 id} 요청을 보낸다.

        // Then
        result.andExpect(status().isOk());
        // 한줄- ① 200 OK인지 확인한다. (컨트롤러가 ResponseEntity.ok().build()를 돌려줌)
        List<Article> articles = blogRepository.findAll();
        // 한줄- DB에 남은 글을 전부 꺼낸다.
        assertThat(articles).isEmpty();
        // 용어- isEmpty(): 리스트가 비어 있어야 통과
        // 한줄- ③ 글이 하나도 남아 있지 않은지 확인한다.
    }

    @DisplayName("findAllArticles : 블로그 글 목록 조회에 성공한다")
    @Test
    public void findAllArticles() throws Exception {
        // Given
        String url = "/api/articles";
        // 한줄- 목록 조회 주소.
        String title1 = "첫 번째 글";
        String content1 = "첫 번째 내용";
        String title2 = "두 번째 글";
        String content2 = "두 번째 내용";
        // 용어- title1, title2: 같은 종류 값이 여러 개라 뒤에 번호를 붙인 변수 이름
        // 한줄- 저장할 글 2건의 제목과 내용.
        blogRepository.save(Article.builder().title(title1).content(content1).build());
        // 한줄- 첫 번째 글을 저장한다.
        blogRepository.save(Article.builder().title(title2).content(content2).build());
        // 한줄- 두 번째 글을 저장한다. (과제 조건: 2건 이상)

        // When
        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // 한줄- GET /api/articles 요청을 보낸다.

        // Then
        resultActions.andExpect(status().isOk())
        // 한줄- ① 200 OK인지 확인한다.
                .andExpect(jsonPath("$.length()").value(2))
                // 용어- $.length(): 응답 JSON 배열의 길이(개수)
                // 한줄- ② 응답 배열에 글이 2개인지 확인한다.
                .andExpect(jsonPath("$[0].title").value(title1))
                // 한줄- ③ 첫 번째 글의 제목 확인.
                .andExpect(jsonPath("$[0].content").value(content1))
                // 한줄- ③ 첫 번째 글의 내용 확인.
                .andExpect(jsonPath("$[1].title").value(title2))
                // 용어- $[1]: 배열의 두 번째 요소 (0부터 센다)
                // 한줄- ③ 두 번째 글의 제목 확인.
                .andExpect(jsonPath("$[1].content").value(content2));
                // 한줄- ③ 두 번째 글의 내용 확인.
    }
}
