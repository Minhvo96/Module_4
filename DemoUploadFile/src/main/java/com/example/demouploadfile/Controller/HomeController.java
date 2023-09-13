package com.example.demouploadfile.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/upload")
    public String home() {
        return "index"; // Trả về tên của trang HTML mà bạn muốn hiển thị (không cần đuôi .html)
    }
}

