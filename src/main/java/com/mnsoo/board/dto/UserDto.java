package com.mnsoo.board.dto;

import com.mnsoo.board.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private List<String> roles;

    public static UserDto from(User user) {

        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
