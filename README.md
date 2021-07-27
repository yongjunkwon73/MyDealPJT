이미지
![CAR_Image](https://user-images.githubusercontent.com/85722789/126855221-e47a5a34-2de9-4246-bedc-ea9a2a4c44dd.jpg)

# 중고차 매매

- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW


# Table of contents

- [중고차매매](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현](#구현)
    - [DDD 의 적용](#ddd-의-적용)
    - [폴리글랏 퍼시스턴스](#폴리글랏-퍼시스턴스)
    - [폴리글랏 프로그래밍](#폴리글랏-프로그래밍)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
  - [운영](#운영)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)
  - [신규 개발 조직의 추가](#신규-개발-조직의-추가)


# 서비스 시나리오
 
기능적 요구사항
1. 구매자는 구매하고자 하는 중고차를 구매요청한다. 
1. 구매요청한 차량의 금액을 결재 한다.
1. 결재후 매매 승인이 되면 차량을 탁송(배송)한다. 
1. 탁송되면  재고가 감소된다. 
1. 구매자가 구매 취소하면 탁송이 취소된다. 
1. 구매취소된 중고차 매매건은는 탁송을 취소한다 
1. 탁송이 취소되면 재고가 증가된다.
1. 구매자는 구매/탁송 상태를 대쉬보드에서 조회한다. 

비구현 시나리오
1. 판매자는 판매하고자 하는 차량의  정보를 입력하여 시세를 확인한다..
1. 판매자는 자신의 차량의 판매 등록한다
1. 구매 부서에서는 등록된 차량 정보를확인후 매입결정을 한다
1. 매입결정된 차량의 시세에따라 판매자에게 금입을  입금후  탁송(회수)요청이된다. 
1. 탁송이 완료되면 재고가 증가된다. 
1. 판매자가 판매 취소하면 틱송이 취소된다. 
1. 판매취소된 중고차 매매건은는 재고가 감소한다다.
1. 판매는 판매 상태를 대쉬보드에서 조회한다.

비기능적 요구사항
1. 트랜잭션
    1. 결제가 되지 않은 구매요청건은 아예 거래가 성립되지 않아야 한다 -> Sync 호출
1. 장애격리
    1. 탁송(배송)기능이 수행되지 않더라도 구매는 365일 24시간 가능해야 한다 -> Async (event-driven), Eventual Consistency
    1. 결제시스템이 과중되면 사용자를 잠시동안 받지 않고 결제를 잠시후에 하도록 유도한다 -> Circuit breaker, fallback
1. 성능
    1. 사용자가 자주 확인 확인할 수 있는 중고차 재고 상태나 구매/탁송상태를  Dashbosr(프론트엔드)에서 확인할 수 있어야 한다 -> CQRS
    1. 구매 결재나 탁송상태에 따라 카톡에 알림을 줄 수 있어야 한다 -> Event driven


# 체크포인트

- 분석 설계

  - 이벤트스토밍
    - 스티커 색상별 객체의 의미를 제대로 이해하여 헥사고날 아키텍처와의 연계 설계에 적절히 반영하고 있는가?
    - 각 도메인 이벤트가 의미있는 수준으로 정의되었는가?
    - 어그리게잇: Command와 Event 들을 ACID 트랜잭션 단위의 Aggregate 로 제대로 묶었는가?
    - 기능적 요구사항과 비기능적 요구사항을 누락 없이 반영하였는가?    
  - 서브 도메인, 바운디드 컨텍스트 분리
    - 팀별 KPI 와 관심사, 상이한 배포주기 등에 따른  Sub-domain 이나 Bounded Context 를 적절히 분리하였고 그 분리 기준의 합리성이 충분히 설명되는가?
      - 적어도 3개 이상 서비스 분리
    - 폴리글랏 설계: 각 마이크로 서비스들의 구현 목표와 기능 특성에 따른 각자의 기술 Stack 과 저장소 구조를 다양하게 채택하여 설계하였는가?
    - 서비스 시나리오 중 ACID 트랜잭션이 크리티컬한 Use 케이스에 대하여 무리하게 서비스가 과다하게 조밀히 분리되지 않았는가?
  - 컨텍스트 매핑 / 이벤트 드리븐 아키텍처 
    - 업무 중요성과  도메인간 서열을 구분할 수 있는가? (Core, Supporting, General Domain)
    - Request-Response 방식과 이벤트 드리븐 방식을 구분하여 설계할 수 있는가?
    - 장애격리: 서포팅 서비스를 제거 하여도 기존 서비스에 영향이 없도록 설계하였는가?
    - 신규 서비스를 추가 하였을때 기존 서비스의 데이터베이스에 영향이 없도록 설계(열려있는 아키택처)할 수 있는가?
    - 이벤트와 폴리시를 연결하기 위한 Correlation-key 연결을 제대로 설계하였는가?
  - 헥사고날 아키텍처
    - 설계 결과에 따른 헥사고날 아키텍처 다이어그램을 제대로 그렸는가?
    
- 구현

  - [DDD] 분석단계에서의 스티커별 색상과 헥사고날 아키텍처에 따라 구현체가 매핑되게 개발되었는가?
    - Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 데이터 접근 어댑터를 개발하였는가
    - [헥사고날 아키텍처] REST Inbound adaptor 이외에 gRPC 등의 Inbound Adaptor 를 추가함에 있어서 도메인 모델의 손상을 주지 않고 새로운 프로토콜에 기존 구현체를 적응시킬 수 있는가?
    - 분석단계에서의 유비쿼터스 랭귀지 (업무현장에서 쓰는 용어) 를 사용하여 소스코드가 서술되었는가?
  - Request-Response 방식의 서비스 중심 아키텍처 구현
    - 마이크로 서비스간 Request-Response 호출에 있어 대상 서비스를 어떠한 방식으로 찾아서 호출 하였는가? (Service Discovery, REST, FeignClient)
    - 서킷브레이커를 통하여  장애를 격리시킬 수 있는가?
  - 이벤트 드리븐 아키텍처의 구현
    - 카프카를 이용하여 PubSub 으로 하나 이상의 서비스가 연동되었는가?
    - Correlation-key:  각 이벤트 건 (메시지)가 어떠한 폴리시를 처리할때 어떤 건에 연결된 처리건인지를 구별하기 위한 Correlation-key 연결을 제대로 구현 하였는가?
    - Message Consumer 마이크로서비스가 장애상황에서 수신받지 못했던 기존 이벤트들을 다시 수신받아 처리하는가?
    - Scaling-out: Message Consumer 마이크로서비스의 Replica 를 추가했을때 중복없이 이벤트를 수신할 수 있는가
    - CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능한가?
  - 폴리글랏 플로그래밍
    - 각 마이크로 서비스들이 하나이상의 각자의 기술 Stack 으로 구성되었는가?
    - 각 마이크로 서비스들이 각자의 저장소 구조를 자율적으로 채택하고 각자의 저장소 유형 (RDB, NoSQL, File System 등)을 선택하여 구현하였는가?
  - API 게이트웨이
    - API GW를 통하여 마이크로 서비스들의 집입점을 통일할 수 있는가?
    - 게이트웨이와 인증서버(OAuth), JWT 토큰 인증을 통하여 마이크로서비스들을 보호할 수 있는가?

- 운영

  - SLA 준수
    - 셀프힐링: Liveness Probe 를 통하여 어떠한 서비스의 health 상태가 지속적으로 저하됨에 따라 어떠한 임계치에서 pod 가 재생되는 것을 증명할 수 있는가?
    - 서킷브레이커, 레이트리밋 등을 통한 장애격리와 성능효율을 높힐 수 있는가?
    - 오토스케일러 (HPA) 를 설정하여 확장적 운영이 가능한가?
    - 모니터링, 앨럿팅 
  - 무정지 운영 CI/CD (10)
    - Readiness Probe 의 설정과 Rolling update을 통하여 신규 버전이 완전히 서비스를 받을 수 있는 상태일때 신규버전의 서비스로 전환됨을 siege 등으로 증명 
    - Contract Test :  자동화된 경계 테스트를 통하여 구현 오류나 API 계약위반를 미리 차단 가능한가?


# 분석/설계


## AS-IS 조직 (Horizontally-Aligned)
  ![ASIS](https://user-images.githubusercontent.com/85722789/126855236-4bc35a6e-c5d8-49ae-be34-a13c5ca1f224.JPG)


## TO-BE 조직 (Vertically-Aligned)
   
![TOBE](https://user-images.githubusercontent.com/85722789/126855257-54e03475-bb22-4226-a21a-30a457c717b5.JPG)

## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  http://www.msaez.io/#/storming/qTPVkyZojONcrS0xJzeIbYjPXMl1/385eb70fafd285bf582522ab97f45e92


### 이벤트 도출
![이벤트도출](https://user-images.githubusercontent.com/85722789/126858287-d6e62fc9-7a5e-494e-86bc-0a848b0c0b58.JPG)

### 부적격 이벤트 탈락
![부적격 이벤트 도출](https://user-images.githubusercontent.com/85722789/126858934-59f5c243-f523-4769-94be-58f92497b1fa.JPG)

    - 과정중 도출된 잘못된 도메인 이벤트들을 걸러내는 작업을 수행함

### 액터, 커맨드 부착하여 읽기 좋게
![command Actor](https://user-images.githubusercontent.com/85722789/126858940-6c4c4f76-87be-444a-b9d1-ceda8549bfb0.JPG)

### 어그리게잇으로 묶기
 ![Aggregate도출](https://user-images.githubusercontent.com/85722789/126858941-59d39f1a-c08c-4e2b-baf7-7f3b3b9e7095.JPG)
    - 예약, 대여처리, 결제정보, 재고는 그와 연결된 command 와 event 들에 의하여 트랜잭션이 유지되어야 하는 단위로 그들 끼리 묶어줌

### 바운디드 컨텍스트로 묶기

![bounded context](https://user-images.githubusercontent.com/85722789/126858942-34772c74-ce5e-4449-b0b7-51debafc54ef.JPG)

    - 도메인 서열 분리 
        - Core Domain:  예약(front) : 없어서는 안될 핵심 서비스이며, 연견 Up-time SLA 수준을 99.999% 목표, 배포주기는 예약의 경우 1주일 1회 미만, 대여의 경우 1개월 1회 미만
        - Supporting Domain:   대여 : 경쟁력을 내기위한 서비스이며, SLA 수준은 연간 60% 이상 uptime 목표, 배포주기는 각 팀의 자율이나 표준 스프린트 주기가 1주일 이므로 1주일 1회 이상을 기준으로 함.
        - General Domain:   결제 : 결제서비스로 3rd Party 외부 서비스를 사용하는 것이 경쟁력이 높음 (핑크색으로 이후 전환할 예정)

### 폴리시 부착 (괄호는 수행주체, 폴리시 부착을 둘째단계에서 해놔도 상관 없음. 전체 연계가 초기에 드러남)

![policy 도출](https://user-images.githubusercontent.com/85722789/126858948-054d1d42-fe45-42b6-94c4-e19c4f24f7b4.JPG)

### 폴리시의 이동과 컨텍스트 매핑 (점선은 Pub/Sub, 실선은 Req/Resp)

![폴리쉬이동 켄텍스트 매핑](https://user-images.githubusercontent.com/85722789/126858954-45193cae-5569-4899-aa4a-ac75d5befc9e.JPG)

### 완성된 1차 모형
 
![1차완성본](https://user-images.githubusercontent.com/85722789/126858376-97c0d9d7-c631-439d-8f41-0532b53c3510.png)

![완성된 Model](https://user-images.githubusercontent.com/85722789/126863177-765ea206-3f7f-4f2d-8cac-2e1b7c3cbe65.JPG)
    - View Model 추가

### 1차 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증

![1차 완성본 검증](https://user-images.githubusercontent.com/85722789/126858381-49021905-ab83-4d4d-93a1-f09d5a7aba80.JPG)

    - 구매자는 구매하고자 하는 중고차를 구매요청한다 (ok)    
    - 구매 요청한 차량에 대해  결제한다. (ok)    
    - 결제  승인이 되면  차량을 탁송한다. (ok)    
    - 차량이 탁송되면 재고가 감소된다. (ok)
 

![1차 완성본 검증 2](https://user-images.githubusercontent.com/85722789/126859571-03b5c975-9b75-4821-932b-0e0f07e36e23.JPG)

    - 사용자가 구매 요청을 취소한다 (ok)
    - 구매 취소요청된 결재가 취소된다.(ok)    
    - 결재가 취소된 탁송이 취소된다(ok)
    - 탁송이 취소된 차량의 재고가 증가한다(ok)

 
### 비기능 요구사항에 대한 검증


![비기능 검증](https://user-images.githubusercontent.com/85722789/126859608-95787c48-0a8f-43c9-8c15-7322167b1cb6.JPG)


    - 마이크로 서비스를 넘나드는 시나리오에 대한 트랜잭션 처리
    - 고객 예약시 결제처리:  결제가 완료되지 않은 예약은 절대 대여를 할 수 없기 때문에, ACID 트랜잭션 적용. 예약완료시 결제처리에 대해서는 Request-Response 방식 처리
    - 결제 완료시 탁송 및 재고처리:  예약(front)에서 대여 마이크로서비스로 탁송요청이 전달되는 과정에 있어서 대여 마이크로 서비스가 별도의 배포주기를 가지기 때문에 Eventual Consistency 방식으로 트랜잭션 처리함.
    - 나머지 모든 inter-microservice 트랜잭션: 구매상태, 결재상태,탁송 상태 등 모든 이벤트에 대해 카톡을 처리하는 등, 데이터 일관성의 시점이 크리티컬하지 않은 모든 경우가 대부분이라 판단, Eventual Consistency 를 기본으로 채택함.

## 헥사고날 아키텍처 다이어그램 도출
    
 
![헥사고날](https://user-images.githubusercontent.com/85722789/126863188-1f9e91f4-6a18-42de-9f98-850441859008.JPG)


    - Chris Richardson, MSA Patterns 참고하여 Inbound adaptor와 Outbound adaptor를 구분함
    - 호출관계에서 PubSub 과 Req/Resp 를 구분함
    - 서브 도메인과 바운디드 컨텍스트의 분리:  각 팀의 KPI 별로 아래와 같이 관심 구현 스토리를 나눠가짐

# 구현

- 분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 808n 이다)
```
  cd Billing
  mvn spring-boot:run

  cd Consign
  mvn spring-boot:run 

  cd DashBoard
  mvn spring-boot:run  

  cd Purchase
  mvn spring-boot:run 

  cd Stock
  mvn spring-boot:run
   
  
  ```

## 게이트웨이 적용
```application.yml
server:
  port: 8088

---

spring:
  profiles: default
  cloud:
    gateway:
      routes:
        - id: Purchase
          uri: http://localhost:8081
          predicates:
            - Path=/purchases/** 
        - id: Billing
          uri: http://localhost:8082
          predicates:
            - Path=/billings/** 
        - id: Consign
          uri: http://localhost:8083
          predicates:
            - Path=/consigns/** 
        - id: Stock
          uri: http://localhost:8084
          predicates:
            - Path=/stocks/** 
        - id: DashBoard
          uri: http://localhost:8085
          predicates:
            - Path= /dashes/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true


---

spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: Purchase
          uri: http://Purchase:8080
          predicates:
            - Path=/purchases/** 
        - id: Billing
          uri: http://Billing:8080
          predicates:
            - Path=/billings/** 
        - id: Consign
          uri: http://Consign:8080
          predicates:
            - Path=/consigns/** 
        - id: Stock
          uri: http://Stock:8080
          predicates:
            - Path=/stocks/** 
        - id: DashBoard
          uri: http://DashBoard:8080
          predicates:
            - Path= /dashes/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true

server:
  port: 8080
  
- gateway Service yml 에 loadBalancer 적용
```yml
apiVersion: v1
kind: Service
metadata:
  name: gateway
  labels:
    app: gateway
spec:
  ports:
    - port: 8080
      targetPort: 8080
  selector:
    app: gateway
  type: LoadBalancer

```
- 적용 이미지
![게이트웨이](https://user-images.githubusercontent.com/30138356/125386847-edaddb80-e3d7-11eb-9738-5c8904b3a28e.PNG)

## DDD 의 적용

- 각 서비스내에 도출된 핵심 Aggregate Root 객체를 Entity 로 선언하였다: (예시는 Billing 마이크로 서비스).  이때 가능한 현업에서 사용하는 언어 (유비쿼터스 랭귀지)를 그대로 사용하였다. 
``` Billing.JAVA
  package mydealprj;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Billing_table")
public class Billing {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long payId;
    private Long puId;
    private Long salePrice;
    private String payStatus;
    private String payDate;
    private String payCancelDate;

    @PostPersist
    public void onPostPersist(){
         // 결재 완료 후 Kafka 전
        if(this.payStatus == "Y") {
          Payed payed = new Payed();
          BeanUtils.copyProperties(this, payed);
          payed.publishAfterCommit();
            
        }      

    }
    @PostUpdate
    public void onPostUpdate(){
         // 결재 취소 전송 
       if(this.payStatus == "M") {
        PayCancelled payCancelled = new PayCancelled();
        BeanUtils.copyProperties(this, payCancelled);
        payCancelled.publishAfterCommit(); 
       }
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }
    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }
    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
    public String getPayCancelDate() {
        return payCancelDate;
    }

    public void setPayCancelDate(String payCancelDate) {
        this.payCancelDate = payCancelDate;
    }




}

```
- Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 다양한 데이터소스 유형 (RDB or NoSQL) 에 대한 별도의 처리가 없도록 데이터 접근 어댑터를 자동 생성하기 위하여 Spring Data REST 의 RestRepository 를 적용하였다
```JAVA
package mydealprj;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="purchases", path="purchases")
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long>{

}


```
### 적용 후 REST API 의 테스트

  - 사용신청(order) 발생 시, req/res 방식으로 결제(payment) 서비스를 호출하고 결제 완료 후 발생하는 PayApproved Event 가 카프카로 송출된다. 
```
  # purchase  서비스의 자동차 구매  신청(주문) 
   
   보완 필요    
```  
  
```
  # 주문 후 결제 상태 확인 ( payStatus = PAID )
    
```
 


  - billApproved 를 수신한 렌트(rent) 서비스가 전달받은 OrderId 로 렌트승인(APPROVE) 상태인 데이터를 생성한다.
  ```
  # 주문 후 결제 상태 확인 ( rentStatus = APPROVE )
  
    보완 필요 
  ```
 

  - 이후 렌트승인 상태인 OrderId 에 대해 렌트신청 할 경우, 렌트(RENT) 상태로 변경되며 rent Event 가 카프카로 송출된다.
```
# 탁송  (  )
 
```
  보완 필요 

- 재고(stock) 서비스에서는 해당 rent Event 수신 후, 재고차감 이력을 기록한다. 
```
  # 렌트 후 Rent Event 수신한 Stock 서비스의 재고 차감 확인 ( 재고 차감/증가 이력만 남김 )
  ```
  보완 필요 
```
  # 재고 차감 내역 콘솔에서 확인
```
  

## Correlation-key
- 구매 취소  작업을 통해, Correlation-key 연결을 검증한다

```
#  구매 취소 
```
 
```
#  탁송 취소 
```
 
```
#  재고 증가
```
 
```
#
```
 

## 동기식 호출 과 Fallback 처리
- 분석단계에서의 조건 중 하나로 구매 (purchase)->결제(billing) 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로 처리하기로 하였다. 
호출 프로토콜은 이미 앞서 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient 를 이용하여 호출하도록 한다.

결제서비스를 호출하기 위하여 Stub과 (FeignClient) 를 이용하여 Service 대행 인터페이스 (Proxy) 를 구현 ( url 은 Config Map 적용 )
``` JAVA
# (purhase) billingService.java
 
//  원소스 
 /*
@FeignClient(name="Billing", url="http://Billing:8080")
public interface BillingService {
    @RequestMapping(method= RequestMethod.GET, path="/billings")
    public void pay(@RequestBody Billing billing);
}  */
  
-- 변경 소스  
@FeignClient(name="Billing", url="http://${api.url.Biiling}")
public interface BillingService {
    @RequestMapping(method= RequestMethod.GET, path="/billings")
    public void pay(@RequestBody Billing billing);
   }
   
```
-  구매 주문 후 (@PostPersist) 결제를 요청하도록 처리
``` JAVA
# purchase.java (Entity)
    보완 필요 
   
  // 
```
- 동기식 호출에서는 호출 시간에 따른 타임 커플링이 발생하며, 결제 시스템이 장애가 나면 주문도 못받는다는 것을 확인:
```
  # 결제(paymentSystem) 서비스를 잠시 내려놓음

  # 사용 신청 처리 (보완 필요)
  http POST localhost:8088/order customerId=11 time=3 orderId=20  # Fail
```
  
```
  # 결제서비스 재기동ㅍ
  cd payment
  mvn spring-boot:run

  # 사용 신청 처리(보완 필요)
  http POST localhost:8088/order customerId=11 time=3 orderId=20  #Success
```
 

과도한 요청시에 서비스 장애 벌어질 수 있음에 유의

## 비동기식 호출 / 시간적 디커플링 / 장애격리 / 최종 (Eventual) 일관성 테스트
결제가 이루어진 후에 렌트승인 시스템으로 이를 알려주는 행위는 동기식이 아니라 비동기식으로 처리하여 대여를 위하여 결제가 블로킹 되지 않도록 처리한다.

이를 위하여 결제시스템에 기록을 남긴 후에 곧바로 결제완료이 되었다는 도메인 이벤트를 카프카로 송출한다(Publish)
``` JAVA
  ...
    @PostPersist
    public void onPostPersist(){
         // 결재 완료 후 Kafka 전
        if(this.payStatus == "Y") {
          Payed payed = new Payed();
          BeanUtils.copyProperties(this, payed);
          payed.publishAfterCommit();
            
        }      

    }
    @PostUpdate
    public void onPostUpdate(){
         // 결재 취소 전송 
       if(this.payStatus == "M") {
        PayCancelled payCancelled = new PayCancelled();
        BeanUtils.copyProperties(this, payCancelled);
        payCancelled.publishAfterCommit(); 
       }

    }
```
렌트승인 서비스에서는 결제완료 이벤트에 대해서 이를 수신하여 자신의 정책을 처리하도록 PolicyHandler 를 구현한다:
``` JAVA

```
렌트승인 시스템은 사용신청/결제와 완전히 분리되어있으며, 이벤트 수신에 따라 처리되기 때문에, 렌트승인이 유지보수로 인해 잠시 내려간 상태라도 사용신청을 받는데 문제가 없다:
```
# 렌트승인 서비스 (lectureSystem) 를 잠시 내려놓음
# 사용신청 처리 후 사용신청 및 결제 처리 Event 진행확인
```

```
# 렌트승인 서비스 기동
cd rent
mvn spring-boot:run

# 렌트 상태 Update 확인
```



## CQRS

- CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능하도록 구현한다

 구매요청 / 결재 / 탁송 서비스의 전체 현황 및 상태 조회를 제공하기 위해 dashboard를 구성하였다.

dashboard의 어트리뷰트는 다음과 같으며

 
![CQRS구성](https://user-images.githubusercontent.com/85722789/127074479-bfe6e740-7a78-449a-a943-a38fcddb2fd2.JPG)


purchased, purchasecancelles, payed, paycanceled, Consigned, Consignedcancelled 이벤트에 따라 주문상태, 반납상태, 취소상태를 업데이트 하는 모델링을 진행하였다.

자동생성된 소스 샘플은 아래와 같다
dash.java
``` JAVA
 package mydealprj;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Dash_table")
public class Dash {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long dashId;
        private Long puId;
        private Long cuId;
        private String puDate;
        private String puCancelDate;
        private String puStatus;
        private Long payId;
        private String payDate;
        private String payCancelDate;
        private String payStatus;
        private Long conId;
        private String conDate;
        private String conCancelDate;
        private String conStatus;
        private Long stockId;
        private String stockType;
        private String stockDate;
        private Long stockTotal;


        public Long getDashId() {
            return dashId;
        }

        public void setDashId(Long dashId) {
            this.dashId = dashId;
        }
        public Long getPuId() {
            return puId;
        }

        public void setPuId(Long puId) {
            this.puId = puId;
        }
        public Long getCuId() {
            return cuId;
        }

        public void setCuId(Long cuId) {
            this.cuId = cuId;
        }
        public String getPuDate() {
            return puDate;
        }

        public void setPuDate(String puDate) {
            this.puDate = puDate;
        }
        public String getPuCancelDate() {
            return puCancelDate;
        }

        public void setPuCancelDate(String puCancelDate) {
            this.puCancelDate = puCancelDate;
        }
        public String getPuStatus() {
            return puStatus;
        }

        public void setPuStatus(String puStatus) {
            this.puStatus = puStatus;
        }
        public Long getPayId() {
            return payId;
        }

        public void setPayId(Long payId) {
            this.payId = payId;
        }
        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }
        public String getPayCancelDate() {
            return payCancelDate;
        }

        public void setPayCancelDate(String payCancelDate) {
            this.payCancelDate = payCancelDate;
        }
        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }
        public Long getConId() {
            return conId;
        }

        public void setConId(Long conId) {
            this.conId = conId;
        }
        public String getConDate() {
            return conDate;
        }

        public void setConDate(String conDate) {
            this.conDate = conDate;
        }
        public String getConCancelDate() {
            return conCancelDate;
        }

        public void setConCancelDate(String conCancelDate) {
            this.conCancelDate = conCancelDate;
        }
        public String getConStatus() {
            return conStatus;
        }

        public void setConStatus(String conStatus) {
            this.conStatus = conStatus;
        }
        public Long getStockId() {
            return stockId;
        }

        public void setStockId(Long stockId) {
            this.stockId = stockId;
        }
        public String getStockType() {
            return stockType;
        }

        public void setStockType(String stockType) {
            this.stockType = stockType;
        }
        public String getStockDate() {
            return stockDate;
        }

        public void setStockDate(String stockDate) {
            this.stockDate = stockDate;
        }
        public Long getStockTotal() {
            return stockTotal;
        }

        public void setStockTotal(Long stockTotal) {
            this.stockTotal = stockTotal;
        }

}

```
DashboardRepository.java
```JAVA
 package mydealprj;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashRepository extends CrudRepository<Dash, Long> {

    List<Dash> findByPayId(Long payId);
   // List<Dash> findByPayId(Long payId);
    List<Dash> findByConId(Long conId);
   // List<Dash> findByConId(Long conId);
    List<Dash> findByStockId(Long stockId);

}
```
DashboardViewHandler.java
```JAVA
 package mydealprj;

import mydealprj.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DashViewHandler {


    @Autowired
    private DashRepository dashRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPuchased_then_CREATE_1 (@Payload Puchased puchased) {
        try {

            if (!puchased.validate()) return;

            // view 객체 생성
            Dash dash = new Dash();
            // view 객체에 이벤트의 Value 를 set 함
            dash.setPuId(puchased.getId());
            dash.setPuDate(puchased.getPuDate());a
            dash.setPuStatus(puchased.getPuStatus());
            // view 레파지 토리에 save
            dashRepository.save(dash);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_1(@Payload Payed payed) {
        try {
            if (!payed.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByPayId(payed.getPayId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setPayStatus(payed.getPayStatus());
                    dash.setPayDate(payed.getPayDate());
                    dash.setPuId(payed.getPuId());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayCancelled_then_UPDATE_2(@Payload PayCancelled payCancelled) {
        try {
            if (!payCancelled.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByPayId(payCancelled.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setPayStatus(payCancelled.getPayStatus());
                    dash.setPuCancelDate(payCancelled.getPayCalncelDate());
                    dash.setPuId(payCancelled.getPuId());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenConsigned_then_UPDATE_3(@Payload Consigned consigned) {
        try {
            if (!consigned.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByConId(consigned.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setConDate(consigned.getConDate());
                    dash.setPuId(consigned.getPuId());
                    dash.setConStatus(consigned.getConStatus());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenConsignCancelled_then_UPDATE_4(@Payload ConsignCancelled consignCancelled) {
        try {
            if (!consignCancelled.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByConId(consignCancelled.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setPuId(consignCancelled.getPuId());
                    dash.setConCancelDate(consignCancelled.getConCancelDate());
                    dash.setConStatus(consignCancelled.getConStatus());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenStockChanged_then_UPDATE_5(@Payload StockChanged stockChanged) {
        try {
            if (!stockChanged.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByStockId(stockChanged.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setStockDate(stockChanged.getStockDate());
                    dash.setStockTotal(stockChanged.getStockTotal());
                    dash.setPuId(stockChanged.getPuId());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


 
```
CQRS에 대한 테스트는 아래와 같다
주문생성 시 주문 및 결제까지 정상적으로 수행 및 등록이 되며
(증적자료 보완 필요)


dashbaord CQRS 결과는 아래와 같다
(증적자료 보완 필요)


![image](https://user-images.githubusercontent.com/22028798/125186621-5d03be00-e266-11eb-85a6-58cede9ce417.png) 

## 폴리글랏 퍼시스턴스 (미구현)
- CQRS 를 위한 Dashboard 서비스만 DB를 구분하여 적용함. 인메모리 DB인 hsqldb 사용.
```xml
		<!-- <dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency> -->

		<dependency>
		    <groupId>org.hsqldb</groupId>
		    <artifactId>hsqldb</artifactId>
		    <version>2.4.0</version>
		    <scope>runtime</scope>
		</dependency>
```
- 변경 후에도 정상 구동됨을 확인
 
# 운영

## Deploy / Pipeline
각 구현체 들의 pipeline build script 는 shared-mobility/kubernetes/sharedmobility 내 
포함되어 있다. ( ex. order.yml )

- Build 및 ECR 에 Build/Push 하기
```
 
# purchase
cd 
mvn package
docker build -t 879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-purchase:latest .
docker push   879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-purchase:latest

# Billing
cd ..
cd billing
mvn package
docker build -t 879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-billing:latest .
docker push   879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-billing:latest 

# Consign
cd ..
cd Rent
mvn package
docker build -t 879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-consign:latest .
docker push   

# stock
cd ..
cd Stock
mvn package
docker build -t 879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-stock:latest .
docker push   879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-stock:latest

# dashboard
cd ..
cd Dashboard
mvn package
docker build -t 879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-dashboard:latest . 
docker push   879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-dashboard:latest

# gateway
cd ..
cd gateway
mvn package
docker build -t 879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-gateway:latest .
docker push   879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-gateway:latest
```

## 리포지토리 구성

![레파지토리생성](https://user-images.githubusercontent.com/85722789/127076827-cd73a502-3448-44a4-a023-eaf8b28e2e10.jpg)
push 결과 
![리포지토리 구성](https://user-images.githubusercontent.com/85722789/127076891-689d9a2c-c6d2-4eaf-832b-b9121d4253ec.jpg)


## Kubernetes Deploy 및 Service 생성
```
cd ..
 
kubectl apply  -f kubernetes/mydealpjt/purchase.yml
kubectl apply  -f kubernetes/mydealpjt/billing.yml
kubectl apply  -f kubernetes/mydealpjt/consign.yml
kubectl apply  -f kubernetes/mydealpjt/stock.yml
kubectl apply  -f kubernetes/mydealpjt/dashboard.yml
kubectl apply  -f kubernetes/mydealpjt/gateway.yml

```


- kubernetes/mydealprj/purchase.yml 파일
```YML
---
  ---

apiVersion: apps/v1
kind: Deployment
metadata:
  name: purchase
  labels:
    app: purchase
spec:
  replicas: 1
  selector:
    matchLabels:
      app: purchase
  template:
    metadata:
      labels:
        app: purchase
    spec:
      containers:
        - name: purchase
          image: 879772956301.dkr.ecr.ap-northeast-2.amazonaws.com/user04-purchase:latest
          ports:
            - containerPort: 8080
          readinessProbe:
            httpGet:
              path: '/actuator/health'
              port: 8080
            initialDelaySeconds: 10
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 10
          livenessProbe:
            httpGet:
              path: '/actuator/health'
              port: 8080
            initialDelaySeconds: 120
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 5


---


apiVersion: v1
kind: Service
metadata:
  name: purchase
  labels:
    app: purchase
spec:
  ports:
    - port: 8080
      targetPort: 8080
  selector:
    app: purchase
 
---


apiVersion: v1
kind: Service
metadata:
  name: order
  labels:
    app: order
spec:
  ports:
    - port: 8080
      targetPort: 8080
  selector:
    app: order

```

- Deploy 완료 ( POD /Service 생성)

![POD생성](https://user-images.githubusercontent.com/85722789/127077585-9b059e3b-5c0f-4bd0-90c7-179c4228fe58.jpg)

![Service 생성](https://user-images.githubusercontent.com/85722789/127077593-2055b333-da5c-4ce5-b4fc-1588c7f90237.jpg)

## Helm 설치

 - Helm 설치 하기 
 ```
 curl https://raw.githubusercontent.com/helm/helm/master/scripts/get > get_helm.sh				# Download Helm Script
chmod 700 get_helm.sh												# 실행권한 부여
./get_helm.sh
 ```

![helm 설치](https://user-images.githubusercontent.com/85722789/127078381-a43391b4-c817-4490-9ef2-c4696354d3e4.jpg)

- Helm 서버인 Tiller에 대한 클러스터 관리자 역할을 가진 계정 생성
```
kubectl --namespace kube-system create sa tiller								# tiller service 계정 생성
kubectl create clusterrolebinding tiller --clusterrole cluster-admin --serviceaccount=kube-system:tiller	# 클러스터 관리자 역할 부여
```

![helm 클러스터 관리자](https://user-images.githubusercontent.com/85722789/127078485-7de70975-79d5-4c7b-a365-7ae224740ef4.jpg)

- Helm incubator repository 추가

![helm 클러스 레파지토리](https://user-images.githubusercontent.com/85722789/127079158-797476d7-ebf6-4c7c-8938-3693c07be242.jpg)

## chart 설치
```
helm repo update						# 최신 chart 리스트 업데이트
kubectl create ns kafka						# kafka namespace 생성
helm install my-kafka --namespace kafka incubator/kafka 	# my-kafka에 incubator 설치
kubectl get all -n kafka					# kafka 설치 확인
```
[chart 설치](https://user-images.githubusercontent.com/85722789/127079139-170bea80-3eab-4ba4-9a51-6c9664ca76a5.jpg)

## 동기식 호출 / 서킷 브레이킹 / 장애격리
 - istio 설치
```
cd /home/project
curl -L https://istio.io/downloadIstio | ISTIO_VERSION=1.7.1 TARGET_ARCH=x86_64 sh -        # istio 설치파일 download
cd istio-1.7.1
export PATH=$PWD/bin:$PATH                                                                  # istio PATH 추가
istioctl install --set profile=demo --set hub=gcr.io/istio-release                          # istio 설치
kubectl label namespace default istio-injection=enabled                                     # istio를 kubectl에 injection
kubectl get all -n istio-system                                                             # istio injection 상태 확인
```
 - istio 설치파일 다운로드
 ![isto설치3](https://user-images.githubusercontent.com/85722789/127080331-0e36837a-88c6-4f1d-867a-1f4581d15ed1.jpg)
 - stio 설치 확인(istio namespace service 구성 및  확인)
 ![isto설치3](https://user-images.githubusercontent.com/85722789/127080331-0e36837a-88c6-4f1d-867a-1f4581d15ed1.jpg)

![isto  name service 확인](https://user-images.githubusercontent.com/85722789/127080451-069b42c0-a07b-4369-9e80-d51b29cd6c60.jpg)

 - 마이크로서비스 재배포 후(Pod의 READY가 1/1에서 2/2로 변경됨, gateway)  
 ![isto 설치 후 pod 구성](https://user-images.githubusercontent.com/85722789/127080460-9b0e71f6-e6d8-41b9-b1d0-51bbd51bdd36.jpg)
 

 - kiali 설치

  ![kiali설치](https://user-images.githubusercontent.com/85722789/127080653-76284201-0c22-469e-885a-a60a462ca1ee.jpg)

 - kiali.yaml 수정
```
vi istio-1.7.1/istiosamples/addons/kiali.yaml
	4라인의 apiVersion: 
		apiextensions.k8s.io/v1beta1을 apiVersion: apiextensions.k8s.io/v1으로 수정

```
-  kiali 서비스 설정
```
kubectl apply -f samples/addons
	kiali.yaml 오류발생시, 아래 명령어 실행
		kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.7/samples/addons/kiali.yaml
```

-  kiali External IP 설정 및 IP 확인

```
kubectl edit svc kiali -n istio-system                                   # kiali External IP 설정
	:%s/ClusterIP/LoadBalancer/g
	:wq!
kubectl get all -n istio-system                                          # EXTERNAL-IP 확인
```
![kiali External IP 확인](https://user-images.githubusercontent.com/85722789/127080831-98a0a6d0-e812-41de-8af3-4f8679f5cb38.jpg)


- kiali 접속 확인 
![kiali 접속](https://user-images.githubusercontent.com/85722789/127081026-f24dff7d-fc1b-48ad-8d9d-b2e192fac998.jpg)
![kiali 접속2](https://user-images.githubusercontent.com/85722789/127081038-2b9d455a-0b7d-4398-8952-7c900bd21a6e.jpg)

## 서킷브레이커 설정



서킷브레이커 설정(DestinationRule)

Seige 툴을 통한 서킷 브레이커 동작 확인 (미수행)




## Self-healing (Liveness Probe)
-  bliing.yml에 정상 적용되어 있는 livenessProbe

```
 Spec:
      containers:
        - name: billing
          image: 879772956301.악.ecr.ap-northeast-2.amazonaws.com/user04-billing:latest
          args:
          - /bin/sh
          - -c
          - touch /tmp/healthy; sleep 10; rm -rf /tmp/healthy; sleep 600;
          ports:
            - containerPort: 8080
          readinessProbe:
            httpGet:
              path: ＇/actuator/health＇
              port: 8080
            initialDelaySeconds: 10
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 10
          livenessProbe:
            httpGet:
              path: ＇/actuator/health＇
              port: 8080
            initialDelaySeconds: 120
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 5
```

 

- 정상작동 중 확인 (적용전)

 ![liveness적용전](https://user-images.githubusercontent.com/85722789/127086868-ac9334d6-7ad6-41d8-b8b6-8ac4db22ca5b.jpg)

- 적용 후  (Crashloog back off 발생 및 Retry)

![liveness적용후](https://user-images.githubusercontent.com/85722789/127086877-86f2be5b-19a0-495d-bfad-e14e42f2e6db.png)

 

## Config Map 

- 변경 가능성이 있는 설정을 ConfigMap을 사용하여 관리 
 -- purchase 서비스에서 바라보는 billing 서비스 url 일부분을 configMap 사용하여 구현
 
 
 - purchase 서비스내 FeignClient (purchase/src/main/java/mydealprj/external/billingService.java)
   ![configmap 설정 0](https://user-images.githubusercontent.com/85722789/127089597-c63a9f48-c65a-4404-844e-cb9c67165ad2.JPG)
 - purchase 서비스 application.yml
 ![configmap 설정 1](https://user-images.githubusercontent.com/85722789/127089614-9eee0148-f49a-412e-9d32-a16f0bc28550.JPG)

 - purchase 서비스 purchase.yml
  ![configmap 설정 2](https://user-images.githubusercontent.com/85722789/127089632-156fc3f0-079f-4686-b8e1-c025d085f6d4.JPG)
 - 적용 상태 

![configmap 적용상태](https://user-images.githubusercontent.com/85722789/127089661-66e6f3cc-27a5-48fa-919d-b6ab194132f9.JPG)

## Circuit Breaker 
--서킷 브레이킹 프레임워크 선택 : Hystrix 옵션을 사용하여 구현함 시나리오는 사용신청(order)-->결제(payment) 시 RESTful Request/Response 로 구현되어 있고 결제 요청이 과도할 경우 CB 를 통하여 장애격리.

-- Hystrix 를 설정: 요청처리 쓰레드에서 처리시간이 610 밀리가 넘어서기 시작하여 어느정도 유지되면 CB 회로가 닫히도록 (요청을 빠르게 실패처리, 차단) 설정



## 오토스케일 아웃 (미구현)
- 결제 서비스에 대한 Replica를 동적으로 늘려주도록 HPA 를 설정한다. 설정은 CPU 사용량이 15%를 넘어서면 Replica 를 10개까지 늘려준다.
