package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa(){
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size()); // 데이터 갯수가 2개인지 확인

        Question q = all.get(0);
        assertEquals("SBB는 무엇인가요?", q.getSubject()); //첫번째 데이터 제목 확인
    }

}
