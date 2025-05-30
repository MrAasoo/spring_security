package com.aasoo.basic_auth_db_user.repo;

import com.aasoo.basic_auth_db_user.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
    Optional<UserAuth> findByUserName(String userName);
}
