# Promise-T Project
이 프로젝트는 개인 PT 또는 선생님과 학생의 관계에서 수업 일정 예약에 필요한 서비스가 있었으면 좋겠다는 마음으로 시작하였습니다.

프로젝트는 총 3단계로 구상하고 있습니다.

- STEP 1. 수업 생성 및 수업 예약 관리 시스템 구축
- (TODO) STEP 2. 수업 일지 기록 시스템 구축
- (TODO) STEP 3. 채팅 시스템 구축

위의 과정들을 목표로 진행하고 있습니다.

또한 개발적으로 현재 코드 스타일을 점검 및 사용 기술들의 모르는 점들을 확인하기 위해 진행하고 있습니다.

최종적인 목표는 서버 배포 및 클라이언트단 구현까지가 목표입니다.

---

## API Documents
```
https://app.getpostman.com/join-team?invite_code=3cf74fff2b78ea5a01962be091d4aa8883ed91bf56700f006b69600f784fa539
```

---
## Stack
 * [JAVA 17]
   * Spring Boot 3.x 버전을 사용을 위한 17버전 채택
   * Sealed Classes 도입으로 인한 상속 제한 JDK 내부 구현을 캡슐화 하여 접근 제한
   * apple silicon 에 대한 공식 지원 추가
   > 위와 같이 11버전과의 차이가 존재한다고 알고만 있었으며 직접 사용해보면서 차이점을 느껴보는것을 목표로 프로젝트를 진행할 예정입니다.
특히나 현재 개발 환경이 m1 mac을 사용하고 있기 때문에 공식 지원에 대한 최적화가 느껴지는지가 개인적으로 궁금합니다.
 * [SpringBoot 3.3.x]
   * 평소 2.6 버전을 사용해왔기 때문에 어떤 부분이 다른지 직접 경험하고 싶어 해당 버전을 선택하였습니다.
   * 3.x 버전 이상으로는 java17을 요구하기 때문에 따라서 java 버전도 평소 사용하던 11버전이 아닌 17 버전을 사용하였습니다.
   * Spring Framework 6.x 버전과 호환되기 때문에 jakarta 패키지로 변경된것을을 확인하며 개발해볼 예정입니다.
   * 모듈화 관리에 최적화가 되었다 하기 때문에 추후 프로젝트를 모듈로 분리해볼 예정입니다.

| Name                 | Port   |
|:---------------------|:-------|
| `Spring`             | `8080` |
| `postgresql-master`  | `5435` |
| `postgresql-replica` | `5436` |
| `kafka`              | `9092` |
| `zookeeper`          | `2181` |
| `kafka-ui`           | `8081` |
---
## How To Run
- Docker 및 Docker Compose가 설치되어 있어야 합니다.
- Java 17 이상이 설치되어 있어야 합니다.
- Gradle이 설치되어 있어야 합니다.
- docker-compose 파일이 mac/linux용으로 경로가 작성되어 있습니다.

1. docker-compose 실행
```shell
docker compose -f ./.promise_t-test/docker-compose.yml up -d
```

2. spring 실행
```shell
./gradlew bootRun
```
---
## 배포 환경
* 만약 배포 수준의 코드가 완성이 된다면 CodePipeLine, EB 등을 사용해 배포하는것을 목표로 하고 있습니다.
---
## Others
* 해당 프로젝트는 pre-commit 시에 spotless check를 수행하고 있습니다. 커밋전 ```./gradlew stoplessApply``` 를 수행한 뒤 커밋해야 합니다.
---