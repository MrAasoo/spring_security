package com.aasoo.oauth2_jwt_db_user.repo;

import com.aasoo.oauth2_jwt_db_user.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, String> {

    Optional<UserAuth> findByUsername(String username);

    boolean existsByUsername(String username);
}
