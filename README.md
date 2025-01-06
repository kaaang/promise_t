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
### WORK FLOW
1. 사용자는 선생님, 학생, 관리자로 구분된다.
2. 선생님은 수업을 생성할 수 있다.
3. 선생님은 수업에 신청할 수 있는 수업 시간을 생성할 수 있다. (수업 시간은 겹칠 수 없으며, 최대 인원을 지정해야만 한다.)
4. 학생은 수업을 신청할 수 있다.
---
### API Documents
## [Users]-------------------------------------------------------------------------
> #### [POST] /users/signup
사용자 생성(회원가입)

##### RequestBody
| Key               | Type   | Required | Description                           |
|:------------------|:-------|:---------|:--------------------------------------|
| `email`           | String | true     | 이메일                                   |
| `name`            | String | true     | 이름                                    |
| `password`        | String | true     | 비밀번호                                  |
| `passwordConfirm` | String | true     | 비밀번호 확인                               |
| `roleType`        | Enum   | true     | 권한(ROLE_TEACHER-선생님, ROLE_STUDENT-강사) |

#### Status
| Code  | Description    |
|:------|:---------------|
| `201` | 사용자 생성 성공      |
| `400` | 중복된 사용자 아이디 존재 |

> #### [POST] /users/signin
로그인

##### RequestBody
| Key               | Type   | Required | Description                           |
|:------------------|:-------|:---------|:--------------------------------------|
| `email`           | String | true     | 이메일                                   |
| `password`        | String | true     | 비밀번호                                  |

##### ResponseBody
| Key                | Type    | Required | Description   |
|:-------------------|:--------|:---------|:--------------|
| `accessToken`      | String  | true     | 엑세스 토큰        |
| `refreshToken`     | String  | true     | 리프레쉬 토큰       |
| `expiresIn`        | Integer | true     | 액세스 토큰 유효 시간  |
| `refreshExpiresIn` | Integer | true     | 리프레쉬 토큰 유효 시간 |

#### Status
| Code  | Description  |
|:------|:-------------|
| `200` | 로그인 성공       |
| `404` | 사용자를 찾을 수 없음 |

> #### [POST] /users/refresh
토큰 갱신

##### RequestBody
| Key            | Type   | Required | Description |
|:---------------|:-------|:---------|:------------|
| `refreshToken` | String | true     | 리프레쉬 토큰     |

##### ResponseBody
| Key                | Type    | Required | Description   |
|:-------------------|:--------|:---------|:--------------|
| `accessToken`      | String  | true     | 엑세스 토큰        |
| `refreshToken`     | String  | true     | 리프레쉬 토큰       |
| `expiresIn`        | Integer | true     | 액세스 토큰 유효 시간  |
| `refreshExpiresIn` | Integer | true     | 리프레쉬 토큰 유효 시간 |

#### Status
| Code  | Description                       |
|:------|:----------------------------------|
| `200` | 로그인 성공                            |
| `404` | 사용자를 찾지 못하였거나, 저장된 리프레쉬 토큰이 없을 경우 |


## [Course]-------------------------------------------------------------------------
> #### [POST] /courses
수업 생성

##### RequestBody
| Key           | Type   | Required | Description |
|:--------------|:-------|:---------|:------------|
| `title`       | String | true     | 수업 이름       |
| `description` | String | true     | 수업 설명       |

#### Status
| Code  | Description   |
|:------|:--------------|
| `201` | 수업 생성 성공      |
| `403` | 선생님 권한이 아닌 경우 |

> #### [GET] /courses:id
수업 상세 조회

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 아이디      |

##### ResponseBody
| Key           | Type   | Required | Description |
|:--------------|:-------|:---------|:------------|
| `id`          | UUID   | true     | 수업 아이디      |
| `title`       | String | true     | 수업 이름       |
| `description` | String | true     | 수업 설명       |
| `createdAt`   | Date   | true     | 수업 생성일      |
| `updatedAt`   | Date   | true     | 수업 수정일      |

#### Status
| Code  | Description    |
|:------|:---------------|
| `200` | 수업 조회 성공       |
| `404` | 수업을 찾을 수 없는 경우 |

> #### [GET] /courses
내 수업 리스트 조회

#### Query Params
| Key    | Type    | Required | Description |
|:-------|:--------|:---------|:------------|
| `page` | Integer | true     | 조회 페이지      |
| `size` | Integer | true     | 조회 사이즈      |

##### ResponseBody(data[])
| Key           | Type   | Required | Description |
|:--------------|:-------|:---------|:------------|
| `id`          | UUID   | true     | 수업 아이디      |
| `title`       | String | true     | 수업 이름       |
| `createdAt`   | Date   | true     | 수업 생성일      |
| `updatedAt`   | Date   | true     | 수업 수정일      |

#### Status
| Code  | Description   |
|:------|:--------------|
| `200` | 수업 리스트 조회 성공  |
| `403` | 선생님 권한이 아닌 경우 |

> #### [PUT] /courses/:id
수업 수정

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 아이디      |

