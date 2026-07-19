package file.dhiren.fileuploadsystem.auth.service;

import file.dhiren.fileuploadsystem.auth.dto.AuthResponse;
import file.dhiren.fileuploadsystem.auth.dto.LoginRequest;
import file.dhiren.fileuploadsystem.auth.dto.RegisterRequest;
import file.dhiren.fileuploadsystem.auth.dto.UserResponse;

public interface AuthService {


  UserResponse register(RegisterRequest registerRequest);

  AuthResponse login(LoginRequest request);

}
