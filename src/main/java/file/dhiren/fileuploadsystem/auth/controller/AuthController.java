package file.dhiren.fileuploadsystem.auth.controller;

import file.dhiren.fileuploadsystem.auth.dto.AuthResponse;
import file.dhiren.fileuploadsystem.auth.dto.LoginRequest;
import file.dhiren.fileuploadsystem.auth.dto.RegisterRequest;
import file.dhiren.fileuploadsystem.auth.dto.UserResponse;
import file.dhiren.fileuploadsystem.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentication", description = "User Authentication APIs")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "Register User")
    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request)
    {
        return authService.register(request);
    }

    @Operation(summary = "Authenticate user and generate JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }


}
