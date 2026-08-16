package com.example.product_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_api.dto.ProfileDTO;
import com.example.product_api.io.AuthRequest;
import com.example.product_api.io.AuthResponse;
import com.example.product_api.io.ProfileRequest;
import com.example.product_api.io.ProfileResponse;
import com.example.product_api.service.CustomerUserDetailsService;
import com.example.product_api.service.TokenBlacklistService;
import com.example.product_api.service.impl.ProfileServiceImpl;
import com.example.product_api.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;
    private final ProfileServiceImpl profileService;
    private final CustomerUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest request) {
        log.info("API /register is called {}", request);
        ProfileDTO profileDTO = mapToProfileDTO(request);
        profileDTO = profileService.createProfile(profileDTO);
        log.info("Printing the profile dto details {}", profileDTO);
        return mapToProfileResponse(profileDTO);
    }

    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest request) throws Exception {
        log.info("API /login is called {}", request);
        authenticate(request);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(jwtToken, request.getEmail());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/signout")
    public void signout(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromRequest(request);
        if (jwtToken != null) {
            tokenBlacklistService.addTokenToBlacklist(jwtToken);
        }
    }

    private String extractJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void authenticate(AuthRequest request) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (DisabledException ex) {
            throw new Exception("Profile disabled");
        } catch (Exception ex) {
            throw new Exception("Invalid credentials");
        }
    }

    /**
     * Mapper method to map values from profile request to profile dto
     * 
     * @param profileRequest
     * @return profileDto
     */
    private ProfileDTO mapToProfileDTO(ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest, ProfileDTO.class);
    }

    /**
     * Mapper method to map values from profile dto to profile response
     * 
     * @param profileDTO
     * @return profileResponse
     */
    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileResponse.class);
    }
}
