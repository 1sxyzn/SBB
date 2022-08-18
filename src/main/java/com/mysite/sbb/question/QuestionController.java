package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

// 스프링의 의존성 주입(Dependency Injection) 방식 3가지
// @Autowired 속성 : 속성에 @Autowired 애너테이션을 적용하여 객체를 주입
// 생성자 : 생성자를 작성하여 객체를 주입 (권장)
// Setter : Setter 메서드를 작성하여 객체를 주입 (메서드에 @Autowired 애너테이션 필요)


@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 생성
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    @RequestMapping("/question/list")
    public String list(Model model){
        List<Question> questionList = this.questionRepository.findAll();
        model.addAttribute("questionList", questionList); // model : 자바 클래스와 템플릿 간의 연결고리 역할, 템플릿에서 이 값 사용
        return "question_list";
    }
}
