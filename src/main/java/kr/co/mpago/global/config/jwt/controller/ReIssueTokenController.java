package kr.co.mpago.global.config.jwt.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reissue")
@AllArgsConstructor
public class ReIssueTokenController {
    @GetMapping()
    public ResponseEntity reissueToken() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}