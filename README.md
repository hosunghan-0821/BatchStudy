# Spring Batch 📦 모니터링 학습 기록

> **대상 Job:** Excel → `Product`, `Product_Size`, `Product_SKU_Token` 적재 (Spring Batch 5)

## 0. 단계별 목표 🎯

| 단계           | 초점                       | 목표                                                                   |
| ------------ | ------------------------ | -------------------------------------------------------------------- |
| **1단계 (기본)** | **Batch 계층 지표**만, 도구 최소화 | Actuator + (선택) Spring Boot Admin 으로 read/write/skip·TPS 를 눈으로 확인한다. |
| **2단계 (확장)** | **Batch 지표 고도화**         | Prometheus + Grafana + Alertmanager 로 TPS·Skip% 그래프와 경보를 자동화한다.      |
| **3단계**      | **JVM & DB 레이어**         | Heap·GC·스레드·쿼리 레이턴시 등 전 스택으로 모니터링 범위를 확장한다.                          |

---

## 1. 해야 할 일 Todo ✅

### ■ 1단계 – 기본 Batch 지표

| #                          | 작업                                                              | 상태 |
| -------------------------- | --------------------------------------------------------------- | -- |
| **Batch 구현**               |                                                                 |    |
| 1                          | 스켈레톤 프로젝트(Spring Boot 3, Batch 5) 생성                            | ⬜  |
| 2                          | Excel → 3개 테이블 적재 Step 작성(Reader/Processor/Writer)              | ⬜  |
| 3                          | 1만 행 더미 Excel로 로컬 실행 성공 확인                                      | ⬜  |
| **Actuator 지표 확인**         |                                                                 |    |
| 4                          | `spring-boot-starter-actuator` 의존성 추가                           | ⬜  |
| 5                          | `management.endpoints.web.exposure.include=metrics,health,info` | ⬜  |
| 6                          | `/actuator/metrics/spring.batch.step.write.count` 값 확인          | ⬜  |
| **(선택) Spring Boot Admin** |                                                                 |    |
| 7                          | SBA 서버·클라이언트 추가, 대시보드에서 Job/Step 현황 확인                          | ⬜  |

### ■ 2단계 – Prometheus 스택(확장 Batch 모니터링)

| #  | 작업                                               | 상태 |
| -- | ------------------------------------------------ | -- |
| 8  | `micrometer-registry-prometheus` 의존성 추가          | ⬜  |
| 9  | Prometheus Docker 컨테이너 구성 & scrape 설정            | ⬜  |
| 10 | `MetricsStepListener` 구현 → `batch_tps`           | ⬜  |
| 11 | `ChunkListener` 구현 → `batch_skip_ratio`          | ⬜  |
| 12 | Grafana 설치, TPS / Skip% / Step Duration 패널 3개 생성 | ⬜  |
| 13 | Alertmanager 연동, 경보 룰(TPS<50, Skip%>0.5) 설정      | ⬜  |
| 14 | 1백만 행 Excel·에러 Row·DB 락 테스트 → 경보 검증              | ⬜  |

### ■ 3단계 – JVM · DB 모니터링 (추후)

| #  | 작업                                         | 상태 |
| -- | ------------------------------------------ | -- |
| 15 | JVM 기본 메트릭(Heap, GC, Thread) Prometheus 노출 | ⬜  |
| 16 | Grafana 패널 추가(Heap Used, GC Pause 99p)     | ⬜  |
| 17 | DB 커넥션 풀·쿼리 레이턴시 메트릭 수집(Hikari, p6spy)     | ⬜  |
| 18 | 종합 대시보드 & Alert Rule 작성                    | ⬜  |

> 각 단계 완료 시 ⬜ → ✅ 로 수동 체크

---

## 2. 진행 로그 🗒️

| 날짜         | 내용                                  |
| ---------- | ----------------------------------- |
| yyyy‑mm‑dd | (예) 프로젝트 뼈대 생성, Excel Reader 테스트 통과 |
| yyyy‑mm‑dd |                                     |

---


## 3. 참고 링크 🔗

* Spring Batch: [https://docs.spring.io/spring-batch/](https://docs.spring.io/spring-batch/)
* Spring Boot Admin: [https://github.com/codecentric/spring-boot-admin](https://github.com/codecentric/spring-boot-admin)
* Micrometer Prometheus: [https://micrometer.io/docs/registry/prometheus](https://micrometer.io/docs/registry/prometheus)
* Prometheus Alerting: [https://prometheus.io/docs/alerting/](https://prometheus.io/docs/alerting/)
* Grafana Dashboards: [https://grafana.com/docs/grafana/latest/dashboards/](https://grafana.com/docs/grafana/latest/dashboards/)

---

*측정하지 못하면 개선할 수 없다. — Keep shipping!* 🚀
