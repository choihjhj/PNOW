# 📖 PNOW (Place Now)

> **실시간 음식점 예약 서비스 (Spring Boot 기반)**  
> 배포 자동화(CI/CD)와 인증 시스템(OAuth2)을 포함한     
> **운영 가능한 백엔드 서비스 구축 프로젝트**  


---
## 🎯 프로젝트 목적
단순 CRUD 구현을 넘어, 실제 서비스 환경에서 발생하는 문제를 직접 해결하는 경험을 목표로 개발했습니다.     
- GitHub Actions + AWS(CodeDeploy, S3, EC2)를 활용한 CI/CD 자동 배포 구축   
- Spring Security + OAuth2 기반 인증/인가 시스템 설계   
- 동시성, 메모리, 쿼리 성능 문제 해결을 통한 백엔드 최적화 경험   
- 저사양 환경(t3.micro)에서의 운영을 통해 실제 인프라 대응 능력 강화   
     
**“개발 → 배포 → 운영 → 문제 해결”** 까지 전 과정을 경험하는 것을 목표로 한 프로젝트입니다.    
    
---
## 🧭 프로젝트 개요

- **프로젝트명** : PNOW (Place Now)
- **개발 기간** : 2024.02 ~ 2024.05 
- **리팩토링** : 2025.11 ~ 2026.03
- **인원** : 1인
- **서비스 설명** : 네이버 예약을 모티브로 한 실시간 음식점 예약 플랫폼     
   
---     
## 🚀 핵심 기능
 - Google OAuth2 로그인
 - 음식점 예약 / 취소    
 - 예약 가능 시간 조회
 - 예약 알림 메일 발송
 - 실시간 영업 상태 표시   
 - 즐겨찾기 / 검색 기능    
   
---
## 🏗️ 시스템 아키텍처 및 기술 스택

![aws_architecture](/src/main/resources/static/img/aws구조2.png)

### 💻 Backend
- Java 11
- Spring Boot 2.7.17
- Spring Data JPA
- Spring Security
- OAuth2 Client    


### 🗄️ Database
- H2    
- MariaDB (AWS RDS)   



### ⚙️ Infra / DevOps
- AWS EC2
- AWS S3
- AWS CodeDeploy
- GitHub Actions


 
<!-- **Server Port Number** : 9091 -->     

---
## ☁️ 배포 파이프라인 (CI/CD)

> **GitHub → Actions → S3 → CodeDeploy → EC2**

- Push 시 자동 빌드
- 배포 파일 S3 업로드
- CodeDeploy가 EC2에 배포
- 무중단에 가까운 자동 배포 흐름 구성

---
## ⚡ 기술적 문제 해결

### 1️⃣ 동시성 문제 해결 (중복 예약 방지)
**문제**      
- 동일 시간대 예약 중복 발생 가능
- 동시 요청 시 데이터 정합성 깨짐

**해결**    
- DB 복합 Unique Constraint 적용
- saveAndFlush()로 즉시 반영
- 예외 기반 처리
    
**결과**    
- 100건 동시 요청 테스트 → 1건만 성공
- 데이터 정합성 확보    

---

### 2️⃣ 메모리 최적화 (OOM 해결)
**문제**      
- EC2 t3.micro에서 서버 다운 발생
- 메모리 사용량 약 800MB

**해결**    
- FetchType → LAZY 변경
- JVM 튜닝
- 불필요 설정 제거
    
**결과**    
- 800MB → 400MB 절감
- 안정적 서비스 운영 가능   

---

### 3️⃣ OAuth2 인증 구조 개선
**문제**      
- HttpSession + SecurityContext 이중 저장
- 로그아웃 시 인증 불일치 발생

**해결**    
- SecurityContext 중심 구조로 통합
- CustomUserPrincipal 적용
    
**결과**    
- 인증 정합성 확보
- 보안 및 유지보수성 향상

---
## 🧠 핵심 성과
-단순 기능 구현을 넘어   
👉 동시성 / 성능 / 인프라 문제를 직접 해결
- CI/CD 자동화 구축 경험
- OAuth2 기반 인증 시스템 설계 경험
- 저사양 환경에서의 서비스 운영 경험
   
