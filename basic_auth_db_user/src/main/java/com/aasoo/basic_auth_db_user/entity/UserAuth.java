package com.aasoo.basic_auth_db_user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_user_auth")
public class UserAuth {

    @NotNull
    @Id
    private String userName;

    private String password;

    private String role;

}
