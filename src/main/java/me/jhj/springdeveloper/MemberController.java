package me.jhj.springdeveloper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    //요청을 받아서 비즈니스 로직으로 연결 하는 역할
    // http://localhost:8080/member 연결
    @GetMapping("/member")
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }

    //회원정보를 등록하는 요청
    // http://localhost:8080/member
    @PostMapping("/member")
    public ResponseEntity<Member>createMember(@RequestBody Member member){
        //비지니스 로직 호출
        return ResponseEntity.ok(memberService.saveMember(member));
        //같은 뜻 return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(member));
    }
}