---
## 🔮 향후 개선 방향
- [ ] **Redis** 캐시 적용으로 예약 처리 속도 향상
- [ ] **EC2 인스턴스** 용량 업그레이드로 안정적 배포

---
## 📙 프로젝트 기술 문서 (Tistory Blog)

개발 과정, CI/CD 구축, 문제 해결 과정을 기록한 기술 블로그입니다.

> - 🔧 **[ [CICD] Spring Boot + GitHub Actions + AWS CodeDeploy를 활용한 CI/CD 구축(1) ](https://choihjhj.tistory.com/entry/CICD-Spring-Boot-GitHub-Actions-AWS-CodeDeploy%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-CICD-%EA%B5%AC%EC%B6%951)**
> - 🔧 **[ [CICD] Spring Boot + GitHub Actions + AWS CodeDeploy를 활용한 CI/CD 구축(2) ](https://choihjhj.tistory.com/entry/CICD-Spring-Boot-GitHub-Actions-AWS-CodeDeploy%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-CICD-%EA%B5%AC%EC%B6%952)**

---
## 🧩 ERD 구조
![ERD](ERD.png)

---

## 💥 서비스 주요 화면

| 메인 | 맛집 카테고리                                               | 예약                                                     | 예약 목록                                                 |
|------|-------------------------------------------------------|--------------------------------------------------------|-------------------------------------------------------|
| ![img](/src/main/resources/static/img/PNOW-이미지-0.jpg) | ![img](/src/main/resources/static/img/PNOW-이미지-4.jpg) | ![img](/src/main/resources/static/img/PNOW-이미지-14.jpg) | ![img](/src/main/resources/static/img/PNOW-이미지-8.jpg) |

| 즐겨찾기                                                  | 내 정보                                                   | 검색                                                     | 지난 예약 목록                                               |
|-------------------------------------------------------|--------------------------------------------------------|--------------------------------------------------------|--------------------------------------------------------|
| ![img](/src/main/resources/static/img/PNOW-이미지-9.jpg) | ![img](/src/main/resources/static/img/PNOW-이미지-17.jpg) | ![img](/src/main/resources/static/img/PNOW-이미지-16.jpg) | ![img](/src/main/resources/static/img/PNOW-이미지-15.jpg) |

---

## ✨ Swagger API Docs

- **Swagger UI** : [http://ec2-15-165-144-6.ap-northeast-2.compute.amazonaws.com:9091/swagger-ui/index.html](http://localhost:9091/swagger-ui/index.html)

<details>
  <summary>📘 Swagger UI 미리보기 (클릭해서 보기)</summary>


  <img src="src/main/resources/static/img/localhost_9091_swagger-ui_index.html.png" width="800"/>

</details>

---

## 📚 API Endpoints 요약

| 구분 | HTTP | URI | 설명             |
|------|:----:|:----|:---------------|
| **HOME** | GET | `/` | 홈페이지 조회        |
| **USER** | GET | `/users` | 회원 정보 조회       |
| | PUT | `/users/{id}` | 회원 정보 수정       |
| | DELETE | `/users/{id}` | 회원 탈퇴          |
| **STORE** | GET | `/stores` | 맛집 카테고리 페이지 조회 |
| | GET | `/stores/detail/{id}` | 가게 세부 정보 조회    |
| | GET | `/stores/category/{categoryId}/district/{districtId}` | 가게 목록 조회       |
| | GET | `/stores/search` | 가게 검색          |
| **RESERVATION** | POST | `/reservations` | 예약 등록          |
| | DELETE | `/reservations/{id}` | 예약 삭제          |
| | GET | `/reservations/stores/{storeId}` | 예약 페이지 조회      |
| | GET | `/reservations/{storeId}/availability/{reservationDate}` | 예약 가능 시간 목록 조회 |
| | GET | `/reservations/status/{status}` | 예약 목록 조회       |
| | GET | `/reservations/topbar` | 알림창 예약 목록 조회      |
| **BOOKMARK** | POST | `/bookmarks/stores/{storeId}` | 즐겨찾기 등록        |
| | GET | `/bookmarks` | 즐겨찾기 목록 조회     |
| | DELETE | `/bookmarks/{id}` | 즐겨찾기 삭제        |
| **DISTRICT** | GET | `/districts/city/{cityId}` | 지역 목록 조회       |

---