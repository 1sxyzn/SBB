package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository <Question, Integer> { //엔티티 타입과 엔티티 pk의 속성 타입
    Question findBySubject(String subject);
}
