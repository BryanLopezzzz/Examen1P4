package org.example.examen1programacion4_119600374.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            var userDetails = (UserDetails) auth.getPrincipal();
            username = userDetails.getUsername();
        }
        model.addAttribute("username", username);
        return "index";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }
}
