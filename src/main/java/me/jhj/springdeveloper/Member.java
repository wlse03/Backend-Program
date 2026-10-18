package me.jhj.springdeveloper;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED) //텅빈 기본 생성자를 자동으로 생성 PROTECTED= 관계자 전용
@AllArgsConstructor //클래스의 모든 필드 값을 파라미터로 받는 생성자를 자동 생성
@Getter //ENTITY 필드를 조회하여 가져올때 사용 하는 어노테이션
@Entity //DB 테이블에 대응 하는 하나의 클래스
public class Member {
    @Id //고유키를 설정하는 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID같이 번호를 부여할때 자동으로 숫자를 부여 하는 어노테이션 *EMAIL, NAME 등 고유키를 정할땐 사용 x
    @Column(name="id", updatable=false) //UPDATABLE= FALSE 부여된 코드 수정불가능
    private Long id;

    @Column(name="name", nullable = false) //NULLABLE = FALSE 칸을 비워두면 안됨
    private String name;

    public Member(String name) {
        this.name = name;
    }
    // 위에 @AllArgsConstructor 해줬지만 따로 객채 생성한 이유는 nullable로 빈칸을 지정해주지 말라고 설정 해서 직접 해줘야 함
}
