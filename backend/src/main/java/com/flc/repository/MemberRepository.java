package com.flc.repository;

import com.flc.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * Repository interface for Member entity
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    
    @Query("SELECT m FROM Member m WHERE m.name LIKE %:name%")
    List<Member> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT m FROM Member m ORDER BY m.memberId DESC LIMIT 10")
    List<Member> findRecentMembers();
}
