package com.raf.airportuserservice.repository;

import com.raf.airportuserservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE user SET miles = :newMiles WHERE id = :userId", nativeQuery = true)
    Integer setMiles(@Param("newMiles") Long newMiles, @Param("userId") Long userId);
}
