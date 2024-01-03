package kr.co.mpago.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @RequestHeader("Authorization") String token) {
        return "home";
    }
}
