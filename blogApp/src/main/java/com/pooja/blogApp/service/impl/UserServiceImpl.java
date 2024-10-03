package com.pooja.blogApp.service.impl;

import com.pooja.blogApp.config.AppConstants;
import com.pooja.blogApp.entity.Role;
import com.pooja.blogApp.entity.User;
import com.pooja.blogApp.exception.ResourceNotFoundException;
import com.pooja.blogApp.payloads.UserDto;
import com.pooja.blogApp.repository.RoleRepository;
import com.pooja.blogApp.repository.UserRepository;
import com.pooja.blogApp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser=this.userRepository.save(user);
        return this.userToDto(savedUser);
    }
    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        this.userRepository.delete(user);
    }
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
          User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
          user.setId(userDto.getId());
          user.setName(userDto.getName());
          user.setEmail(userDto.getEmail());
          user.setPassword(userDto.getPassword());
          user.setAbout(userDto.getAbout());
          User updatedUser=this.userRepository.save(user);
          return this.userToDto(updatedUser);
    }
    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        return this.userToDto(user);
    }
    @Override
    public List<UserDto> getAllUser() {
        List<User> users=this.userRepository.findAll();
        List<UserDto> userDtoList=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(user.getPassword());
        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepository.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }


    public User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto, User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
