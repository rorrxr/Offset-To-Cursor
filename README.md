# Offset-To-Cursor - 커서 기반 페이징 실습 프로젝트

## 📖 프로젝트 소개

**Offset-To-Cursor**는 기존의 오프셋 기반 페이징이 가지는 성능 문제를 해결하기 위해 **커서 기반 페이징 방식**을 도입하는 과정을 실습하는 예제 프로젝트입니다.  
QueryDSL을 활용하여 커서 기반 페이징 쿼리를 효율적으로 구현하였으며, 오프셋 방식과 커서 방식의 차이를 실습을 통해 비교할 수 있습니다.

- 오프셋 방식: `page`, `size` 기반 전통적 페이징
- 커서 방식: `lastId`, `size` 기반으로 고정 성능 확보
- 두 방식을 모두 API로 구현하여 구조와 장단점 비교 가능

> 📚 관련 블로그: [오프셋 기반 vs 커서 기반 페이징 기법 비교](https://deve1opment-story.tistory.com/)

---

## 🚀 주요 기능

| 기능 구분       | 설명 |
|----------------|------|
| ✅ 오프셋 페이징 API | `/products/offset` 엔드포인트 제공<br/>page & size 기반 전통적 페이징 처리 |
| ✅ 커서 페이징 API  | `/products/cursor` 엔드포인트 제공<br/>lastProductId 기반 커서 페이징 처리 |
| ✅ QueryDSL 적용   | 조건문, 정렬 등을 가독성 있게 구성한 커서 페이징 쿼리 작성 |
| ✅ Spring Boot 3.x 호환 | 최신 Spring 환경에서 QueryDSL 설정 및 페이징 로직 구성 |

---

## 💻 기술 스택

| 영역       | 기술 |
|------------|------|
| Language   | Java 21 |
| Backend Framework | Spring Boot 3.x, Spring Data JPA |
| Query Builder | QueryDSL |
| Database | MySQL, H2 (개발용) |
| Build Tool | Gradle |
| IDE | IntelliJ IDEA |
