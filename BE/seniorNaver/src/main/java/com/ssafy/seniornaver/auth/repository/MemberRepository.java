package com.ssafy.seniornaver.auth.repository;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByMemberId(String id);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByRefreshToken(String refreshToken);
    boolean existsByMemberId(String id);
    boolean existsByPassword(String password);
    boolean existsByMemberIdAndAuthProvider(String id, AuthProvider authProvider);
    Optional<Member> findByNickname(String nickname);

}
