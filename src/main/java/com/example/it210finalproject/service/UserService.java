package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.Role;
import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.dto.LoginForm;
import com.example.it210finalproject.model.dto.RegisterForm;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BcryptPassword bcryptPassword;

    public UserService(UserRepository userRepository, BcryptPassword bcryptPassword) {
        this.userRepository = userRepository;
        this.bcryptPassword = bcryptPassword;
    }

    private User convertRegisterFormToUser(RegisterForm registerForm) {
        return new User(
                registerForm.getId(),
                registerForm.getFullName(),
                registerForm.getEmail(),
                registerForm.getPassword(),
                registerForm.getPhone(),
                Role.PASSENGER,
                0D
        );
    }

    public void registerUser(RegisterForm registerForm) {
        // Mã khẩu mật khẩu
        registerForm.setPassword(bcryptPassword.bcrypt(registerForm.getPassword()));
        // Lưu người dùng
        User user = convertRegisterFormToUser(registerForm);
        userRepository.save(user);
    }

    public User login(LoginForm loginForm) {
        // Lấy email
        User user = userRepository.findByEmail(loginForm.getEmail());
        if (user == null) {
            return null;
        }
        // Kiểm tra mật khẩu
        if (!bcryptPassword.verify(loginForm.getPassword(), user.getPassword())) {
            return null;
        }
        return user;
    }

    public void updateProfile(User user, EditProfileForm form) {
        // Luu người dùng
        user.setFullName(form.getFullName());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public long countByRole(Role role) {
        return userRepository.countByRole(role);
    }
}
