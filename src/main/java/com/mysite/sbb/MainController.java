package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/sbb")
    @ResponseBody
    public String index(){
        return "welcome to SBB!";
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
    // redirect:<URL> -> URL로 리다이렉트 (완전히 새로운 URL로 요청)
    // forward:<URL> -> URL로 포워드 (기존 요청 값들이 유지된 상태로 URL 전환)
}
