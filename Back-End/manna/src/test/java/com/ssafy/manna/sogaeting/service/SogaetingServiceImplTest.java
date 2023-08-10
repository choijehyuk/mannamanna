package com.ssafy.manna.sogaeting.service;

import com.ssafy.manna.global.common.domain.Session;
import com.ssafy.manna.global.common.repository.RedisSessionRepository;
import com.ssafy.manna.sogaeting.dto.response.SogaetingMemberResponse;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SogaetingServiceImplTest {

    @Autowired
    private RedisSessionRepository redisSessionRepository;

    @Test
    public void init(){
        Session session = Session.builder()
            .userName("박소현")
            .userId("sohyun3")
            .gender("female")
            .build();
        redisSessionRepository.save(session);
    }

    @Test
    public void Sub_Stirng_last_test(){
        String str = "/manna/upload/images/member/jaeeitest_hyunjin.jpg";
        String s = StringUtils.substringAfterLast(str, "/");

        Assertions.assertThat(s).isEqualTo("jaeeitest_hyunjin.jpg");
    }

    @Test
    public void 년도_테스트(){
        int year = LocalDate.now().getYear();
        System.out.println(year - 1998);
    }

//    @Test
//    public void 온라인_업데이트() {
//        List<SogaetingMemberResponse> memberByCondition1 = sogaetingService.findMemberByCondition(
//            null, null, null, null, null, "test1");
//        List<SogaetingMemberResponse> memberByCondition2 = sogaetingService.findMemberByCondition(
//            null, null, null, null, null, "test2");
//
//        Assertions.assertThat(memberByCondition1.get(0).getId())
//            .isNotEqualTo(memberByCondition2.get(0).getId());
//    }
}