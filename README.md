# MeetEat
>배달공유 프로젝트

높은 배달비 문제로 인한 자영업자의 어려움을 다룬 뉴스를 접하곤
 “배달공유 웹사이트”라는 주제를 떠올리게 되었습니다.
 
* 누구나 본인의 위치에서 100m이내의 거리에 배달공유를 원하는 사람을 찾을 수 있습니다. 
* 회원은 배달공유할 사람을 구하는 글을 작성할 수 있습니다.
* 내 주변 100m이내의 회원들이 쓴 글을 조회해 채팅을 할 수 있습니다.


# 사용방법
>DB내에 데이터가 없으면 위치조회 기능을 사용할 수 없어, 테스트 데이터 사용중입니다.


## 1. 위치 조회(자동, 수동)
> 자동으로 현재 위치를 수집합니다. 위치수집에 실패하면 수동검색 페이지로 이동합니다.

![자동 위치수집](https://user-images.githubusercontent.com/98367972/230476345-cbcbfab4-4670-4f4c-afcb-d94cb67024b0.png)

> 수동의 경우, 도로명 주소창으로 위치를 탐색합니다.

![위치정보 수집(수동)](https://user-images.githubusercontent.com/98367972/230476470-373a1952-d084-4d42-8cde-86d7c6613106.png)

## 2. 사용자 위치 마커
> 지도에서 내 위치와 주변 100m 이내의 사용자들의 위치를 마커로 표시하고, 거리와 도보시간을 계산합니다.

![사용자 위치 마커](https://user-images.githubusercontent.com/98367972/230476501-5ccbf81c-3096-41bb-a1a9-895704e3eed7.png)

## 3. 위치 기반 게시글 조회
> 내 주변 100m 이내 사용자들의 게시글만 조회됩니다.

![게시글 필터링](https://user-images.githubusercontent.com/98367972/230476491-64321e6e-2a65-4755-9a6e-d33591a73f08.png)

## 4. 채팅 기능
> 근거리 사용자간 배달공유를 위한, 오픈소스 Web Socket을 활용한 실시간 채팅기능 입니다.

![채팅 원본](https://user-images.githubusercontent.com/98367972/230476535-f238e579-ee2c-4c89-9d22-dc5f486fab7c.png)
