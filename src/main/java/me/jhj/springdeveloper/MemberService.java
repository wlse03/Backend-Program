package me.jhj.springdeveloper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

     //멤버테이블에 있는 모든 레코들을 읽어서 반환
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }
    //멤버저장
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }
}
