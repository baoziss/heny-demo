package com.heny.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TurnPointController {
    @PostMapping("/upload")
    public Boolean uploadTurnPoint(MultipartFile turnPointFile) {
        return null;
    }
}