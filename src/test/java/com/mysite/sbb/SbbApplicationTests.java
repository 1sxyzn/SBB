package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

    @Autowired // DI에 의해 스프링이 자동으로 QuestionRepository 객체를 생성 = 프록시 패턴 사용
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void testJpa(){ // 데이터 조회
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb는는 무엇가요?", q.getSubject());
    }

    @Test
    void testJpa2(){ // 데이터 조회
        Optional<Question> oq = this.questionRepository.findById(1); // id 값으로 데이터 조회
        if(oq.isPresent()){
            Question q = oq.get();
            assertEquals("SBB는 무엇인가요?", q.getSubject());
        }
    }

    @Test
    void testJpa3(){ // 데이터 조회
        Question q = this.questionRepository.findBySubject("SBB는 무엇인가요?");
        assertEquals(1, q.getId());
    }

    @Test
    void testJpa4(){ // 데이터 조회
        Question q = this.questionRepository.findBySubjectAndContent("SBB는 무엇인가요?", "SBB에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    @Test
    void testJpa5(){ // 데이터 조회
        List<Question> qList = this.questionRepository.findBySubjectLike("SBB%");
        Question q  = qList.get(0);
        assertEquals("SBB는 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpa6(){ // 데이터 수정
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("제목 수정");
        this.questionRepository.save(q);
    }

    @Test
    void testJpa7(){
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent()); // 값이 True인지 확인
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

    @Test
    void testJpa8(){ // 답변 데이터 생성, 저장
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네. ID는 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위한 Question 객체
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

}
