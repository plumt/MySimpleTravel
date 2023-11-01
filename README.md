# MySimpleTravel

---

# 안드로이드 제주도 여행 앱

기간 2023.08.20 ~

---

- 날씨, 여행 관광 정보
- 나의 여행 계획
- 커뮤니티
- 설정

---

- Android Gradle Plugin Version 7.3.1
- Gradle Version 7.4
- Hilt Version 2.42
- Kotlin Version 1.7.0 

---


# 1. API

## 지역 코드 API


BASE URL : https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/

별도 기한 및 키가 존재 하지 않는다.

Retrofit2 방식 사용




## 날씨 API


BASE URL : https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=

BASE IMAGE URL : https://ssl.pstatic.net/sstatic/keypage/outside/scui/weather_new_new/img/weather_svg/

별도 기한 및 키가 존재 하지 않는다.

크롤링 방식 사용




## 버스, 지도에 표시할 데이터(렌트카, 병원, 올레길, 사고다발, 약국, 기념품, 박물관 및 미술관, 숙박, 오름 위치, 버스 정류장) API


<https://www.jejudatahub.net/> 제주데이터허브

BASE URL : https://open.jejudatahub.net/api/proxy/

Retrofit2 방식 사용

< 데이터 명 >
1. 카셰어링 업체 위치 정보(쏘카)
2. 카셰어링 업체 위치 정보(통합)
3. 기념품 가게 위치 정보
4. 약국 정보
5. 병·의원 정보
6. 올레길 위치 정보
7. 박물관 및 미술관 위치 정보
8. 교통사고 다발지역 정보
9. 캠핑 가능 장소 위치 정보
10. 한라산 둘레길 코스별 정보
11. 관광 숙박업 정보
12. 오름 위치 정보
13. 일일 노선별 버스 이용자 정보
14. 일일 정류소별 버스 이용자 정보
15. 버스 노선 기본 정보
16. 버스 정류소 기본 정보




## 도로 및 교통, 주유소, 충전소, 주차장, 관광지 관련 API


<http://www.jejuits.go.kr/> 제주특별자치도 교통정보센터

BASE_URL : http://api.jejuits.go.kr/api/

Retrofit2 방식 사용




## 제주 관광 정보 관련 API


<https://www.visitjeju.net/kr/visitjejuapi> VISIT JEJU

BASE_URL : https://api.visitjeju.net/vsjapi/contents/

Retrofit2 방식 사용


---

## 2. 라이브러리