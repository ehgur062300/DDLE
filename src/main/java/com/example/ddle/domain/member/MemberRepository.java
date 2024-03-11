package com.example.ddle.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    Optional<Member> findByEmail(String email);
}
