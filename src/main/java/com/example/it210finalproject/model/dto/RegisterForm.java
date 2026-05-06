package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.validation.ConfirmPasswordValidate;
import com.example.it210finalproject.validation.EmailFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfirmPasswordValidate(message = "Mật khẩu không giống nhau")
public class RegisterForm {
    private Long id;
    @NotBlank(message = "Họ và tên không được trống")
    private String fullName;
    @NotBlank(message = "Số điện thoại không được trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không hợp lệ")
    private String phone;
    @NotBlank(message = "Email không được trống")
    @EmailFormat
    private String email;
    @NotBlank(message = "Mật khẩu không được trống")
    @Length(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;
    @NotBlank(message = "Xác nhận mật khẩu không được trống")
    @Length(min = 8, message = "Xác nhận mật khẩu phải có ít nhất 8 ký tự")
    private String confirmPassword;
}
