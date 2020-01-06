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

도시별 날씨의 목록을 만들기 때문에, CityVO 클래스 파일을 만든다 항목은 다음과 같다.

```kotlin
class CityVO (val name: String, val time: String, val weather: String, val temperature : String)
```

### (3) 레이아웃에 RecyClerView 추가

```
<android.support.v7.widget.RecyclerView
	android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/mRecyclerView"
    android:layout_marginStart="8dp"
    android:layout_marginEnd="8dp"
    android:layout_marginTop="8dp"
    android:layout_marginBottom="8dp">
</android.support.constraint.ConstraintLayout>
```

### (4) item 생성

RecyclerView의 항목을 담당할 item View를 만든다.

### (5) Adapter 생성

 Adapter란 각요소를 어느 View에 넣을 지 연결해 주는 것이다.

Recycler의 Adapter는 기본으로 RecyclerView.Adapter를 extend 해야한다. 하지만 이걸 쓰기 위해서는 ViewHolder가 필요한데 아직 없으므로 내부 클래스로 생성해준다.

```kotlin
class MainRVAdapter(val context: Context, val cityList: ArrayList<CityVO>): RecyclerView.Adapter<MainRVAdapter.Holder>() {

    //화면을 최초 로딩하여 만들어진 View가 없는 경우,xml 파일을 inflate하여 ViewHolder를 생성한다.
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder{
        val view = LayoutInflater.from(context).inflate(R.layout.main_recycler_item, parent, false)
        return Holder(view)
    }

    //위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: Holder?,position: Int){
        holder?.bind(cityList[position],context)
    }

    //RecyclerView로 만들어지는 item의 총 갯수를 반환한다.
    override fun getItemCount(): Int {
       return cityList.size;
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val weatherImg = itemView?.findViewById<ImageView>(R.id.weatherImg)
        val cityName = itemView?.findViewById<TextView>(R.id.cityNameTxt)
        val cityWeather = itemView?.findViewById<TextView>(R.id.cityWeatherTxt)
        val cityTemp = itemView?.findViewById<TextView>(R.id.cityTempTxt)

        fun bind(city: CityVO, context: Context) {
            /* dogPhoto의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고, 이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/
            if (city.weather != "") {
                val resourceId = context.resources.getIdentifier(city.weather, "drawable", context.packageName)
                weatherImg?.setImageResource(resourceId)
            } else {
                weatherImg?.setImageResource(R.mipmap.ic_launcher)
            }
            cityName?.text = city.name
            cityWeather?.text = city.weather
            cityTemp?.text = city.temperature
        }
    }
}
```

### (6) 어댑터 설정

어댑터를 다 만들었으니 이제 메인으로 돌아가 Adapter를 생성하고, 어떤 데이터와 어떤 RecyclerView를 쓸 것인지 설정한다.

```kotlin
class MainView : AppCompatActivity() {

    var cityList = ArrayList<CityVO>(); //도시 저장용.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)

        val mAdapter = MainRVAdapter(this,cityList)
        mRecyclerView.adapter = mAdapter
    }
}
```

추가로 RecyclerView Adapter는 레이아웃 매니저를 설정해주어야 한다.

레이아웃매니저는 각 item을 배치하고, item이 더 이상 보이지 않을 때 재사용할 것인지 결정하는 역할을 한다. item을 재사용할 때, LayoutManager는 Adapter 에게 view의 요소를 다른 데이터로 대체 할 것인지를 물어본다. LayoutManage를 통해 불필요한 findViewByld를 수행하지 않아도 되고, 성능을 향상 시킨다.

종류는 LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager가 있다.

recyclerview에 setHasFixedSize 옵션에 true 값을 준다. 이유는 item이 추가되거나 삭제될 때 크기가 변경될 수 있고, 그렇게 되면 다른 View의 크기가 변경될 가능성이 있기 때문이다.

```kotlin
//추가
val lm = LinearLayoutManager(this)
mRecyclerView.layoutManager = lm
mRecyclerView.setHasFixedSize(true)
```

---

## 출처

[ListView](https://blog.yena.io/studynote/2017/12/01/Android-Kotlin-ListView.html)

[RecyclerView](https://blog.yena.io/studynote/2017/12/06/Android-Kotlin-RecyclerView1.html)