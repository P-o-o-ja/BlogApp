package com.pooja.blogApp.service;

import com.pooja.blogApp.payloads.UserDto;

import java.util.List;


public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto user);
    void deleteUser(Integer userId);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUser();
}

