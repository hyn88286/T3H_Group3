package com.the.doanthucung.service;

import com.the.doanthucung.model.dto.UserDto;

public interface IuserService {
    UserDto findUserByUsername(String username);
}
