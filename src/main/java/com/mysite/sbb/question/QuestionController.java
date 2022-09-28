package com.mysite.sbb.question;

import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

// 스프링의 의존성 주입(Dependency Injection) 방식 3가지
// @Autowired 속성 : 속성에 @Autowired 애너테이션을 적용하여 객체를 주입
// 생성자 : 생성자를 작성하여 객체를 주입 (권장)
// Setter : Setter 메서드를 작성하여 객체를 주입 (메서드에 @Autowired 애너테이션 필요)

@RequestMapping("/question")
@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 생성
@Controller
public class QuestionController {

    private final QuestionService questionService; // Controller -> Service -> Repository 구조로 데이터 처리
    private final UserService userService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        // model : 자바 클래스와 템플릿 간의 연결고리 역할, 템플릿에서 이 값 사용
        return "question_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 해당 메소드에 접근 가능, 권한 없으면 로그인 페이지로
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){ // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser); // 바인딩
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
}
