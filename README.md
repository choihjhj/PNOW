# 📖 PNOW (Place Now)

> **Spring Boot 기반 음식점 예약 서비스**  
> **CI/CD 자동 배포(AWS EC2, CodeDeploy)** 와  
> **OAuth2 소셜 로그인(Google)**, **Spring Security**, **JPA** 등을 활용한  
> **백엔드 개발 역량 강화를 위한 개인 프로젝트**입니다.

---

## 🧭 프로젝트 개요

- **프로젝트명** : PNOW (Place Now)
- **개발 기간** : 2024.02 ~ 2025.11 
- **개발자** : 1인 개인 프로젝트
- **개발 목적** :  
  Spring Boot + AWS 환경에서 **자동 배포 CI/CD 파이프라인 구축 경험**과  
  OAuth2 기반 **보안 및 인증 시스템 설계** 능력 강화를 목표로 함.
- **핵심 기능** :
  - 음식점 예약 CRUD
  - 예약 가능 시간 조회
  - 실시간 영업 상태 표시 (영업 중 / 준비 중)
  - 즐겨찾기 / 검색 기능
  - Google OAuth2 로그인

---

## 🏗️ 시스템 아키텍처 및 기술 스택

![aws_architecture](/src/main/resources/static/img/aws구조2.png)

### 💻 개발 환경
- **Language** : Java 11
- **Editor** : IntelliJ IDEA
- **Build Tool** : Gradle 8.3
- **Framework** : Spring Boot 2.7.17



### 🗄️ Database
- **테스트용** : H2 Database
- **운영용** : AWS RDS (MariaDB)



### ⚙️ CI / CD & Infrastructure
- **CI/CD** : GitHub Actions, AWS S3, AWS CodeDeploy
- **Server** : AWS EC2
- **Storage** : Amazon S3 (배포 파일 및 정적 리소스 저장)



### 🔐 주요 라이브러리
- Spring Boot Web
- Spring Data JPA
- Lombok
- Spring Security
- OAuth2-Client (Google 소셜 로그인)
- Validation
- Swagger
- Thymeleaf   
<!-- **Server Port Number** : 9091 -->     

---
## ☁️ 배포 파이프라인 (CI/CD)

> **GitHub → GitHub Actions → AWS S3 → AWS CodeDeploy → EC2**

1. GitHub에 push 발생 시 **GitHub Actions** 자동 빌드 실행
2. 빌드 결과(`.jar`)를 `.zip` 형태로 **AWS S3 업로드**
3. **AWS CodeDeploy**가 S3 파일을 EC2 인스턴스로 전달
4. **EC2에서 자동 배포 및 실행** 

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

> *사용자는 로그인 후 음식점을 검색, 예약, 즐겨찾기 할 수 있습니다.*


---

## ✨ Swagger API Docs

