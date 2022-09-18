package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> { // SiteUser의 PK 타입이 Long

}
