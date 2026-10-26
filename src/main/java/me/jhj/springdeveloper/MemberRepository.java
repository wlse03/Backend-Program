package me.jhj.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
//    Member findByName(String name);
    // findAll() select * form member;
    // findById() select * from member where id = ?
    // deleteAll() delete from member;
    // deleteById() delete from member where id =  ?
    // save() insert into member(id, name) values (?, ?) , update member set.. where id = ?

    // findByName() select * from member where name = ?
    public List<Member> findByName(String name);

    // select * from member where email = ?
    // public Optional<Member> findByEmail(String email);

    // select * from member where name = ? and email = ?
    // public Optional<Member> findByNamAndEmail(String name, String email);

}
