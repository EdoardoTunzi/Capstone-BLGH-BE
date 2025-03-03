package com.example.Capstone_BLGH_BE.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary uploader() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dpsswabjf");
        config.put("api_key", "694858762295978");
        config.put("api_secret", "Pzmkzt8_K1kFUszpMgUnVWg97l8");
        return new Cloudinary(config);
    }
}