## 🚀 Getting Started

```
./gradlew bootRun
java -jar build/libs/*.jar
```

## 사용 기술
* Kotlin
* Spring Boot
* JPA
* Spring Security
* JWT
* H2
* Kotlin Rest Assured
* Kotest

## API
### 인증번호 요청
```
Request
POST apis/phone/authentication
{
    phoneNumber: 핸드폰번호
}

Response
HTTP/1.1 200 OK
{
    phoneNumber : 핸드폰번호,
    authenticationNumber : 인증번호
}
```

### 인증번호 검증
```
Request
PUT apis/phone/authentication
{
    phoneNumber: 핸드폰번호,
    authenticationNumber : 인증번호
}

Response
HTTP/1.1 204 NO CONTENT
```

### 멤버 인증번호 요청
```
Request
POST apis/phone/authentication/members
{
    phoneNumber: 핸드폰번호
}

Response
HTTP/1.1 200 OK
{
    phoneNumber: 핸드폰번호,
    authenticationNumber : 인증번호
}
```

### 멤버 가입
```
Request
POST apis/members
{
    email: 이메일,
    nickname: 닉네임,
    password: 비밀번호,
    name: 이름,
    phone: 핸드폰번호
}

Response
HTTP/1.1 201 CREATED
-H Location apis/members/id
```

### 로그인
```
Request
POST apis/members/login
{
    id: 이메일 or 닉네임 or 핸드폰번호,
    password: 비밀번호
}

Response
HTTP/1.1 200 OK
{
    엑세스토큰
}
```

### 사용자 정보 조회
```
Request
GET apis/members/{id}

Response
HTTP/1.1 200 OK
{
    email: 이메일,
    nickname: 닉네임,
    password: 비밀번호,
    name: 이름,
    phone: 핸드폰번호
}
```

### 비밀번호 변경
```
Request
PATCH apis/members
{
    phone: 핸드폰번호,
    password: 비밀번호
}
Response
HTTP/1.1 204 NO CONTENT
```
### 특별히 소개하고 싶은 부분
* 미흡하지만 테스트 커버리지를 최대한 높이려고 노력해보았습니다. 시간 관계상 서비스 레이어 부분 테스트를 작성하지 못했지만 주어진다면 Mockk를 사용하여 작성해보고 싶습니다.
* 사용자 시점에서 테스트를 작성하는 인수테스트를 작성 하였습니다. 시나리오 별 테스트를 작성하여 APIs이 어떤 형태로 동작하는지 한 눈에 확인이 가능합니다.
