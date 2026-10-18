package me.jhj.springdeveloper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MemberService {

    private final MemberRepository memberRepository;

     //멤버테이블에 있는 모든 레코들을 읽어서 반환
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }
    //리스트 타입이여서 [{}]로 반환

    //멤버저장
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }
    //{}로 단일 객체 반환
}
