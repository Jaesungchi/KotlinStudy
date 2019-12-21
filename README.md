## Kotlin Study:green_book:

![imge](https://img.shields.io/badge/ProjectType-SingleStudy-green)![imge](https://img.shields.io/badge/Language-Kotlin-yellow)![imge](https://img.shields.io/badge/Tools-AndroidStudio-blue)

안드로이드 개발자로서 더욱 더 성장하기 위해 Kotlin 언어 공부를 시작합니다.

2019 - 12 - 21 ~

## 왜? 코틀린?

안드로이드 개발사인 JetBrain에서 공개한 언어로 2011년 자바를 대체하기 위해서 나왔다
2017년에는 구글 I/O에서 공식언어로 지정되었다.

### 코틀린의 특징

- **정적 타입 지정 언어** : 자바와 같은 정적타입지정언어지만 차이점은 변수 선언 시 변수의 타입을 지정해주지 않아도 된다. 코틀린 컴파일러가 문맥으로 타입을 자동으로 만들어주기 때문이다.
- **객체지향언어 + 함수형 프로그래밍** : 자바와 같이 객체지향언어이지만 함수형 프로그래밍의 장점을 채택한다. 함수형 프로그래밍을 채택함으로써 간결성, 다중 스레드에서 안전성,  테스트의 독립성을 갖는다.

### 코틀린의 장점:+1:

- **간결성**:zap: : 자바에 비해 확실히 간결한 코드를 갖는다. 간결한 코드는 파악이 쉬워 유지보수하는데 도움을 준다.
  자바 예시 코드 :

  ```java
  private String getSelectedDealerName(@Nullable Car car) { 
  if (car == null) { return null; } 
  Auction auction = car.getAuction(); 
  if (auction == null) { return null; } 
  Bid selectedBid = auction.getSelectedBid(); 
  if (selectedBid == null) { return null; } 
  Dealer dealer = selectedBid.getDealer(); 
  if (dealer == null) { return null; } return dealer.getName(); 
  }
  ```

  동일한 기능의 코틀린 예시 코드 :

  ~~~kotlin
  private fun getSelectedDealerName(car: Car?): String? { 
  return car?.auction?.selectedBid?.dealer?.name 
  }
  ~~~

  위와 같이 자바보다 확실히 짧은 코드만으로 같은 의미를 표현한다.

- **안전성**:shield: : 코틀린은 일부 유형의 오류를 설계 단계에서 방지한다. 따라서 적은 비용으로 높은 안전성을 추구할 수 있다. 자바와의 차이점은 코틀린은 타입 자동 추론을 지원한다.  또한, 코틀린은 컴파일 시점에서 몇 가지 에러 유형에 대해 검사를 진행한다.

  - NullPointerException : null값이 될 수 있는지에 대한 여부 표시

    ```kotlin
    val number:Int? = 10 //null값 허용
    val number:Int = 10 //null값 비허용
    ```

    위와 같은 코드는 자바에서는 이렇게 표현한다.

    ```java
    @Nullable
    int number = 10; // null값 허용
    @NonNull
    int number = 10; // null값 비허용
    ```

    벌써부터 큰 차이가 느껴진다.

  - ClassCaseException : 타입 검사와 캐스트를 한 연산자를 사용해 구현 가능

    ```kotlin
    if(value is String) print (value.toUpperCase())
    ```

-  **상호운용** : 코틀린은 자바를 대체하는 것을 목적으로 만들었기 때문에 현재 자바 코드와 100% 상호 운용이 가능하다. 라이브러리 또한 그대로 사용할 수 있다. 따로 자바 API 호출 시에도 변환 작업이 필요가 없다.

---

## 코틀린의 사전지식 (자바와의 차이점) :sunglasses:

#### Nullable과 NonNull 변수 선언

- 자바에서의 코드

  ```java
  @Nullable
  int number = 10; // null값 허용
  @NonNull
  int id = 10; // null값 비허용
  ```

- 코틀린 코드

  ```kotlin
  var number: int? = null //null값 허용
  var id: int = 10 //null값 비허용
  ```

#### val와 var의 차이

```kotlin
var name: String = "최재성" //var는 수정 가능
val id: String = "Jaesungchi" //val는 수정 불가능
val birthYear = 1995 //코틀린 기능으로 변수 타입을 선언하지 않아도 됨.
```

#### Safe call

자바언어로는 서버에서 정보를 가져와 선택한 아이디의 이름을 화면에 띄우기 위해서는 if() null을 항상 체크해주어야 합니다.

```java
private String getSelectedUserName(@Nullable Auction auction){
    if(auction == null) return null;
	ID userId = auction.getUserId();
    if(userId == null) return null;
    String name = user.getUserName();
    if(name == null) return null;
    return user.getUserName();
}
```

하지만 코틀린에서는 한줄이면 끝납니다! :open_mouth:

```kotlin
private fun getSelectedUserName(auction: Auction?): String?{
	return auction?.userID?.name?.name
}
```

이렇게 물음표를 이용해 체이닝해서 다음 변수를 참고할 수 있다.

#### Class

자바에서 VO 클래스를 만든다면 아래와 같이 만들 것이다.

```java
public class Auction{
	@NonNull
	private int auctionNumber;
	@NonNull
	private int auctionAmount;
	@Nullable
	private User customer;
	
	public Auction(@NonNull int auctionNumber,@NonNull int auctionAmount){...}
	
	setter ~~
	
	getter ~~
}
```

위와같이 굉장히 많은 코드를 적어야 한다. (쓰다가 지친다...) :sleeping:

코틀린으로 만들경우 아래의 코드면 끝난다. :clap:

```kotlin
data class Auction(var acutionNumber:int,var auctionAmount:int,var customer: User?)
```

var로 선언해서 setter/getter가 만들어지고, 물음표를 통해 Nullable 한 필드임을 알려준다.
생성자 또한 만들어 준다.

추가로 data Class는 toString(), hashCode(), equlas(), copy()까지 만들어준다... :scream:



---

### 출처

[kotlin을 왜 쓸까?][https://velog.io/@hyejineeee/-%EC%BD%94%ED%8B%80%EB%A6%B0-kotlin-%EC%9D%80-%EB%AC%B4%EC%97%87]

[kotlin_사전지식](https://gun0912.tistory.com/81)