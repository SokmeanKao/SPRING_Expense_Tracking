package org.example.srg3springminiproject.repository;

import org.apache.ibatis.annotations.*;
import org.example.srg3springminiproject.model.User;
import org.example.srg3springminiproject.model.request.ForgetRequest;
import org.example.srg3springminiproject.model.response.UserResponse;

import java.util.UUID;

@Mapper
public interface UserRepository {
    @Results(id = "authMapper", value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "profileImage", column = "profile_image")
    })
    @Select("INSERT INTO users_tb (email, password, profile_image) VALUES (#{user.email}, #{user.password}, #{user.profileImage}) RETURNING *")
    User save(@Param("user") User newUser);

    @Select("SELECT * FROM users_tb WHERE email = #{email}")
    @ResultMap("authMapper")
    User getUserByEmail(@Param("email") String email);

    @Select("UPDATE users_tb SET password = #{pass.password} WHERE email = #{email} RETURNING *")
    @ResultMap("authMapper")
    User updatePassword(@Param("pass") ForgetRequest forgetRequest , @Param("email") String email);

    @Select("SELECT * FROM users_tb WHERE user_id = CAST(#{id} AS UUID)")
    @Result(property = "userId",column = "user_id")
    @Result(property = "email", column = "email")
    @Result(property = "profileImage", column = "profile_image")
    UserResponse getUserById(@Param("userId") UUID id);
}
