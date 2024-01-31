package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.request.SignUpRequest;
import com.sixman.fattle.dto.response.UserInfoResponse;
import com.sixman.fattle.entity.User;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public HttpStatus login(long userCode) {
        User user =  userRepository.getUser(userCode);
        if (user == null) {
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.OK;
        }
    }

    public void signUp(SignUpRequest request) {
        userRepository.joinUser(request);
    }

    public HttpStatus checkNickname(String nickname) {
        int cnt = userRepository.checkNickname(nickname);

        if (cnt == 0) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    public UserInfoResponse userInfo(long userCode) {
        return userRepository.getUserInfo(userCode);
    }
}
