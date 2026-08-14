package com.example.product_api.service;

import org.springframework.stereotype.Service;

import com.example.product_api.entity.ProfileEntity;
import com.example.product_api.repositories.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final ProfileRepository profileRepository;

    public ProfileEntity getLoggedInProfile() {
        // TODO: Implement this method
        return null;
    }
}
