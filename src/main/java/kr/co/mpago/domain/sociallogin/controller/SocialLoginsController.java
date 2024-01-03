package kr.co.mpago.domain.sociallogin.controller;

import kr.co.mpago.domain.sociallogin.dto.SocialLoginsDto;
import kr.co.mpago.domain.sociallogin.entity.SocialLogins;
import kr.co.mpago.domain.sociallogin.service.SocialLoginsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class SocialLoginsController {
    private final SocialLoginsService socialLoginsService;

    @Autowired
    public SocialLoginsController(SocialLoginsService socialLoginsService) {
        this.socialLoginsService = socialLoginsService;
    }

    @PostMapping("/login")
    public ResponseEntity<SocialLoginsDto> login(@RequestBody SocialLoginsDto socialLoginsDto) {
        SocialLoginsDto responseDto = socialLoginsService.login(socialLoginsDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
}
