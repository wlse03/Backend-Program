package me.jhj.springdeveloper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
