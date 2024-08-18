package com.mnsoo.board.service;

import com.mnsoo.board.domain.User;
import com.mnsoo.board.dto.user.UserDto;
import com.mnsoo.board.repository.UserRepository;
import com.mnsoo.board.security.CustomUserDetails;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        List<String> roles = user.getRoles().stream()
                .map(Enum::name)
                .toList();
        userDto.setRoles(roles);

        return new CustomUserDetails(userDto);
    }
}
