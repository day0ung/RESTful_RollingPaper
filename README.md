# 목차
* [개발환경](#개발환경)
* [프로젝트 구조](#프로젝트-구조)
* [구현 방법에 대한 설명](#구현-방법에-대한-설명)

---
## 개발환경
#### 기본환경
* Mac OS
* IntelliJ
* H2 console
* GitHub
* Postman
* SourceTree


#### 주요 프레임워크 / 라이브러리
* Java 11 openjdk 
* SpringBoot 2.7.14
* Spring Data JPA
* SpringBoot Security
* Junit5
* QueryDsl
* Swagger

## 프로젝트 구조
```java
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/restful_api
│   │   │   │   ├── api/
│   │   │   │   ├── config/
│   │   │   │   ├── domain/
│   │   │   │   ├── exception/
│   │   │   │   ├── security/
│   │   │   │   ├── service/
│   │   │   │   ├── util/
│   │   │   │   ├
│   │   │   ├── resources/
│   │   │   │   ├── application.yml
│   │   ├── test/

```
* api/ :API 관련 클래스들을 포함하는 디렉토리입니다. 이 디렉토리에는 컨트롤러, 요청/응답 모델 등이 포함되어 있습니다
* config/: 프로젝트의 설정 관련 클래스들을 포함하는 디렉토리입니다. Spring 설정, 데이터베이스 설정 등이 포함되어 있습니다.
* domain/: 도메인 객체나 엔티티 클래스들이 위치하는 디렉토리입니다. 데이터베이스 테이블과 매핑되는 클래스들이 포함되어 있습니다.
* exception/: 예외 처리 관련 클래스들을 포함하는 디렉토리입니다. 커스텀 예외 클래스나 예외 핸들러 등이 포함되어 있습니다
* security/: 보안 관련 클래스들을 포함하는 디렉토리입니다. Spring Security 설정, 사용자 인증/로그인 필터 등이 포함되어 있습니다.
* service/: 비즈니스 로직 관련 클래스들을 포함하는 디렉토리입니다. 서비스 클래스들이 위치하며, 서비스 인터페이스와 구현체들이 포함되어 있습니다.
* util/: 유틸리티 클래스나 도구들을 포함하는 디렉토리입니다.


#### API 명세

1. 사용자 - USERS 

| HTTP | API               | Summary          |
|------|-------------------|------------------|
 | POST | /api/users/signup | 새로운 사용자 회원가입 API |
| POST | /api/users/signin | 사용자 로그인  API     |


2. 롤링페이퍼 - PAPER

| HTTP   | API         | Summary          |
|--------|-------------|------------------|
| POST   | /api/paper  | 롤링페이퍼 등록 API     |
| PUT    | /api/paper/{paperId} | 롤링페이 수정 API      |
 | DELETE | /api/paper/{paperId} | 롤링페이퍼 삭제  API    |
 | GET    | /api/paper | 롤링페이퍼 전체 조회  API |
 | GET    | /api/paper/{paperId} | 롤링페이퍼 단건 조회  API |


3. 롤링페이퍼 댓글 - COMMENT  

| HTTP | API                          | Summary             |
|------|------------------------------|---------------------|
| POST | /api/comment/paper/{paperId} | 댓글 등록 API           |
| PUT  | /api/comment/{commentId}     | 댓글 수정 API           |
| DELETE  | /api/comment/{commentId}     | 댓글 삭제 API           |
| GET  | /api/comment/{commentId}     | 댓글 단건 조회 API        |
| GET  | /api/comment/paper/{commentId} | 롤링페이퍼에 속한 댓글 조회 API |


## 구현 방법에 대한 설명

### 주요 구현 내용
#### 1. 주어진 비즈니스 요구사항의 API 구현
프로젝트는 주어진 요구사항을 기반으로 다양한 API를 구현하였습니다.  
**- USER API** `/api/users/**`   
사용자의 회원가입/로그인 기능을 구현하였으며
게시글의 작성자 xUserId를 검증하기 위해 추가하였습니다.  

**- PAPER API** `/api/paper/**`  
롤링페이퍼의 생성/삭제 기능을 구현하였으며, 권한을 가진 사용자만이 삭제/생성할 수 있도록하였습니다.  
 롤링페이퍼 삭제시, 롤링페이퍼에 해당하는 게시글도 삭제 될 수 있게 ORM을 작성하였습니다
목록조회 API에서 페이징과 검색기능은 QueryDsl을 활용하였습니다

**- COMMENT API** `/api/comment/**`  
댓글 API /api/comment/** 에서는 요청의 CRUD 기능을 구현하였습니다.


#### 2. 인가처리
프로젝트에서는 Spring Security를 활용하여 인가 처리를 구현하였습니다. JWT(JSON Web Token)를 사용하여 인증된 사용자에게 토큰을 발급하고, 이 토큰을 사용하여 API 엔드포인트에 접근할 때 권한을 확인합니다.
1. **JWT 토큰 발급**: 사용자가 로그인하면, Spring Security는 해당 사용자에게 JWT 토큰을 발급합니다. 이 토큰은 사용자의 인증 정보를 포함하고 있으며, 유효 기간 등의 정보를 담고 있습니다.

2. **권한 설정**: 프로젝트에서는 `USER`과 `GEUST`라는 두 가지 권한을 정의하였습니다. `ADMIN` 권한은 특정 관리 작업에 대한 권한을 가지며, `USER` 권한은 일반적인 사용자의 권한을 나타냅니다.

Spring Security의 설정을 통해 각 API 엔드포인트에 대한 접근 권한을 지정하였습니다. 이를 통해 `ADMIN` 권한이 필요한 작업에는 `ADMIN` 권한을 가진 사용자만 접근할 수 있으며, `USER` 권한이 필요한 작업에는 `USER` 권한을 가진 사용자만 접근할 수 있습니다.


#### 3. 에러 헨들링과 일관성
@RestControllerAdvice를 사용한 클래스를 만들어 전역적으로 예외 처리를 관리하였습니다.  
애플리케이션의 여러 부분에서 발생하는 예외를 중앙에서 일관된 방식으로 처리할 수 있도록 하고, 클라이언트에게 일관된 에러 응답을 제공할 수 있도록 구현하였습니다  

또한 일관된 응답 구조를 가질 수 있도록 ResponseEntity를 활용하였으며, HTTP상태코드를 반환하도록하였습니다

#### 4. 단위 테스트, 통합 테스트
프로젝트에서는 JUnit 5를 활용하여 테스트를 작성하였습니다. 다양한 테스트 어노테이션을 활용하여 단위 테스트와 통합 테스트를 목적에 맞게 구현하였습니다.
1. **컨트롤러 통합 테스트**: 컨트롤러 테스트를 위해 `@SpringBootTest`, `@AutoConfigureMockMvc`, `@Transactional` 어노테이션을 사용하여 통합 테스트를 진행하였습니다. 이를 통해 실제 스프링 컨텍스트를 로드하고, MockMvc를 활용하여 API 엔드포인트의 동작을 테스트하였습니다.

2. **도메인/서비스 단위 테스트**: 도메인과 서비스 레이어의 테스트를 위해 `@DataJpaTest`와 `@ExtendWith(MockitoExtension.class)`를 사용하였습니다. `@DataJpaTest`를 사용하여 JPA 관련 테스트를 진행하고, `MockitoExtension`을 사용하여 Mock 객체를 생성하여 독립적인 단위 테스트를 수행하였습니다


