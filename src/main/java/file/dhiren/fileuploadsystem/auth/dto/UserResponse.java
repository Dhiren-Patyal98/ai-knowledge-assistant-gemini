package file.dhiren.fileuploadsystem.auth.dto;


import file.dhiren.fileuploadsystem.auth.entity.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
}
