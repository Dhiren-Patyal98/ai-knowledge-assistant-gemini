package file.dhiren.fileuploadsystem.auth.service;

import file.dhiren.fileuploadsystem.auth.dto.AuthResponse;
import file.dhiren.fileuploadsystem.auth.dto.LoginRequest;
import file.dhiren.fileuploadsystem.auth.dto.RegisterRequest;
import file.dhiren.fileuploadsystem.auth.dto.UserResponse;
import file.dhiren.fileuploadsystem.auth.entity.User;
import file.dhiren.fileuploadsystem.auth.entity.UserRole;
import file.dhiren.fileuploadsystem.auth.mapper.UserMapper;
import file.dhiren.fileuploadsystem.auth.repository.UserRepository;
import file.dhiren.fileuploadsystem.exception.UserAlreadyExistsException;
import file.dhiren.fileuploadsystem.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class  AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserMapper userMapper;

    @Override
    public UserResponse register(RegisterRequest request)
    {


        log.info("Registration request received for {}", request.getEmail());

        if(userRepository.existsByEmail(request.getEmail())){

            log.warn("Registration failed. Email {} already exists", request.getEmail());

            throw new UserAlreadyExistsException(
                    "User already exists with email: " + request.getEmail());
        }


        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        User savedUser = userRepository.save(user);

        log.info("User {} registered successfully", user.getEmail());

        return userMapper.toUserResponse(savedUser);

    }

    @Override
    public AuthResponse login(LoginRequest request) {

        log.info("Login attempt for {}", request.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword())
            );
        }catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }


        log.info("User {} authenticated successfully", request.getEmail());


        User user =  userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(request.getEmail());

        log.debug("JWT generated for {}", request.getEmail());

        return AuthResponse.builder()
                .token(token)
                .user(userMapper.toUserResponse(user))
                .build();

    }




}
