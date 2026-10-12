package me.jhj.springdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    // 요청 url과 요청 방식(GET, PUT, POST, PATCH, DELETE) 에 따라 그요청을 실행 할 메서드 매핑
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam ("code") int code) {
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
    public ResponseEntity<String> quiz2(@RequestBody Code code){
        switch (code.value()){
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok().body("ok");
        }

    }
}

record Code(int value) {

}