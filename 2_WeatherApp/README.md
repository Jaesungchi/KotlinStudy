## Kotlin Study for [Weather App](https://github.com/Jaesungchi/WeatherApp):green_book:

![imge](https://img.shields.io/badge/ProjectType-SingleStudy-green)  ![imge](https://img.shields.io/badge/Language-Kotlin-yellow)  ![imge](https://img.shields.io/badge/Tools-AndroidStudio-blue)

날씨 앱을 만들기 위한 코틀린 공부 정리

날씨 앱을 만들기 위해 필요한 레이아웃, 기능.

1. RecyclerView : 도시 별 날씨를 보기 위해
2. Adapter : 아이템을 RecyclerView에 추가하기 위해 
3. 액티비티 전환
4. 날씨 API 사용

---

## 1. RecyclerView

### ListView vs RecyclerView

ListView에서는 모든 데이터에 대한 View를 만들고 View가 사라졌다가 나타날때마다 리소스를 부른다. 이건 많은 메모리를 사용하므로 앱이 느려지거나 충돌이 날 수 있다.

RecyclerView는 ListView의 단점을 보완하기 위해 만들어졌다. ViewHolder를 필수로 사용하는 등, 조금 복잡 할 수 있지만, 앱에서 불필요하게 메모리를 사용하는 일이 줄어든다.



Recycler view를 만들기 전 준비해야 할 것을 정리하면 이렇다

1. Gradle에 Implement 추가
2. 데이터 클래스의 정의
3. 레이아웃에 RecyclerView 추가
4. item 생성
5. Adapter 생성
6. Adapter 설정

### (1) Gradle에 Implement 추가

App 단의 build.gradle에 다음 내용을 추가한다.

```kotlin
implementation 'com.android.support:recyclerview-v7:26.1.0'
```

### (2) 데이터 클래스 정의

도시별 날씨의 목록을 만들기 때문에, City 클래스 파일을 만든다 항목은 다음과 같다.

```kotlin
class City (val name: String, val time: String, val weather: String)
```



---

## 출처

[ListView](https://blog.yena.io/studynote/2017/12/01/Android-Kotlin-ListView.html)

[RecyclerView](https://blog.yena.io/studynote/2017/12/06/Android-Kotlin-RecyclerView1.html)