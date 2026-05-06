package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.Role;
import com.example.it210finalproject.exceptions.EmailDuplicate;
import com.example.it210finalproject.exceptions.PhoneDuplicate;
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

    public void registerUser(RegisterForm registerForm) throws EmailDuplicate, PhoneDuplicate {
        // Kiểm tra email, số điện thoại
        if (userRepository.findByEmail(registerForm.getEmail()) != null) {
            throw new EmailDuplicate("Email đã tồn tại");
        }
        if (userRepository.findByPhone(registerForm.getPhone()) != null) {
            throw new PhoneDuplicate("Số điện thoại đã tồn tại");
        }
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

    public void updateProfile(User user, EditProfileForm form) throws EmailDuplicate, PhoneDuplicate {
        // Kiểm tra email
        User data = userRepository.findByEmail(form.getEmail());
        if (data != null && !data.getId().equals(user.getId())) {
            throw new EmailDuplicate("Email đã tồn tại");
        }
        // Kiểm tra số điện thoại
        data = userRepository.findByPhone(form.getPhone());
        if (data != null && !data.getId().equals(user.getId())) {
            throw new PhoneDuplicate("Số điện thoại đã tồn tại");
        }
        // Luu người dùng
        user.setFullName(form.getFullName());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        userRepository.save(user);
    }
}
