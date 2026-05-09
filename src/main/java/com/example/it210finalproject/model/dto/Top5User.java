package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.model.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Top5User {
    User user;
    Long ticketCount;
}
