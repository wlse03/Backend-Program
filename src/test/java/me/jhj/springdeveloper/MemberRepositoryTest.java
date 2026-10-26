package me.jhj.springdeveloper;

import org.hibernate.internal.FastSessionServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest //리포지토리만의 필요한 스프링 빈을 만들어주는 어노테이션
class MemberRepositoryTest {
    //메서드가 여러개라고 해서 레코드가 여러개는 아니다 메서드가 실행 될 때마다 롤백을 해줌(@Transactional)
    @Autowired
    private MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블의 모른 레코드 가져오기")
    @Test
    public void getAllMembers() {
        // 준비 given

        // 실행 when
        List<Member> members = memberRepository.findAll(); // select * from member;

        // 검증 then
        assertThat(members.size()).isEqualTo(3);

    }
    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 id가 2인 레코드 가져오기")
    @Test
    public void getMemberById2() {
        // 준비 given


        // 실행 when
        Member member = memberRepository.findById(2L).get(); //select * from member where id = 2;

        // 검증 then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 name이 B인 레코드 가져오기")
    @Test
    public void getMemberByName() {
        // 준비 given

        // 실행 when
        List<Member> member = memberRepository.findByName("B"); //select * from member where name = B;

//        // 검증 then
//        assertThat(members.size()).isEqualTo(1);
//        assertThat(members.get(0).getId()).isEqualTo(2);
        assertThat(member.get(0).getId()).isEqualTo(2L);

//        MemberRepository의 save(), saveAll(), deleteById(),deleteAll()
    }

    @DisplayName("하나의 Member 객체를 DB에 저장")
    @Test
    public void saveMember() {
        //준비 given
        Member member = new Member("A");//여기에 객체 값 id를 주면 insert가 안 되고 update가 되 버림

        //실행 when
        memberRepository.save(member);

        //검증 then
        List<Member> members = memberRepository.findAll();
        assertThat(members.get(0).getId()).isEqualTo(1L);
    }

    @DisplayName("3개의 Member 객체를 DB에 저장")
    @Test
    public void saveMembers() {
        //준비 given
        //List<Member> members = new ArrayList<>();

        List<Member> members = List.of(new Member("A"), new Member("B"), new Member("C"));

        //실행 when
        memberRepository.saveAll(members);

        //검증 then
        assertThat(memberRepository.findAll().size()).isEqualTo(3);
    }
    @Sql("/insert-members.sql")
    @DisplayName("id가 2인 레코드 삭제")
    @Test
    public void deleteMemberById() {
        //준비 given : @Sql로 대신

        //실행 when
        memberRepository.deleteById(2L);

        //검증 then
        Optional<Member> member = memberRepository.findById(2L);
        assertThat(member.isEmpty()).isTrue();
    }

    @Sql("/insert-members.sql")
    @DisplayName("모든 레코드 삭제")
    @Test
    public void deleteAllMembers() {
        //준비 given : @Sql로 대신

        //실행 when
        memberRepository.deleteAll();

        //검증 then
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(0);
    }
    //내부적으로 사용되는 영속성 컨텍스트의 변경감지(dirty-checking) 기능을 이용한 레코드 변경
    @Sql("/insert-members.sql")
    @DisplayName("변경감지 기능을 이용해 id가 2인 레코드의 name을 홍길동으로 변경")
    @Test
    public void updateMember() {
        //준비 given
        Member member = memberRepository.findById(2L).get();

        //실행 when
        member.setName("홍길동");

        //검증 then
        member = memberRepository.findById(2L).get();
        assertThat(member.getName()).isEqualTo("홍길동");
    }

}

