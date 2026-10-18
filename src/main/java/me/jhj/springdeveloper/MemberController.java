package me.jhj.springdeveloper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //이 클래는 외부 인터넷 접속을 받아 처리하고 결과를 JSON 형태로 변환 (CONTROLLER + @RESPONSEBODY)
public class MemberController {
    @Autowired //스프링컨테이너에서 맞는 빈을 찾아 자동으로 의존관계를 주입하는 어노테이션
    private MemberService memberService; //MemberService클래스 타입의 memberService라는 변수 초기화 @Autowired를 만나면 자동으로 변수 memberService에 주입
    //요청을 받아서 비즈니스 로직으로 연결 하는 역할
    // http://localhost:8080/member 연결
    @GetMapping("/member")
    //getAllMembers() 메서드 실행 후 List<Member> 바구니 담는다
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    } //MemberService 대문자가 아님 객체를 가져옴

    //회원정보를 등록하는 요청
    // http://localhost:8080/member
    @PostMapping("/member")
    //<Member>를 담는 바구니 ResponseEntity<>는 Spring Framework에서 HTTP 응답을 생성하고 관리하기 위한 클래스
    //@RequestBody Member member: body부분의 글자를 객체로 변환 후/ Member클래스의 형태로 바꾸고/ member라는 변수에 대입한다
    public ResponseEntity<Member>createMember(@RequestBody Member member){
        //비지니스 로직 호출
        return ResponseEntity.ok(memberService.saveMember(member));
        //회원 정보(member)를 memberService에 저장하고, 완료되면 200 ok가 된다
        //같은 뜻 return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(member));
    }
}
