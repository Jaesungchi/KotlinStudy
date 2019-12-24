## Kotlin Study:green_book:

![imge](https://img.shields.io/badge/ProjectType-SingleStudy-green)  ![imge](https://img.shields.io/badge/Language-Kotlin-yellow)  ![imge](https://img.shields.io/badge/Tools-AndroidStudio-blue)

안드로이드 개발자로서 더욱 더 성장하기 위해 Kotlin 언어 공부를 시작합니다.

2019 - 12 - 21 ~

1. 계산기 2019-12-21 ~ 2019-12-24

2. 화면전환기 2019-12-24 ~

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

#### String template

- 자바에서는 로그를 찍거나 텍스트를 만들때 a + b + c 방식으로 만든다.

  ```java
  priavet void logUserInfo(@NonNull User user){
  	String name = user.getName();
  	String age = user.getAge();
  	Log.d("UserIndo","UserName : " + name + " ,UserAge : " + age);
  }
  ```

- 코틀린은 개선된 방식으로 코드를 작성한다.

  ```kotlin
  private fun logUserInfo(user: User){
  	val name = user.name
  	val age = user.age
  	Log.d("UserInfo","UserName : $name, UserAge : $age")
  }
  ```

  $표시로 바로 변수를 넣어서 사용할 수 있다.

#### Overload

자바는 오버로딩을 지원해서 생성자나 함수를 여러개 만들어야 하는 경우가 있다. 하지만 kotlin이라면 얘기가 다르다.

```kotlin
class UserCustomView @JvmOverloads constructor(
	contex: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
	defStyleRes: Int = 0
);
...
```

= null, = 0 과 같이 만약 해당 값이 없으면 default로 이렇나 값이 지정되도록하면 알아서 오버로딩 생성자가 만들어진다...
(아직 사전지식임에도 정말 놀랍다...:rofl:)

#### Lambda

버튼 클릭 리스너를 만들때 자바에서는 다음과 같은 코딩을 하게 된다.

```java
button.setOnClickListener(new View.OnClickListener() { 
	@Override public void onClick(View view) { 
		// 동작
	} 
}); //길다면 이렇게
button.setOnClickListener(view ->{
	//동작
}); //짧으면 이렇게
```

이렇게 쓰지않는 view를 연결해주고 자시고 한다. 코틀린은 짧다.

```kotlin
button.setOnClickListener{
 //동작
}
```

(하.. java하면서 저게 너무 불편했는데 정말 편리한 기능이다.)

#### let

만약 어떠한 정보를 가져와서 null인지 아닌지 체크하고 그 안에 정보를 사용한다면 자바는 다음과 같다.

```java
private void getUserName(@Nullable User user){
	if(user != null){
		String name = user.getName();
        //동작
	}
}
```

이때 코틀린은 let을 사용한다. ? 로 null을 체크하고 let을 함께 붙여주면 null이 아닐때만 코드가 동작한다.

```kotlin
private fun getUserName(user: User?){
	user?.let{ user->
		val name = user.name
		//동작
	}
    //or
    user?.let{
        val name = it.name
    }
}
```

위에서 or 이후 코드처럼 lambda 인자도 없앨 수 있고 들어 오는 값은 it으로 사용할 수 있다.

#### let + run

만약 어떠한 정보를 가져오는데 문제가 발생해서 log를 남겨두고 싶다면 자바는 이렇게 코딩할 것이다.

```java
private void setUser(@Nullable User user) { 
	if (user != null && user.getName() != null) {
    	String name = user.getName(); 
    	// 동작
    } else { 
    	// 로그
    } 
}
```

코틀린은 let과 elvis 오퍼레이터와 run을 이용해 다음과 같이 만들 수 있다.

```kotlin
private fun setUser(user: User?){
	user?.name?.let{ //user를 확인하고 체인 해서 name을 확인한다.
		//동작
	} ?: run{
		//로그
	}
}
```

하지만 위 방식은 let에서 결과가 null이면 run으로 빠지게 되는데 보통은 let에서 그런경우는 없다고 한다.

#### apply

이번에는 새로 VO 객체를 만들고 설정하는 방법이다. 자바는 이렇다.

```java
User user = new User(); //여기서 생성자로 넣을 수 도 있음
user.setName("홍길동");
user.setAge("25");
user.setAddress("서울시 양천구");
```

위와 같은 기능의 코틀린 코드는 다음과 같다.

```kotlin
val user = User().apply{
	name = "홍길동"
	age = "25"
	address = "서울시 양천구"
}
```

보통은 이렇게 객체를 생성할때 쓰기도 하지만 안드로이드에서 Activity 실행을 위해 intent를 만들고 여러 값을 Extra로 넘기는 함수를 사용할때도 사용 가능하다.

```kotlin
private fun getUserActivityIntent() =
	Intent(this. UserActivity::class.java).apply{
		putExtra(EXTRA_A,a);
		putExtra(EXTRA_B,b);
		putExtra(EXTRA_C,c);
	}
```

#### run

아까 나왔던 run은 다음처럼 사용할 수 있다.

```kotlin
private fun initRecyclerView() { 
	binding.recyclerView.run { 
		adapter = recyclerViewAdapter
		layoutManager = GridLayoutManager(context, SPAN_COUNT)
		addItemDecoration(DividerItemDecoration(context,LinearLayout.VERTICAL)) 
		isNestedScrollingEnabled = false 
	} 
}
```

여기서 만약 run을 사용하지 않았다면 binding.recyclerview.xxxx를 계속 쳐줘야 했다.

### apply / also / run / let / with 의 차이

![img](https://k.kakaocdn.net/dn/nf1nj/btqviF0WrxB/Eq605A7Z8UJxIe3zqMeE5K/img.png)

![img](https://k.kakaocdn.net/dn/oquKm/btqvlSxYb1Q/0cioJSk1YnP1lXd7DVZPK0/img.png)

scope에 this가 들어오는지 vs it 이 들어오는지, 결과로 자신이 나가는지 , 마지막 줄이 return 되는지 개념이 다르다.

---

## Kotlin의 기본 문법

### 변수 선언

- 변경 가능한 변수는 var 를 변경이 안되는 변수는 val를 사용해서 선언한다. 또한 따로 자료형을 선언하지 않아도 된다.

  ```kotlin
  val ABC = 30 //변경이 안되는 변수 final 자료형을 선언하지 않아도 Int로 받음
  var BCD: Int = 30 //변경이 되는 변수 null값을 받지 않음
  var CDE: Int? = 30 //변경이 되는 변수 null값을 받음
  ```

### 함수 선언

- 함수는 fun을 붙여서 선언한다.

  ```kotlin
  fun function(a: Int, b:String?): Int{
  	//fun 함수명(변수명: 자료형): 반환자료형
      //위는 a는 NonNull 이고 b 는 Nullable이다.
  }
  ```

### 자료형, 문자열

- 코틀린에서의 문자열은 "" 를 이용하고 문자는 ''를 이용한다. python 처럼 여러줄 로 문장을 표현할 때는 """로 표현한다.

  ```kotlin
  var data = "최재성" //data : String
  var dot = '점' //dot :Char
  var sentence = 
  """
  긴문장 표현방법
  이렇게 하시면 됩니다.
  """
  ```

- 문자열 간 비교는 자바와 달리 == 를 사용하면 된다.

  ```Kotlin
  if(data == "데이터")
  ```

- 문자 표현방식은 $기호를 붙여서 사용하면 된다. 자바보다 빠르다

  ```kotlin
  var data = "Hello"
  println("$data World!")
  ```

- 배열 선언은 Array를 이용한다. Array를 이용해 자료형을 맞추고 arrayOf()를 이용해 초기화한다.

  ```kotlin
  var numArr: Array<Int> = arrayOf(0,1,2,3,4,5) //자료형 생략 가능
  ```

### 제어문( For, while, when)

- for는 배열을 순회하거나 범위 지정을 할 수 있다.

  ```kotlin
  val numArr = arrayOf(1,2,3,4,5)
  for(num in numArr){} //배열 순회
  for(i in 1..3){} //1~3까지 순회
  for(i in 0..10 step 2){} //0~10 까지 2씩 증가
  for(i int 10 DownTo 0 step 2){} //10~0 까지 2씩 감소
  ```

- while은 java와 크게 다르지 않다.

  ```kotlin
  var i = 0
  while(i < 10){
  	i--
  }
  ```

- when은 switch를 대신한다.

  ```kotlin
  when(x){
  	1 -> println("1") //한개
  	2, 3 -> println("2 or 3") //여러 값
  	in 4..7 -> println("4 ~ 7") //범위
  	!in 8..10 -> printlnf("not 8 ~ 10") // not도 가능하다.
  	else ->{ //아닌경우
  		println("else")
  	}
  }
  ```

---

### 출처

[kotlin을 왜 쓸까?](https://velog.io/@hyejineeee/-%EC%BD%94%ED%8B%80%EB%A6%B0-kotlin-%EC%9D%80-%EB%AC%B4%EC%97%87)

[kotlin_사전지식](https://gun0912.tistory.com/81)