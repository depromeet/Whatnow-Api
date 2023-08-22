# 13th-6team-server
66666년만에 환생한 흑마법사


[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=depromeet_Whatnow-Api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=depromeet_Whatnow-Api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=depromeet_Whatnow-Api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=depromeet_Whatnow-Api)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=depromeet_Whatnow-Api&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=depromeet_Whatnow-Api)


## 서비스
- Whatnow-Api
- Whatnow-Location


# Whatnow<img src="https://play-lh.googleusercontent.com/NfEaR4D-qhL5eXJ8bRF5nY75Z6bCcbsa4XQ7334kuhI3GblNU_Q0hmI9YM6pid7cv2k=w480-h960-rw" align=left width=100>

> 약속 실시간 위치 공유 어플리케이션 • <b>백엔드</b> 레포지토리

<br/>

<br/>

사진 들어갈 자리

<br/>

## ✨ 서비스 관련
- [구글 스토어](https://play.google.com/store/apps/details?id=com.depromeet.whatnow)


<br>

## 📚 사용 스택
<div align="left">
<div>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/MySQL-4479A1.svg?style=flat-square&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=Amazon AWS&logoColor=white">
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white">
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=flat-square&logo=JSON Web Tokens&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/SonarCloud-F3702A?style=flat-square&logo=SonarCloud&logoColor=white">
<img src="https://img.shields.io/badge/Amazon CloudWatch-FF4F8B?style=flat-square&logo=Amazon CloudWatch&logoColor=white">
<img src="https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=slack&logoColor=white">
</div>

</div>

<br/>


![1](https://github.com/depromeet/Whatnow-Api/assets/54030889/b5b024b5-de44-4f7a-bcb6-6249680ec234)
![2](https://github.com/depromeet/Whatnow-Api/assets/54030889/26ef88f1-c2b4-402a-bae1-c8671f8b6894)
![3](https://github.com/depromeet/Whatnow-Api/assets/54030889/6f8d6f62-4ed0-48a3-8b04-ff658217d465)




## 📁 Project Structure
DDD와 멀티모듈 구조를 사용했습니다.
각 도메인별 연관관계를 최대한 끊어내고
도메인 이벤트를 활용해 도메인간의 의존성을 줄였습니다.
```bash
├── Whatnow-Api  
│       └── com.depromeet.whatnow 
│           └── <각 usecase 별 패키지> # ex : order,issuedTicket
│               └── controller
│               └── dto
│               └── mapper # 분산락으로 인한 다른트랜잭션일 때 최신의 정보를 가져오기 위함
│               └── service # usecase 파사드 형태로 다른 도메인서비스들의 반환값을 모아 응답값 생성
├── Whatnow-Common  # 공통으로 쓰이는 어노테이션, 에러 코드등
├── Whatnow-Domain   
│       └── com.depromeet.whatnow     
│           ├── common  # 분산락 aop , 도메인 이벤트 발행
│           └── domains 
│               └── <도메인>  # 각도메인 ex : order ,ticket
│                   └── adaptor # 도메인 리포지토리를 한번 더 감싼 컴포넌트
│                   └── domain # 도메인 오브젝트
│                   └── exception # 도메인별 에러 정의
│                   └── repostiory # 도메인 리포지토리
│                   └── service # 도메인 서비스, 도메인 이벤트 핸들러
├── Whatnow-Infrastructure  # 레디스 , feignClient(외부 api 콜) ,s3 등.
```


### 💻 개발자 선생님들 💻
<table>
    <tr align="center">
        <td><B>백엔드</B></td>
        <td><B>백엔드</B></td>
        <td><B>백엔드</B></td>
    </tr>
    <tr align="center">
        <td><B>김동호</B></td>
        <td><B>이서현</B></td>
        <td><B>이찬진</B></td>
    </tr>
    <tr align="center">
        <td>
            <img src="https://github.com/kdomo.png?size=100">
            <br>
            <a href="https://github.com/kdomo"><I>kdomo</I></a>
        </td>
        <td>
            <img src="https://github.com/BlackBean99.png?size=100" width="100">
            <br>
            <a href="https://github.com/BlackBean99"><I>BlackBean99</I></a>
        </td>
        <td>
            <img src="https://github.com/ImNM.png?size=100" width="100">
            <br>
            <a href="https://github.com/ImNM"><I>ImNM</I></a>
        </td>
    </tr>
<table>
<table>
    <tr align="center">
        <td><B>안드로이드</B></td>
        <td><B>안드로이드</B></td>
        <td><B>안드로이드</B></td>
    </tr>
    <tr align="center">
        <td><B>현영우</B></td>
        <td><B>윤여준</B></td>
        <td><B>조준장</B></td>
    </tr>
    <tr align="center">
        <td>
            <img src="https://github.com/ieeh1016.png?size=100">
            <br>
            <a href="https://github.com/ieeh1016"><I>ieeh1016</I></a>
        </td>
        <td>
            <img src="https://github.com/yjyoon-dev.png?size=100" width="100">
            <br>
            <a href="https://github.com/yjyoon-dev"><I>yjyoon-dev</I></a>
        </td>
        <td>
            <img src="https://github.com/junjange.png?size=100" width="100">
            <br>
            <a href="https://github.com/junjange"><I>junjange</I></a>
        </td>
    </tr>
<table>


### 🎨 디자인 선생님들 🎨

<table>
    <tr align="center">
        <td><B>디자인</B></td>
        <td><B>디자인</B></td>
        <td><B>디자인</B></td>
    </tr>
    <tr align="center">
        <td><B>박성경</B></td>
        <td><B>나태현</B></td>
        <td><B>조규원</B></td>
    </tr>
    <tr align="center">
        <td>
            <img src="https://mir-s3-cdn-cf.behance.net/user/100/d346a7453045023.649e225b6c5ba.png" width="100">
            <br>
            <a href="https://www.behance.net/noapark"><I>noapark</I></a>
        </td>
        <td>
            <img src="https://mir-s3-cdn-cf.behance.net/user/230/6c5574362427819.634c1e2990f81.jpg" width="100">
            <br>
            <a href="https://www.behance.net/taehyeonna"><I>taehyeonna</I></a>
        </td>
        <td>
            <img src="https://mir-s3-cdn-cf.behance.net/user/230/757a7f616212273.648282d296b12.jpg" width="100">
            <br>
            <a href="https://www.behance.net/ku_oni"><I>ku_oni</I></a>
        </td>
    </tr>
<table>
