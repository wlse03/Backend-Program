package me.jhj.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

// [필수 추가] MockMvc 핵심 static 메서드 3가지
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; //객체를 json 문자열로 변환해주는 API

    @DisplayName("GET/quiz?code=1 => 응답코드 201, 응답 본문은 Created 리턴")
    @Test
    public void getQuiz1() throws Exception {
        // 준비 given
        final String url = "/quiz";

        // 실행 when
        final ResultActions result = mockMvc.perform(get(url).param("code", "1"));

        // 검증 then
        result.andExpect(status().isCreated()).andExpect(content().string("created"));
    }

    @DisplayName("GET/quiz?code=2 => 응답코드 400, 응답 본문은 BadRequest 리턴")
    @Test
    public void getQuiz2() throws Exception {
        // 준비 given
        final String url = "/quiz";

        // 실행 when
        final ResultActions result = mockMvc.perform(get(url).param("code", "2"));

        // 검증 then
        result.andExpect(status().isBadRequest()).andExpect(content().string("BadRequest"));
    }

    @DisplayName("POST/ quiz 요청, 요청 바디에 {'value' : 1}이면 응답 코드는 403, 응답 본문은 Forbidden!")
    @Test
    public void postQuiz1() throws Exception {
        //준비 given
        final String url = "/quiz";

        //실행 when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new Code(1))));
        //검증 then
        result.andExpect(status().isForbidden()).andExpect(content().string("Forbidden!"));
    }

    @DisplayName("POST /quiz 요청, 요청 바디에 {value': 2}이면 응답코드는 200, 응답 본문은 ok")
    @Test
    public void postQuiz2() throws Exception {
        //준비 given
        final String url = "/quiz";

        //실행 when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new Code(2))));
        //검증 then
        result.andExpect(status().isOk()).andExpect(content().string("ok"));
    }
}