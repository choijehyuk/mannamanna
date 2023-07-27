package com.ssafy.manna.member.domain;

import com.querydsl.core.annotations.QueryEntity;
import com.ssafy.manna.global.common.domain.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@QueryEntity
public class MemberDetail extends BaseTimeEntity {

    @Id
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberAddress memberAddress;

    private String tel;                 //전화번호
    private String birth;               //생년월일
    private String emailId;             //이메일 아이디
    private String emailDomain;         //이메일 도메인

    private int height;                 //키
    private String job;                 //직업

    private boolean isSmoker;           //흡연여부
    private boolean isDrinker;          //음주
    private String mbti;                //MBTI
    private String religion;            //종교
    private String introduction;        //자기소개
    private boolean isBlockingFriend;   //지인차단 여부
    private int mileage;                //현재마일리지

    // 정보 수정 (비밀번호, 키, 주소, 직업, 프로필 사진)
    public void updateHeight(int height) {
        this.height = height;
    }

    public void updateJob(String job) {
        this.job = job;
    }

    //주소 수정 추후 추가

}
