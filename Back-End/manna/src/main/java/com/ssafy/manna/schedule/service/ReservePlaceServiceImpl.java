package com.ssafy.manna.schedule.service;

import com.ssafy.manna.global.util.GeoUtils;
import com.ssafy.manna.member.domain.Member;
import com.ssafy.manna.member.repository.MemberRepository;
import com.ssafy.manna.schedule.domain.ReservePlace;
import com.ssafy.manna.schedule.dto.request.ReservePlaceRequest;
import com.ssafy.manna.schedule.repository.ReservePlaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservePlaceServiceImpl implements ReservePlaceService{

    private final ReservePlaceRepository reservePlaceRepository;

    private final MemberRepository memberRepository;

    @Override
    public ReservePlace getPlaceInfo(Integer id) throws Exception {
        //id로 찾아서 
        ReservePlace reservePlace = reservePlaceRepository.findById(id).orElseThrow(()->new Exception("예약 장소 정보가 없습니다."));
        return reservePlace;
    }

    @Override
    public List<ReservePlace> getRecommendList(ReservePlaceRequest reservePlaceRequest) {
        String sido = reservePlaceRequest.getSido();
        String gugun = reservePlaceRequest.getGugun();
        String category = reservePlaceRequest.getCategory();
        List<ReservePlace> recommendList = reservePlaceRepository.findAllBySidoAndGugunAndCategory(sido,gugun,category);
        return recommendList;
    }

    @Override
    public List<ReservePlace> recommendMiddle(String userId, String opponentId) throws Exception {
        final int EARTH_RADIUS = 6371; // 지구의 반지름 (단위: km)
        Member member1 = memberRepository.findById(userId).orElseThrow(()->new Exception("회원 정보가 없습니다."));
        Member member2 = memberRepository.findById(opponentId).orElseThrow(()->new Exception("회원 정보가 없습니다."));

        double latitude1 = member1.getMemberDetail().getAddress().getLatitude();
        double longitude1 = member1.getMemberDetail().getAddress().getLongitude();


        double latitude2 = member2.getMemberDetail().getAddress().getLatitude();
        double longitude2 = member2.getMemberDetail().getAddress().getLongitude();

        //가운데 좌표 계산
        System.out.println(longitude1);
        System.out.println(longitude2);
        double latitudeMiddle = (latitude1+latitude2)/2.0;
        double longitudeMiddle = (longitude1+longitude2)/2.0;

        System.out.println(latitudeMiddle+","+longitudeMiddle);
        // 위도에 따른 1도 당 이동 거리 계산 (단위: 미터)
        double metersPerDegreeLatitude = 111320.0; // 약 111.32 km

// 경도에 따른 1도 당 이동 거리 계산 (단위: 미터)
        double metersPerDegreeLongitude = metersPerDegreeLatitude * Math.cos(Math.toRadians(latitudeMiddle));

// 현재 위치 기준 검색 거리 좌표
        double radius = 1000.0; // 반경 5 km
        double maxY = latitudeMiddle + (radius / metersPerDegreeLatitude);
        double minY = latitudeMiddle - (radius / metersPerDegreeLatitude);
        double maxX = longitudeMiddle + (radius / metersPerDegreeLongitude);
        double minX = longitudeMiddle - (radius / metersPerDegreeLongitude);



        System.out.println(maxY+","+minY+","+maxX+","+minX);
//        List<ReservePlace> tempNearByPlaces = reservePlaceRepository.findNearbyReservePlaces(minX,maxX,minY,maxY);
        List<ReservePlace> tempNearByPlaces = reservePlaceRepository.findNearbyReservePlaces(minY,maxY,minX,maxX);

        List<ReservePlace> nearByPlaces = new ArrayList<>();
        for (ReservePlace place : tempNearByPlaces) {
            double distance = GeoUtils.getDistance(latitudeMiddle, longitudeMiddle, place.getLatitude(), place.getLongitude());
            System.out.println(distance);
            if(distance<radius){
                nearByPlaces.add(place);
            }
        }

        System.out.println(nearByPlaces.size());

        return nearByPlaces;
    }

}
