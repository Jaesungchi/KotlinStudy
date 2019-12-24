# Calculator :slot_machine:

![imge](https://img.shields.io/badge/ProjectType-SingleStudy-green) ![imge](https://img.shields.io/badge/Language-Kotlin-yellow)  ![imge](https://img.shields.io/badge/Tools-AndroidStudio-blue)

이 프로젝트는 Kotlin Study의 첫 프로젝트이며, Kotlin을 사용한 첫 프로그램이기 때문에 문제가 많을 수 있다.

계산기의 주요기능을 담고 예외처리를 하며 Kotlin을 공부하고 적응하기 위한 프로젝트이다.

기본적인 기능을 통해 Kotlin을 맛봤다! :apple:

---

윈도우 계산기를 모티브로 제작하였다. (아래 사진은 ver1 과 윈도우 계산기)

![버전 1과 윈도우 계산기](C:\Users\cjs60\AppData\Roaming\Typora\typora-user-images\1576938503298.png)

---

## Anko 라이브러리 추가.

Anko 라이브러리는 Kotlin 제작사인 JetBrains에서 만든 것으로 코드 작성을 도와준다. 4가지 라이브러리로 구성된다.

- Anko Commons : Intent, Dialog, Log의 편리한 사용
- Anko Layouts : 안드로이드 레이아웃을 코드로 쉽게 작성.
- Anko SQLite : SQLite 지원 라이브러리
- Anko Coroutines : Coroutines을 지원하는 라이브러리

#### 추가방법

 App단계의 Gradle에 dependencies에 추가한다.

![image](https://user-images.githubusercontent.com/37828448/71320873-15d3a200-24f5-11ea-837e-b690b7a4d8d9.png)

다음 프로젝트 수준의 Gradle 에 라이브러리 버전을 저장한다.
![image](https://user-images.githubusercontent.com/37828448/71320886-474c6d80-24f5-11ea-9961-c8a0b9945f44.png)

Sync Now를 해 Sync 한다.

---

## 출처

[Anko 추가하기](https://enfanthoon.tistory.com/36?category=870223)