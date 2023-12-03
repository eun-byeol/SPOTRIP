# 08_EnjoyTrip_Final

## Getting started
### 1. git clone
```
git clone https://lab.ssafy.com/joeunbyeol98/08_enjoytrip_final.git
```

### 2. DB 준비
1. MySQL Workbench 8.0 실행
2. 산출물 내 `20231123_spotripDDL.sql` 전체 실행
3. 산출물 내 `20231124_spotrip_dump.sql` 전체 실행
4. DB 데이터 확인
```
use spotrip;
select * from attraction;
```
### 3. 서버 실행
1. eclipse STS 혹은 intellJ 내에서 spotrip_back 폴더 import 받기
2. Spring Boot App 실행

### 4. 프론트 실행
1. spotrip_front 디렉토리 내 터미널에서 npm 설치
```
npm install
```
2. 뷰 실행
```
npm run dev
```