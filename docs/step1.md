## Step 1
> 회원가입 & 로그인 구현

### 회원가입 
- `user/signup` 
- h2 database 사용

### 로그인
- `user/login`
- id(PK), userId, username, email, password 를 저장했다. 
- 여기서 userId 와 email 은 unique 값으로 설정했다. 만약 이미 존재하는  userId 와 email을 입력하면 `DataIntegrityViolationException` 이 뜨도록 했다.

-----
### Spring Security
- 스프링 시큐리티는 스프링 기반 어플리케이션의 인증과 권한을 담당하는 스프링 하위 프레임워크이다. 
- 스프링 시큐리티의 세부 설정은 SecurityFilterChain 빈을 생성하여 설정했다. 

### 프론트
- 프론트는 bootstrap 사용

----
### 구현 화면
- 회원가입

  ![image](https://github.com/ddoddii/ddoddii.github.io/assets/95014836/04ad9802-5a5b-46f3-8561-f5024dfd9cf3)

- 로그인

  ![image](https://github.com/ddoddii/ddoddii.github.io/assets/95014836/a9c6545a-3ddb-43f1-93c9-fc08d05ac2c5)