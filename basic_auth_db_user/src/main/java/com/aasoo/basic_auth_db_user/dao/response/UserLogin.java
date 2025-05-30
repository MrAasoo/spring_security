package com.aasoo.basic_auth_db_user.dao.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    @JsonProperty("UserName")
    private String username;
    @JsonProperty("Role")
    private String role;
}
