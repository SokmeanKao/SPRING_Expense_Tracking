package org.example.srg3springminiproject.mapper;

import org.example.srg3springminiproject.model.User;
import org.example.srg3springminiproject.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponse toUserResponse(User user);
}
