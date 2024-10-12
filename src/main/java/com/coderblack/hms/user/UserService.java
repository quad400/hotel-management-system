package com.coderblack.hms.user;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.user.request.UserPermissionRequest;
import com.coderblack.hms.user.request.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public BaseResponse<UserResponse> getUser(String userId) throws AccountNotFoundException, AccountLockedException {
        var user = userRepository.findById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new AccountNotFoundException(String.format("User with this ID:%s not found", userId)));

        if (user.isAccountLocked()) {
            throw new AccountLockedException("This Account has been locked, kindly contact admin to unlock");
        }
        return new BaseResponse<UserResponse>("User fetched successfully", user);
    }

    public DefaultResponse updateUser(UserRequest request, Authentication currentUser) throws AccountNotFoundException, AccountLockedException {
        var userId = ((User) currentUser.getPrincipal()).getId();
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("User with this ID:%s not found", userId)));

        if (existingUser.isAccountLocked()) {
            throw new AccountLockedException("This Account has been locked, kindly contact admin to unlock");
        }
        userMapper.toUser(request, existingUser);
        User user = userRepository.save(existingUser);

        return new DefaultResponse("User Details Updated Successfully", user.getId());
    }

    public PageResponse<UserResponse> getUsers(String search, Pageable pageable) {
        Page<User> users;

        if (search != null && !search.isEmpty()) {
            users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, search, pageable);
        } else {
            users = userRepository.findAll(pageable);

        }
        List<UserResponse> userResponses = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return new PageResponse<>("Users Fetch Successfully",
                users.getNumber() + 1,
                users.getTotalElements(),
                users.getSize(),
                users.hasPrevious(),
                users.hasNext(),
                userResponses
        );
    }

    public DefaultResponse blockUser(String userId) throws AccountNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new AccountNotFoundException(String.format("User with this ID:%s not found", userId)));
        user.setAccountLocked(!user.isAccountLocked());
        userRepository.save(user);
        if (user.isAccountLocked()) {
            return new DefaultResponse("Account locked successfully", user.getId());
        } else {
            return new DefaultResponse("Account unlocked successfully", user.getId());
        }
    }

    public DefaultResponse updateUserPermissions(String userId, @Valid UserPermissionRequest request) throws AccountNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new AccountNotFoundException(String.format("User with this ID:%s not found", userId)));
        user.setRole(Role.valueOf(request.role()));
        userRepository.save(user);

        return new DefaultResponse(String.format("User Role Updated to %s successfully", request.role()), user.getId());
    }
}
