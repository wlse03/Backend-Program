package me.jhj.springdeveloper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        // 스프링 부트 실행
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }

}
//DAO(data access object) db와 연결되는 자바 객체
//DTO(data transfer object) 데이터 전달 객체 계층간
//필요한 이유 데이터베이스의 민감한 정보가 노출되거나 의도치 않게변경 되는 것을 방지하기 위해 사용
//컨트롤러로(DTO) -> 서비스(DTO-Entity(DAO)) -> 리포지토리 -> DB
