package com.example.product_api.service;

import com.example.product_api.dto.ProfileDTO;

public interface ProfileService {
    /**
     * It will save the user details to database
     * 
     * @param profileDTO
     * @return profileDto
     */
    ProfileDTO createProfile(ProfileDTO profileDTO);
}
