package file.dhiren.fileuploadsystem.security;

import file.dhiren.fileuploadsystem.auth.dto.UserResponse;
import file.dhiren.fileuploadsystem.auth.entity.User;

public interface CurrentUserService {


    User getCurrentUser();
}
