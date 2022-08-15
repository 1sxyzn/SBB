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

    @Autowired
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

}
