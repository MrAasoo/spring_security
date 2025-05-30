package com.aasoo.basic_auth_db_user.dao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUser {
    @JsonProperty("UserName")
    private String username;
    @JsonProperty("Password")
    private String password;
}
