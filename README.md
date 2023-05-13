# MeetEat
> 배달공유 프로젝트

높은 배달비 문제로 인한 자영업자의 어려움을 다룬 뉴스를 접하곤
 “배달공유 웹사이트”라는 주제를 떠올리게 되었습니다.
 
* 누구나 본인의 위치에서 100m이내의 거리에 배달공유를 원하는 사람을 찾을 수 있습니다. 
* 회원은 배달공유할 사람을 구하는 글을 작성할 수 있습니다.
* 내 주변 100m이내의 회원들이 쓴 글을 조회해 채팅을 할 수 있습니다.

![메인](https://user-images.githubusercontent.com/98367972/230483659-54817450-7e49-4b29-9156-dd23183d21eb.png)

# 사용방법
> 현재 http://praymaire.cafe24.com 에서 호스팅 중입니다. 
* 로그인 기능 오류로, 위치조회 기능만 사용 가능합니다.
* 테스트의 가시성을 위해 100m가 아닌, 1km로 설정되어 있습니다.
* 메인 - found - 위치정보 입력(테스트 데이터는 서면을 기준으로 되어 있습니다. ex) 삼한 골든게이트) - 현재 정보로 주변 검색

![해결결과물](https://user-images.githubusercontent.com/98367972/232584641-0f188694-0a7f-4719-b2ac-76b94f2b93d6.PNG)


## 1. 홈
> 최신글을 조회하고, 위치정보를 수집하는 메인 홈입니다.

![메인 페이지](https://user-images.githubusercontent.com/98367972/230483679-5cada741-22a5-4ce1-a4f3-c02f0f84fe26.png)

## 2. 위치 조회(자동, 수동)
> 자동으로 현재 위치를 수집합니다. 위치수집에 실패하면 수동검색 페이지로 이동합니다.

![자동 위치수집](https://user-images.githubusercontent.com/98367972/230476345-cbcbfab4-4670-4f4c-afcb-d94cb67024b0.png)

> 수동의 경우, 도로명 주소창으로 위치를 탐색합니다.

![위치정보 수집(수동)](https://user-images.githubusercontent.com/98367972/230476470-373a1952-d084-4d42-8cde-86d7c6613106.png)

## 3. 사용자 위치 마커
> 지도에서 내 위치와 주변 100m 이내의 사용자들의 위치를 마커로 표시하고, 거리와 도보시간을 계산합니다.

![사용자 위치 마커](https://user-images.githubusercontent.com/98367972/230476501-5ccbf81c-3096-41bb-a1a9-895704e3eed7.png)

## 4. 위치 기반 게시글 조회
> 내 주변 100m 이내 사용자들의 게시글만 조회됩니다.

![게시글 필터링](https://user-images.githubusercontent.com/98367972/230476491-64321e6e-2a65-4755-9a6e-d33591a73f08.png)

## 5. 채팅 기능
> 근거리 사용자간 배달공유를 위한, 오픈소스 Web Socket을 활용한 실시간 채팅기능 입니다.

![채팅 원본](https://user-images.githubusercontent.com/98367972/230476535-f238e579-ee2c-4c89-9d22-dc5f486fab7c.png)


# Project Structure

model2 구조로 개발되었습니다.

> 개발언어 

* Java
* JavaScript
* JSP
* JQuery
* HTML5
* CSS3
* Ajax
* JSON

> 개발도구

* Eclipse
* MYSQL v8.0 (Workbench)
* Tomcat v8.5

> API

* Daum RoadName API
* Kakao map API

> 협업도구

* Github
* AqueryTool

> ERD

![ERD](https://user-images.githubusercontent.com/98367972/230483670-cac0db92-a709-4a94-92db-27f40b3e3f62.png)


## Geocoding / Reverse Geocoding
> 현재 위경도를 session에 저장해 브라우저 종료 전까지 데이터를 유지, DB에 저장된 위경도 데이터의 필터링을 위해 sql 구문에 위경도 계산식을 사용했습니다.

* 공식을 SELECT count(*), SELECT * 로 변경하면 현재 위치에서 100m이내의 회원 수와 직선 거리를 구할 수 있습니다.

```sh
SELECT latitude, longitude
FROM (
      SELECT latitude, longitude, ( 6371 * acos( cos( radians( 35.8551246 ) ) * cos( radians( latitude) ) * cos( radians( longitude ) - radians(128.5321680) ) + sin( radians(35.8551246) ) * sin( radians(latitude) ) ) ) AS distance
      FROM 테이블
     ) DATA
WHERE DATA.distance < 0.1; (100m 기준)
```

MySql에서 적용한 화면입니다. 테스트를 위해 반경 1km 이내 회원들의 위경도와 거리를 조회했습니다.

![지오코딩](https://user-images.githubusercontent.com/98367972/230740041-909a5809-4f53-47d5-909f-8dced0a6f1c8.png)



 * 역지오 코딩을 사용해 session에 저장된 위경도를 다시 한글 주소로 변경했습니다. 

![역지오](https://user-images.githubusercontent.com/98367972/230740045-7aaaa389-5b44-45c5-9784-0d39f7f11619.png)
