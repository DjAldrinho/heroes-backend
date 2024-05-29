package com.example.heroesbackend.security.controller;

import com.example.heroesbackend.exceptions.ResourceExistsException;
import com.example.heroesbackend.security.dto.LoginUserDto;
import com.example.heroesbackend.security.dto.RegisterUserDto;
import com.example.heroesbackend.security.dto.RoleDto;
import com.example.heroesbackend.security.dto.UserDto;
import com.example.heroesbackend.security.entity.Role;
import com.example.heroesbackend.security.entity.User;
import com.example.heroesbackend.security.enums.RolName;
import com.example.heroesbackend.security.exceptions.RoleNotValidException;
import com.example.heroesbackend.security.jwt.JwtProvider;
import com.example.heroesbackend.security.service.RolService;
import com.example.heroesbackend.security.service.UserService;
import com.example.heroesbackend.utils.ValidationResponse;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    private final RolService rolService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, ModelMapper modelMapper,
                          RolService rolService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterUserDto registerUserDto) {

        if (userService.existsUserByUserName(registerUserDto.getUsername())) {
            throw new ResourceExistsException("username", "user");
        }

        if (userService.existsByEmail(registerUserDto.getEmail())) {
            throw new ResourceExistsException("email", "user");
        }

        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));


        Set<Role> roles = new HashSet<>();

        if (registerUserDto.getRoles().isEmpty()) {
            Optional<Role> roleOptional = rolService.getRolByName(RolName.ROLE_USER);
            roleOptional.ifPresent(roles::add);
        } else {
            try {
                for (RoleDto roleDto : registerUserDto.getRoles()) {
                    Role role = modelMapper.map(roleDto, Role.class);
                    roles.add(role);
                }
            } catch (Exception e) {
                throw new RoleNotValidException();
            }

        }

        User newUser = modelMapper.map(registerUserDto, User.class);

        newUser.setRoles(roles);

        User userInserted = this.userService.save(newUser);

        ValidationResponse response = new ValidationResponse("user created", HttpStatus.CREATED);

        response.addData("user", modelMapper.map(userInserted, UserDto.class));

        return response.getResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginUserDto loginUserDto) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = JwtProvider.generateToken(authentication);

        ValidationResponse response = new ValidationResponse("Login success", HttpStatus.OK);
        response.addData("authorization", "Bearer")
                .addData("token", token)
                .addData("username", userDetails.getUsername())
                .addData("authorities", userDetails.getAuthorities());

        return response.getResponse();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(Authentication authentication) {

        ValidationResponse response = new ValidationResponse("User loaded", HttpStatus.OK);

        UserDetails user = (UserDetails) authentication.getPrincipal();

        response.addData("user", modelMapper.map(user, UserDto.class));

        return response.getResponse();
    }

}
