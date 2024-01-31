package com.sixman.fattle.api.service;

import com.sixman.fattle.entity.User;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public HttpStatus login(long userCode) {
        User user =  userRepository.findByUserCode(userCode);
        if (user == null) {
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.OK;
        }
    }

}