##### RequestBody
| Key           | Type   | Required | Description |
|:--------------|:-------|:---------|:------------|
| `title`       | String | true     | 수업 이름       |
| `description` | String | true     | 수업 내용       |

#### Status
| Code  | Description    |
|:------|:---------------|
| `200` | 수업 수정 성공       |
| `404` | 수업을 찾을 수 없는 경우 |

> #### [DELETE] /courses/:id
수업 삭제

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 아이디      |

#### Status
| Code  | Description    |
|:------|:---------------|
| `200` | 수업 삭제 성공       |
| `404` | 수업을 찾을 수 없는 경우 |

## [CourseTime]-------------------------------------------------------------------------
> #### [POST] /courses/:id/times
수업 일정 생성

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 아이디      |

##### RequestBody
| Key           | Type     | Required | Description |
|:--------------|:---------|:---------|:------------|
| `maxCapacity` | String   | true     | 수업 정원       |
| `startTime`   | DateTime | true     | 수업 시작 시간    |
| `endTime`     | DateTime | true     | 수업 종료 시간    |

#### Status
| Code  | Description    |
|:------|:---------------|
| `201` | 수업 일정 생성 성공    |
| `403` | 선생님 권한이 아닌 경우  |
| `404` | 수업을 찾을 수 없는 경우 |

> #### [GET] /courses/:id/times
수업 일정 리스트 조회

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 아이디      |

##### RequestParams
| Key    | Type     | Required | Description |
|:-------|:---------|:---------|:------------|
| `from` | DateTime | true     | 조회 시작일      |
| `to`   | DateTime | true     | 조회 종료일      |

##### ResponseBody(data[])
| Key                 | Type     | Required | Description |
|:--------------------|:---------|:---------|:------------|
| `id`                | UUID     | true     | 수업 일정 아이디   |
| `startTime`         | DateTime | true     | 수업 시작 시간    |
| `endTime`           | DateTime | true     | 수업 종료 시간    |
| `maxCapacity`       | Integer  | true     | 수강 가능 인원    |

#### Status
| Code  | Description     |
|:------|:----------------|
| `200` | 수업 일정 리스트 조회 성공 |
| `404` | 수업을 찾을 수 없는 경우  |

> #### [GET] /courses/-/times/:id
수업 일정 상세 조회

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 일정 아이디   |

##### ResponseBody
| Key                 | Type     | Required | Description |
|:--------------------|:---------|:---------|:------------|
| `id`                | UUID     | true     | 수업 일정 아이디   |
| `startTime`         | DateTime | true     | 수업 시작 시간    |
| `endTime`           | DateTime | true     | 수업 종료 시간    |
| `maxCapacity`       | Integer  | true     | 수강 가능 인원    |

#### Status
| Code  | Description       |
|:------|:------------------|
| `200` | 수업 일정 조회 성공       |
| `404` | 수업 일정을 찾을 수 없는 경우 |

> #### [PUT] /courses/-/times/:id
수업 일정 수정

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 일정 아이디   |

##### RequestBody
| Key           | Type     | Required | Description |
|:--------------|:---------|:---------|:------------|
| `maxCapacity` | Integer  | true     | 수강 가능 인원    |
| `startTime`   | DateTime | true     | 수업 시작 시간    |
| `endTime`     | DateTime | true     | 수업 종료 시간    |

#### Status
| Code  | Description       |
|:------|:------------------|
| `200` | 수업 일정 수정 성공       |
| `404` | 수업 일정을 찾을 수 없는 경우 |

> #### [DELETE] /courses/-/times/:id
수업 일정 삭제

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 일정 아이디   |

#### Status
| Code  | Description       |
|:------|:------------------|
| `200` | 수업 일정 삭제 성공       |
| `404` | 수업 일정을 찾을 수 없는 경우 |

> #### [POST] /courses/-/times/:id/reservations
수업 일정 예약
해당 API는 동시성 해결을 위해서 예약 신청시 이벤트를 발행하여서 정원이 모두 차지 않았는지 확인후 상태를 변경하고 있습니다.
최초 200 반환 후 ```/courses/-/times/:id/reservations/status```를 통하여 예약 완료 상태를 확인해야 합니다.

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 일정 아이디   |

#### Status
| Code  | Description       |
|:------|:------------------|
| `200` | 수업 일정 삭제 성공       |
| `400` | 수업을 예약할 수 없는 경우   |
| `403` | 학생 권한이 아닌경우       |
| `404` | 수업 일정을 찾을 수 없는 경우 |

> #### [POST] /courses/-/times/:id/reservations/status
수업 일정 예약 상태 확인

#### PathVariables
| Key           | Type | Description |
|:--------------|:-----|:------------|
| `id`          | UUID | 수업 일정 아이디   |

##### RequestBody
| Key      | Type | Required | Description                              |
|:---------|:-----|:---------|:-----------------------------------------|
| `status` | Enum | false    | SUCCESS(성공), PENDING(대기중), FAILED(예약 실패) |

#### Status
| Code  | Description    |
|:------|:---------------|
| `200` | 수업 일정 예약 조회 성공 |