- **Swagger UI** : [http://localhost:9091/swagger-ui/index.html](http://localhost:9091/swagger-ui/index.html)

<details>
  <summary>📘 Swagger UI 미리보기 (클릭해서 보기)</summary>


  <img src="src/main/resources/static/img/localhost_9091_swagger-ui_index.html.png" width="800"/>

</details>

---

## 📚 API Endpoints 요약

| 구분 | HTTP | URI | 설명 |
|------|:----:|:----|:----|
| **HOME** | GET | `/` | 홈페이지 조회 |
| **USER** | GET | `/users` | 회원 정보 조회 |
| | PUT | `/users/{id}` | 회원 정보 수정 |
| | DELETE | `/users/{id}` | 회원 탈퇴 |
| **STORE** | GET | `/stores` | 맛집 카테고리 페이지 조회 |
| | GET | `/stores/detail/{id}` | 가게 세부 정보 조회 |
| | GET | `/stores/search` | 가게 검색 |
| **RESERVATION** | POST | `/reservations` | 예약 등록 |
| | DELETE | `/reservations/{id}` | 예약 삭제 |
| | GET | `/reservations/stores/{storeId}` | 예약 페이지 조회 |
| **BOOKMARK** | POST | `/bookmarks/stores/{storeId}` | 즐겨찾기 등록 |
| | DELETE | `/bookmarks/{id}` | 즐겨찾기 삭제 |
| **DISTRICT** | GET | `/districts/city/{cityId}` | 지역 목록 조회 |

---
<!--
## ⚡ 트러블슈팅 (Troubleshooting)

### 1️⃣ CI/CD 배포 시 CodeDeploy Permission Denied 오류
- **문제 상황** : GitHub Actions에서 AWS S3 업로드 후, CodeDeploy 실행 시 “Permission denied” 발생
- **원인 분석** : EC2 인스턴스의 IAM Role 권한 부족 (S3 접근 불가)
- **해결 방법** :
  - EC2 IAM Role에 `AmazonS3FullAccess` 정책 추가
  - `appspec.yml` 내 경로 권한 수정 (`/home/ec2-user` → `/home/ubuntu`)
- **결과** : 정상적으로 CodeDeploy가 배포 스크립트를 실행하며 자동 배포 성공

---

### 2️⃣ OAuth2 로그인 시 Redirect URI mismatch 오류
- **문제 상황** : 구글 OAuth2 로그인 시 “redirect_uri_mismatch” 에러 발생
- **원인 분석** : Google Cloud 콘솔에 등록된 Redirect URI와 실제 서버의 포트 불일치
- **해결 방법** :
  - `application-oauth.properties` 내 리디렉션 URL 수정
  - Google Cloud Console에 EC2 배포 서버 도메인을 추가 등록
- **결과** : 로컬 및 EC2 환경 모두 로그인 정상 동작

---

### 3️⃣ JPA LazyInitializationException 발생
- **문제 상황** : 예약 조회 시 `LazyInitializationException` 발생
- **원인 분석** : 트랜잭션 범위 밖에서 연관 엔티티 접근
- **해결 방법** :
  - `@Transactional(readOnly = true)`를 서비스 계층에 적용
  - `fetch = FetchType.LAZY` 대신 `JOIN FETCH`로 필요한 데이터만 즉시 로딩
- **결과** : 불필요한 쿼리 최소화 및 조회 성능 개선
-->
## 🚀 기술적 성취

✅ **무중단 배포 환경 구성**
- GitHub Actions + S3 + CodeDeploy 조합으로 CI/CD 구축
- EC2에 배포 자동화 스크립트 작성 및 서비스 재기동 자동화

✅ **보안 및 인증**
- Spring Security를 통한 사용자 인증/인가 처리
- OAuth2 Google 소셜 로그인 구현

✅ **데이터 관리**
- Spring Data JPA 기반의 도메인 설계 및 트랜잭션 관리
- H2를 통한 테스트 환경과 MariaDB 운영 환경 분리

✅ **문서화 및 협업 툴**
- Swagger를 통한 REST API 문서 자동화
- Gradle 빌드 파이프라인 정리

---

## 🔮 향후 개선 방향
- [ ] **Docker + Nginx** 기반 무중단 Blue/Green 배포 구현
- [ ] **Redis** 캐시 적용으로 예약 처리 속도 향상



---

## 🧠 프로젝트 회고
> Spring Boot와 AWS의 연계를 통해 백엔드 개발뿐만 아니라  
> **실제 배포·운영 환경을 직접 설계**하며 DevOps 전반을 이해할 수 있었던 프로젝트입니다.  
> CI/CD 자동화, 보안, 데이터베이스 설계 등 **서비스 운영에 필요한 전 과정을 경험**했습니다.


<!--
# 📖 < PNOW > 서비스 소개
플레이스나우(**PNOW**)는 **SpringBoot Framework** 로 개발한 토이 프로젝트로,   
음식점 예약 **CURD** 기능을 기반으로 **Spring Security + OAuth2 구글 소셜 로그인** 을 적용해     
**무중단 배포(AWS EC2)** 와 **CI/CD(Github Actions, S3, AWS CodeDeploy)** 경험을 쌓아 보고자 개발한 프로젝트입니다.
    
개발된 기능으로는 실시간으로 예약 목록 확인 및 등록/취소를 할 수 있고, 현재 시간을 기준으로    
음식점이 영업중/영업준비중 인지와 즐겨찾기 및 검색 기능도 포함되어 있습니다.
      
## 🏗️ 시스템 아키텍처 및 기술 스택
![img.png](/src/main/resources/static/img/aws구조2.png)      
💻 개발 환경     
- **Language** : java 11
- **Editor** : Intellij IDEA
- **Build Tool** : Gradle 8.3
- **Framework** : Springboot 2.7.17        
  🗄️ Database     
- **테스트용** : H2 Database
- **운영용** : AWS RDS (MariaDB)     
  ⚙️ CI / CD & Infrastructure       
- **CI & CD** : : GitHub Actions, AWS S3, AWS CodeDeploy
- **Server** : AWS EC2
- **Storage** : Amazon S3 (배포 파일 및 정적 리소스 저장)
  🔐 주요 라이브러리
- **Library** :
  - SpringBoot Web 
  - Spring Data JPA 
  - Lombok 
  - Spring Security 
  - Oauth2-Client 
  - Validation 
  - Swagger 
  - Thymeleaf
- **Server Port Number** : 9091     
      
## 🙂 프로젝트 기간    
- 2024.02 ~ 2024.04(3개월) : AWS EC2 with Shell Script via SSH 수동 배포까지 완료
- 2025.11 : Github Actions(CI) -> 압축파일(.zip)을 AWS S3에 업로드 -> AWS CodeDeploy를 이용해 배포(CD)

-->