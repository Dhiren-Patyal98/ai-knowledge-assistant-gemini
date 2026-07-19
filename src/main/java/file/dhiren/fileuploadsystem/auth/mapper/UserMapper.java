package file.dhiren.fileuploadsystem.auth.mapper;

import file.dhiren.fileuploadsystem.auth.dto.UserResponse;
import file.dhiren.fileuploadsystem.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user)
    {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
