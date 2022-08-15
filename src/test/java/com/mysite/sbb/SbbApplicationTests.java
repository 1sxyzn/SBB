package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

    @Autowired // DI에 의해 스프링이 자동으로 QuestionRepository 객체를 생성 = 프록시 패턴 사용
    private QuestionRepository questionRepository;

    @Test
    void testJpa(){
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb는는 무엇가요?", q.getSubject());
    }

    @Test
    void testJpa2(){
        Optional<Question> oq = this.questionRepository.findById(1); // id 값으로 데이터 조회
        if(oq.isPresent()){
            Question q = oq.get();
            assertEquals("SBB는 무엇인가요?", q.getSubject());
        }
    }

    @Test
    void testJpa3(){
        Question q = this.questionRepository.findBySubject("SBB는 무엇인가요?");
        assertEquals(1, q.getId());
    }

    @Test
    void testJpa4(){
        Question q = this.questionRepository.findBySubjectAndContent("SBB는 무엇인가요?", "SBB에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    @Test
    void testJpa5(){
        List<Question> qList = this.questionRepository.findBySubjectLike("SBB%");
        Question q  = qList.get(0);
        assertEquals("SBB는 무엇인가요?", q.getSubject());
    }

}
