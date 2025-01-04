# Promise-T Project
현재의 코드 스타일을 점검 및 사용 기술들의 모르는 점들을 점검하기 위한 프로젝트 입니다.

해당 프로젝트는 개발 서버까지 배포를 목표로 진행중이며 혼자 진행하는 프로젝트인 만큼 기획을 보완하면서 계속적으로 변경될 예정입니다.

---



## UBIQUITOUS LANGUAGE
| NAME       | Description      |
|------------|------------------|
| Course     | 수업이라는 대분류를 지칭한다. |
| CourseTime | 수업 내에서 일정을 지칭한다. |

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
| `redis`              | `6379` |

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

