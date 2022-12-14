## ๐ Getting Started

```
./gradlew bootRun
java -jar build/libs/*.jar
```

## ์ฌ์ฉ ๊ธฐ์ 
* Kotlin
* Spring Boot
* JPA
* Spring Security
* JWT
* H2
* Kotlin Rest Assured
* Kotest

## API
### ์ธ์ฆ๋ฒํธ ์์ฒญ
```
Request
POST apis/phone/authentication
{
    phoneNumber: ํธ๋ํฐ๋ฒํธ
}

Response
HTTP/1.1 200 OK
{
    phoneNumber : ํธ๋ํฐ๋ฒํธ,
    authenticationNumber : ์ธ์ฆ๋ฒํธ
}
```

### ์ธ์ฆ๋ฒํธ ๊ฒ์ฆ
```
Request
PUT apis/phone/authentication
{
    phoneNumber: ํธ๋ํฐ๋ฒํธ,
    authenticationNumber : ์ธ์ฆ๋ฒํธ
}

Response
HTTP/1.1 204 NO CONTENT
```

### ๋ฉค๋ฒ ์ธ์ฆ๋ฒํธ ์์ฒญ
```
Request
POST apis/phone/authentication/members
{
    phoneNumber: ํธ๋ํฐ๋ฒํธ
}

Response
HTTP/1.1 200 OK
{
    phoneNumber: ํธ๋ํฐ๋ฒํธ,
    authenticationNumber : ์ธ์ฆ๋ฒํธ
}
```

### ๋ฉค๋ฒ ๊ฐ์
```
Request
POST apis/members
{
    email: ์ด๋ฉ์ผ,
    nickname: ๋๋ค์,
    password: ๋น๋ฐ๋ฒํธ,
    name: ์ด๋ฆ,
    phone: ํธ๋ํฐ๋ฒํธ
}

Response
HTTP/1.1 201 CREATED
-H Location apis/members/id
```

### ๋ก๊ทธ์ธ
```
Request
POST apis/members/login
{
    id: ์ด๋ฉ์ผ or ๋๋ค์ or ํธ๋ํฐ๋ฒํธ,
    password: ๋น๋ฐ๋ฒํธ
}

Response
HTTP/1.1 200 OK
{
    ์์ธ์คํ ํฐ
}
```

### ์ฌ์ฉ์ ์ ๋ณด ์กฐํ
```
Request
GET apis/members/{id}

Response
HTTP/1.1 200 OK
{
    email: ์ด๋ฉ์ผ,
    nickname: ๋๋ค์,
    password: ๋น๋ฐ๋ฒํธ,
    name: ์ด๋ฆ,
    phone: ํธ๋ํฐ๋ฒํธ
}
```

### ๋น๋ฐ๋ฒํธ ๋ณ๊ฒฝ
```
Request
PATCH apis/members
{
    phone: ํธ๋ํฐ๋ฒํธ,
    password: ๋น๋ฐ๋ฒํธ
}
Response
HTTP/1.1 204 NO CONTENT
```
### ํน๋ณํ ์๊ฐํ๊ณ  ์ถ์ ๋ถ๋ถ
* ๋ฏธํกํ์ง๋ง ํ์คํธ ์ปค๋ฒ๋ฆฌ์ง๋ฅผ ์ต๋ํ ๋์ด๋ ค๊ณ  ๋ธ๋ ฅํด๋ณด์์ต๋๋ค. ์๊ฐ ๊ด๊ณ์ ์๋น์ค ๋ ์ด์ด ๋ถ๋ถ ํ์คํธ๋ฅผ ์์ฑํ์ง ๋ชปํ์ง๋ง ์ฃผ์ด์ง๋ค๋ฉด Mockk๋ฅผ ์ฌ์ฉํ์ฌ ์์ฑํด๋ณด๊ณ  ์ถ์ต๋๋ค.
* ์ฌ์ฉ์ ์์ ์์ ํ์คํธ๋ฅผ ์์ฑํ๋ ์ธ์ํ์คํธ๋ฅผ ์์ฑ ํ์์ต๋๋ค. ์๋๋ฆฌ์ค ๋ณ ํ์คํธ๋ฅผ ์์ฑํ์ฌ APIs์ด ์ด๋ค ํํ๋ก ๋์ํ๋์ง ํ ๋์ ํ์ธ์ด ๊ฐ๋ฅํฉ๋๋ค.
