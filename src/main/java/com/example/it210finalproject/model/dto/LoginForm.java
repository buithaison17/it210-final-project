package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.validation.EmailFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginForm {
    @NotBlank(message = "Email không được để trống")
    @EmailFormat(message = "Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    @Length(min = 8, message = "Mật khẩu phải tối thiểu 8 ký tự")
    private String password;
}
