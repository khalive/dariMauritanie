package com.DariM.darim.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.DariM.darim.model.User;
import com.DariM.darim.repository.UserRepository;
import com.DariM.darim.service.AuthService;
import com.DariM.darim.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;

    // @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager,
                          UserRepository userRepository, PasswordEncoder passwordEncoder,
                          JavaMailSender mailSender, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/index")
    public ResponseEntity<?> showIndexPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            return ResponseEntity.ok(Map.of("nom", username));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
    }

    @GetMapping("/login")
    public ResponseEntity<?> showLoginPage(@RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email ou mot de passe incorrect"));
        }
        return ResponseEntity.ok(Map.of("message", "Login page"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }

        String token = jwtUtil.generateToken(email);
        Cookie jwtCookie = new Cookie("Authorization", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);

        User user = userOptional.get();
        String role = user.getRole().name();

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "role", role
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> registerRequest) {
        String name = registerRequest.get("name");
        String email = registerRequest.get("email");
        String password = registerRequest.get("password");

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is already registered."));
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(User.Role.USER);
        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email not found."));
        }

        User user = userOptional.get();
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        String resetLink = "http://localhost:8083/api/auth/reset-password?token=" + resetToken;
        sendResetEmail(user.getEmail(), resetLink);

        return ResponseEntity.ok(Map.of("message", "Password reset link has been sent to your email."));
    }

    private void sendResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("plazmapegusus@gmail.com");
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" + resetLink);
        mailSender.send(message);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        var userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
        }
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        String newpassword = request.get("newpassword");
        String confirmpassword = request.get("confirmpassword");
        String token = request.get("token");

        var userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
        }
        if (!newpassword.equals(confirmpassword)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Passwords do not match"));
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newpassword));
        user.setResetToken(null);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }
}