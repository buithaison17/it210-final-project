package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.validation.EmailDuplicate;
import com.example.it210finalproject.validation.EmailFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditProfileForm {
    @NotBlank(message = "Họ và tên không được trống")
    private String fullName;
    @NotBlank(message = "Email không được để trống")
    @EmailFormat(message = "Email không đúng định dạng")
    @EmailDuplicate
    private String email;
    @NotBlank(message = "Số điện thoại không được trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không đúng định dạng")
    private String phone;
}
