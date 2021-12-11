package com.example.auto_sign;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("home", new HomeView());
        return "home";
    }

    @PostMapping("/")
    public String URLsubmit(@ModelAttribute HomeView home, Model model) throws JSONException, IOException, InterruptedException {
        model.addAttribute("home", home);
        home.sendURL(home.getURL());
        TimeUnit.SECONDS.sleep(3);
        home.downloadVideo(home.getURL());
        System.out.println(home.getURL());
        return "result";
    }
}




