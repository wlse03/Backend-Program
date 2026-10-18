package me.jhj.springdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    // 요청 url과 요청 방식(GET, PUT, POST, PATCH, DELETE) 에 따라 그요청을 실행 할 메서드 매핑
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam ("code") int code) { //http://localhost:8080/quiz?code=1 code= 는 "code" 문자열, 1은 int code
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("created"); //201
            case 2:
                return ResponseEntity.badRequest().body("BadRequest"); //400
            default:
                return ResponseEntity.ok().body("ok"); //200
        }
    }

    @PostMapping("/quiz")
    public ResponseEntity<String> quiz2(@RequestBody Code code){//@RequestBody- body에서 적은 json문자열을 객체로 변환
        switch (code.value()){
            case 1:
                return ResponseEntity.status(403).body("Forbidden!"); //status에는 다른 숫가 와도 상관X
            /* 200: ok(성공), 201: created(생성성공), 204: no content(내용 없음), 400: bad request(잘못된 요청), 401: unauthorized(인증필요)
            403: forbidden(권한 없음), 404: not found(찾을 수 없음), 409: conflict(충돌), 500: internal server error(서버오류) */
            default:
                return ResponseEntity.ok().body("ok");
        }

    }
}

record Code(int value) {
// 순수하게 데이터를 담아 전달하기 위한 DTO(데이터 전달 객체)를 만들 때 사용하는 아주 간결한 클래스 형태
// 값을 보관하는 필드 (final int value), 생성자 (public Code(int value)), 값을 꺼내는 메서드 (code.value())
}