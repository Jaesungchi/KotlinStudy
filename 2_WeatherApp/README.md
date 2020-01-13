## Kotlin Study for [Weather App](https://github.com/Jaesungchi/WeatherApp):green_book:

![imge](https://img.shields.io/badge/ProjectType-SingleStudy-green)  ![imge](https://img.shields.io/badge/Language-Kotlin-yellow)  ![imge](https://img.shields.io/badge/Tools-AndroidStudio-blue)

날씨 앱을 만들기 위한 코틀린 공부 정리

날씨 앱을 만들기 위해 필요한 기능.

1. RecyclerView : 도시 별 날씨를 보기 위해
3. 액티비티 전환(Anko라이브러리)
4. 날씨 API 사용
4. RecyclerView 제스쳐 기능
5. Splash이미지 적용
6. ActionBar 버튼 추가
7. 날씨 불러올때 로딩
8. 도시 검색 및 추가

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
class CityDTO (val name: String, val time: String, val weather: String, val temperature : String)
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
class MainRVAdapter(val context: Context, val cityList: ArrayList<CityDTO>): RecyclerView.Adapter<MainRVAdapter.Holder>() {

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

        fun bind(city: CityDTO, context: Context) {
            /* setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고, 이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/
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

    var cityList = ArrayList<CityDTO>(); //도시 저장용.

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

### (7)itemClick Listener

RecyclerView에서는 ListView와 다르게 별도로 클릭리스너를 생성해주어야 한다. 이 처리는 Adapter에서 람다를 통해 설정해주었다. 코틀린에서도 람다는 사용이 가능하므로, CityDTO를 파라미터로 받아서 아무것도 반환하지 않는 파라미터를 람다식으로 나타낸다.

```kotlin
val itemClick : (CityDTO) -> Unit
```

Unit의 함수 자체를 itemClick 변수에 넣는다.  이제 itemClick 변수를 Adapter의 파라미터로 넣고, Adapter내에서 setOnClickListener 기능을 설정할 때 (CityDTO) -> Unit에 해당하는 함수 자체를 하나의 변수로 꺼내 쓸 수 있는 것이다.

```kotlin
class MainRVAdapter(val context: Context, val cityList: ArrayList<CityDTO>,val itemClick: (CityDTO) -> Unit): RecyclerView.Adapter<MainRVAdapter.Holder>() {
    //(1)Adapter의 파라미터에 람다식 itemClick을 넣는다.
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder{
        val view = LayoutInflater.from(context).inflate(R.layout.main_recycler_item, parent, false)
        return Holder(view,itemClick)
        //(4) Holder의 파라미터가 하나 더 추가됐으니 여기도 파라미터 추가.
    }
    
    ...
    ...
    ...
    
	inner class Holder(itemView: View?, itemClick:(CityDTO) -> Unit) : RecyclerView.ViewHolder(itemView) {
	    //(2)Holder에서 클릭에 대한 처리를 하므로, Holder의 파라미터에 람다식 itemClick을 추가
   		...
    	...
    	...
    	cityTemp?.text = city.temperature
        
    	itemView.setOnClickListener{itemClick(city)}
    	//(3)itemView가 클릭됐을 때 처리할 일을 itemClick으로 설정.
        //(CityDTO) -> Unit 에 대한 함수는 나중에 Main에서 작성한다.
    	}
	}
}
```

이 후 Adapter에서 itemClick에 대한 설정이 끝났으니 main에서 람다에 실행할 코드를 입력하면 마무리 된다! 일단 예제로 간단하게 Toast 메세지만 띄움

```kotlin
val mAdapter = MainRVAdapter(this,cityList){ city ->
    toast(this,"이 도시의 이름은 ${city.name}이며, 현재 날씨는 ${city.weather}입니다.",Toast.LENGTH_LONG)
}
```

자 여기까지 했으면 어느정도 View가 완성되었을 것이다! 다음은 Activity 전환이다!

## 2. 액티비티 전환

### Anko 라이브러리란?

- Intent, Dialog 등을 편하게 사용 가능하게 하는 라이브러리로 다양하게 큰 도움을 준다.

### 사용 전 설정

1. AnKo라이브러리 추가.

   ```kotlin
   dependencies{implementation "org.jetbrains.anko:anko:$anko_version"}
   ```

2. 프로젝트 수준의 gradle 파일 수정

   ```kotlin
   buildscript{ ext.anko_version = '0.10.5'}
   ```

### 액티비티 이동

Anko 사용 전

```kotlin
val intent = Intent(this,FindActivity::class.java)
intent.putExtra("weight",180)
intent.putExtra("height",70)
startActivity(intent)
```

Anko 사용 후

```kotlin
startActivity<FindActivity>("weight" to 100, "height" to 70)
```

이렇게 한 줄로 코드를 줄일 수 있다! 전달 받는 방법 또한 매우 간단하다!

```kotlin
val height = intent.getIntExtra("height",0)
val weight = intent.getIntExtra("weight",0)
```

여기서 갑자기 나오는 intent는 코틀린에서 알아서 처리해준다. 아주 개꿀이다.

이후 추가로 Manifest.xml에 추가하면 좋은 기능이다.

```xml
<activity
	android:name=".FindActivity"
	android:parentActivityName=".MainActivity">
</activity>
```

//// 추가로 Toast 위젯또한 toast("~~~") 면 끝난다. 개꿀이 아닐 수 없다.

## 3. 날씨 API 사용

날씨 API는 세계날씨 정보를 가져오는 것이기 때문에 https://openweathermap.org/ 를 활용하기 로했다.

예시로 api.openweathermap.org/data/2.5/weather?q="" 여기에 API calls를 하면 된다고 나와있다.

일단 라이브러리를 추가한다.

- retrofit : Retrofit 라이브러리
- converte-gson : Json 데이터를 파싱하기 위한 라이브러리
- adapter-rxjava2 : Retrofit을 Rx 형태로 사용하도록 설정해주는 어댑터
- okhttp,logging-intercepter : Retrofit을 사용해 받는 HTTP데이터를 로그상으로 확인하기 위한 라이브러리

### (1) 데이터 클래스 정의

이후 데이터를 받기 위한 데이터 클래스를 정의한다. (각 항목은 API 반환 데이터에 맞게 구성됨)

```kotlin
class WeatherModel (){
    @SerializedName("name")
    val name: String = ""

    @SerializedName("weather")
    val weather: List<WeatherData> = listOf()

    @SerializedName("main")
    val main: MainData? = null

    @SerializedName("visibility")
    val visibility: String = ""

    @SerializedName("wind")
    val wind: WindData? = null
}
```

### API 클래스 정의

```kotlin
class WeatherApi{

    interface  WeatherApiImpl{
        @GET("weather?")
        fun getWeatherList(@Query("q") query: String,@Query("APPID") APPID: String): Observable<WeatherModel>
    }

    companion object {
        fun getWeatherList(query: String): Observable<WeatherModel> {
            return RetrofitCreator.create(WeatherApiImpl::class.java).getWeatherList(query,"appid")
        }
    }
}
```

### Creator 생성

```kotlin
class RetrofitCreator{
    companion object {
        val API_BASE_URL = "https://api.openweathermap.org/data/2.5/"

        private fun defaultRetrofit() : Retrofit{
            return Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(createOKHttpClient())
                    .build()
        }

        fun <T> create(service: Class<T>): T {
            return defaultRetrofit().create(service)
        }

        private fun createOKHttpClient(): OkHttpClient{
            val interceptor = HttpLoggingInterceptor()
            if(BuildConfig.DEBUG){
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            return OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .build()
        }
    }
}
```

Retrofit을 호출하기 위한 Creator를 정의합니다.

### API 호출

```kotlin
compositeDisposable = CompositeDisposable()

        compositeDisposable.add(WeatherApi.getWeatherList(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(newThread())
                .subscribe({response: WeatherModel ->
                    testtext.append("name : ${response.name}\n")
                    testtext.append("temp : ${response.main?.temp.toString()}\n")
                    testtext.append("weather : ${response.weather.get(0).main}\n")
                    testtext.append("description : ${response.weather.get(0).description}\n")
                }, { error : Throwable ->
                    Log.d("test",error.localizedMessage)
                    testtext.text = error.localizedMessage
                })
        )
```

## 4. RecyclerView 제스쳐 기능

현재 기능에는 삭제나 리스트 순서 변경이 없기 때문에 Recycler의 제스쳐 기능을 통해 위치를 바꿀 수 있게 한다. 이번에는 2가지 기능을 넣을 예정이다.

- Drag & Drop : 순서 변경
- Swipe to Dismiss : 옆으로 밀면 아이템 삭제

### ItemTouchHelper

이것은 RecyclerView.ItemDecoration의 서브 클래스로 RecyclerView 및 Callback 클래스와 함께 동작한다. 사용자가 위의 액션을 수행할때 이벤트를 수신한다. 이제 우리가 원하는 기능에 따라 메소드를 재정의 해서 사용하면 된다.

ItemTouchHelper.Callback은 추상 클래스로 추상 메소드인 getMovementFlags(), onMove(), onSwipe()를 필수로 재정의해서 사용한다. 아니면 Wrapper 클래스인 ItemTouchHelper.SimpleCallback을 이용해도 된다.

일단 사용자가 Drag 액션을 시작하면 itemTouchHelper에 이벤트를 전달할 DragListener를 만든다. 그리고 아이템이 Drag & Drop이나 Swiped 됐을 때 어댑터에 이벤트를 전달할 ActionListener를 만든다.

```kotlin
interface ItemActionListener{
    fun onItemMoved(from: Int, to: Int)
    fun onItemSwiped(position: Int)
}
interface ItemDragListener{
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}

```

이제 MainRVAdapter를 수정한다.

```kotlin
class MainRVAdapter(val context: Context, val cityList: ArrayList<CityModel>, val itemClick:(CityModel)->Unit, val listener: ItemDragListener): RecyclerView.Adapter<MainRVAdapter.Holder>(), ItemActionListener {
    
    override fun onItemSwiped(position: Int) {
        cityList.removeAt(position)
        notifyItemRemoved(position) //지운것을 알린다.
    }

    override fun onItemMoved(from: Int, to: Int) {
        if(from == to) return
        val fromCity = cityList.removeAt(from) //지운뒤
        cityList.add(to,fromCity) //자리에 옮긴다
        notifyItemMoved(from,to) //바뀐것을 알린다.
    }
    ...
    ...
}
```

다음은 내부 클래스로 선언한 Holder에 드래그 핸들을 통한 아이템 이동을 위해 , 드래스 핸들 뷰에 터치 리스너를 달아 준다.

```kotlin
inner class Holder(itemView: View,itemClick: (CityModel) -> Unit,listener: ItemDragListener) : RecyclerView.ViewHolder(itemView) {
    init{
        itemView.setOnTouchListener{ v,event->
           if(event.action == MotionEvent.ACTION_DOWN){
                listener.onStartDrag(this)
            }
            false
        }
    }
    ...
    ...
}
```

이제 거의 다 왔다. ItemTouchHelper.Callback을 상속받는 클래스를 만든다. 파라미터로 ItemActionListener를 받는다.

1. getMovementFlags()를 재정의해 Drag 및 Swipe 이벤트의 방향을 지정한다.
2. 아이템이 Drag 되면 이 클래스는 onMove()를 호출한다. 이때 ItemActionListener로 어댑터에 fromPosition과 toPosition을 파라미터와 함께 콜백을 전달한다.
3. Swipe되면 ItemTouchHelper가 범위를 벗어날 때까지 애니매이션을 적용한 후, onSwipe()를 호출한다. 이때 ItemActionListener로 어댑터에 제거할 아이템의 Postion을 파라미터와 함께 콜백을 전달한다.

```kotlin
class ItemTouchHelperCallback(val listener:ItemActionListener) : ItemTouchHelper.Callback(){
    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.DOWN or ItemTouchHelper.UP
        val swipeFalgs = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags,swipeFalgs)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        listener.onItemMoved(p1.adapterPosition,p2.adapterPosition)
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        listener.onItemSwiped(p0.adapterPosition)
    }
}
```

마지막으로 MainActivity에서 인터페이스를 구현한다.

```kotlin
class MainActivity : AppCompatActivity(), ItemDragListener{
	override fun onCreate(savedInstanceState: Bundle?){
		...
		...
		itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(mAdapter))
        itemTouchHelper!!.attachToRecyclerView(mRecyclerView)
	}
	override fun onStartDrag(viewHolder: RecyclerView.ViewHolder){
		itemTouchHelper.startDrag(viewHolder)
	}
}
```

여기까지 하니 드래그는 되는데 터치랑 스와이프가 안먹힌다!! 

Holder에 TouchListener로 되어있으니 누구보다 제일 빠른 순위로 Drag & Drop이 먹히는 것 같다.이것을 LongClickListener로 바꾸어 준다! 해결!**(Kotlin으로 이런 예제가 없어서 애먹음)**

```kotlin
itemView.setOnLongClickListener{ I ->
    listener.onStartDrag(this)
    false
}
```

## 5. Splash이미지 적용

아주 간단하게 image를 만들어 drawable에 넣은 후 코딩 작업을 해준다.

먼저 SplashActivity를 만든다.

```kotlin
class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        startLoading()
    }

    private fun startLoading(){
        val handler = Handler()
        handler.postDelayed(Runnable{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}
```

이후 Manifest에 작업을 해준다!

```xml
<activity android:name=".SplashActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
    </intent-filter>
</activity>
```

이때 MainActivity의 intent 필터는 전부 지워야한다!

## 6. ActionBar 버튼 추가

이제 도시를 내 맘대로 넣을 수 있게 ActionBar에 추가 버튼을 넣어서 도시를 추가하는 Activity로 이동시키는 기능을 만들자. 기존 액션바를 안보이게 하고 새로 추가하기 위해 Style을 바꿔준다.

```xml
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
</style>
```

이후 원하는 레이아웃에 추가한다!

이미지 버튼에 이미지는 src  에 넣어야 한다!

## 7. 날시 불러올때 로딩

날씨를 API를 이용해서 불러오기 때문에 로딩에 시간이 걸린다. 따라서 로딩 이미지를 넣어주어 기다리는 시간을 지루하지 않게 해준다.

간단하게 메소드 2개를 추가하면 안드로이드 기본 로딩을 사용할 수 있다.

```kotlin
private fun loading() {
    //로딩
    android.os.Handler().postDelayed(
           {
                progressDialog = ProgressDialog(this)
                progressDialog!!.isIndeterminate = true
                progressDialog!!.setMessage("잠시만 기다려 주세요")
                progressDialog!!.show()
            }, 0)
}
private fun loadingEnd() {
    android.os.Handler().postDelayed(
            { progressDialog!!.dismiss() }, 0)
}
```

## 8. 도시 검색 및 추가

도시를 실시간으로 검색하기 위해서 RealmSearchView를 이용할 예정이다.

RealmSearchView를 이용하기 전 gradle에 jitpack.io를 추가하여 적용하자.

```xml
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

그리고 dependencies에도 아래 내용을 추가한다.

```
classpath "io.realm:realm-gradle-plugin:0.88.2"
```

그리고 앱단 gradle에도 추가한다.

```xml
implementation 'com.github.thorbenprimke:realm-searchview:0.9.1'
```

이후 search_layout을 만들어 밑의 레이아웃을 추가한다.

```xml
<FrameLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="11"
        android:layout_marginTop="10dp">

        <co.moonmonkeylabs.realmsearchview.RealmSearchView
            android:id="@+id/search_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:rsvHint="@string/search_hint"
            />
</FrameLayout>
```

Realm의 기본적인 모델의 모양은 다음과 같다.

```kotlin
public open class 이름 : RealmObject(){
    public open var name : String? = null
}
```

코틀린은 기본이 final 이고 open 키워드로 개방해야 한다.

우리는 도시 검색을 하기 위해 만들기 때문에 모델은 간단하게 만든다.

```kotlin
open class City : RealmObject(){
    open var name : String? = null
}
```

그리고 Adapter를 만든다.

```kotlin
class CityRVAdpater(val context: Context, val realm: Realm, val filterName : String) : RealmSearchAdapter<City, CityRVAdpater.ViewHolder>(context,realm,filterName) {
    override fun convertViewHolder(viewHolder: RealmViewHolder?): ViewHolder {
        return viewHolder as ViewHolder   
    }

    override fun onBindRealmViewHolder(viewHolder: ViewHolder?, position: Int) {
        val mcity : City = realmResults.get(position)
        viewHolder?.bind(mcity)
    }

    override fun onCreateRealmViewHolder(viewGroup: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_layout, viewGroup, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView : View) : RealmViewHolder(itemView){
        init{
            itemView.setOnClickListener{ I ->
                //여기에 할 일 추가.
                false
            }
        }

        val cityImg = itemView?.findViewById<ImageView>(R.id.cityImg)
        val cityName = itemView?.findViewById<TextView>(R.id.cityName)
        fun bind(city: City) {
            cityName?.text = city.name
        }
    }
}
```



이제 아이템을 bind 하기 위해 편리한 기능인 ButterKnife를 사용해보자. 먼저 프로젝트 단 gradle에 아래 내용을 추가한다.

```xml
classpath 'com.jakewharton:butterknife-gradle-plugin:10.2.1'
```

그리고 app단 gradle 에도 아래 내용을 추가하고 sync 한다.

```xml
implementation 'com.jakewharton:butterknife:10.2.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.1'
```



---

## 이슈

```
communication to api.openweathermap.org not permitted by network security policy
```

가 뜬다면 API 호출 주소를 https 로 바꾸어야한다. 안드로이드 파이부터 바뀌어서 보안상 이렇게 해주어야 한다고 한다.

---

```
Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path 
```

Json 포멧이 제대로 안먹히는 경우이다 . 잘보니 json 포멧중에 [] 로 감싸 오는것만 List로 받을 수 있고 {} 안에 {} 가 있다면 그 자체로 받아야 한다.

---

```text
Annotation processors must be explicitly declared now. The following dependencies on the compile classpath are found to contain annotation processor. Please add them to the annotationProcessor configuration.
```

RealmSearchView를 Gradle에 Synk 후에 빌드하니 이런 에러가 떴다.

annotation processeor로 써야한다는 말로써 implement 밑에 

```
annotationProcessor 'com.github.thorbenprimke:realm-searchview:0.9.1'
```

를 추가하니 에러가 사라졌다.

---

```
Manifest merger failed : Attribute application@appComponentFactory value=(android.support.v4.app.CoreComponentFactory) from [com.android.support:support-compat:28.0.0] AndroidManifest.xml:22:18-91
	is also present at [androidx.core:core:1.0.0] AndroidManifest.xml:22:18-86 value=(androidx.core.app.CoreComponentFactory).
	Suggestion: add 'tools:replace="android:appComponentFactory"' to <application> element at AndroidManifest.xml:5:5-24:19 to override.
```

아마 Gradle 업데이트 하니 에러가 생긴듯. Android X로 패키지명을 바꿔야 함. 

manifests application에 아래 내용을 추가한다.

```xml
tools:replace="android:appComponentFactory"
android:appComponentFactory="whateverString"
```

---

```
Unresolved reference: R
```

이 에러는 갑자기 뜨기 시작했다... Gradle의 업데이트 문제인것 같아 전으로 초기화해서 살렸다.

---

```
java.lang.RuntimeException: Unable to start activity ComponentInfo{com.kotlin.jaesungchi.weatherapp/com.kotlin.jaesungchi.weatherapp.SearchActivity}: java.lang.IllegalArgumentException: City is not part of the schema for this Realm
```

realm.copyToRealm 에서 에러가 발생했다.

app 단 Gradle에 아래 내용을 추가해준다.

```
apply plugin: 'kotlin-kapt'
apply plugin: 'realm-android'

android {
	buildToolsVersion "27.0.3"
	...
}
```

그랬더니 아래 에러가 뜬다.

```
e: error: A default public constructor with no argument must be declared if a custom constructor is declared.
```

이 에러는 Object의 Error이다 . object를 바꿔준다.

```kotlin
data class City(var name : String) : RealmObject() //에서
//아래로 바꿔준다.
open class City(var name: String?= null) : RealmObject()
```

또 에러가 뜬다.

```
More than one file was found with OS independent path 'lib/armeabi/librealm-jni.so'
```

app 단의 Gradle의 android에 아래 내용을 추가한다.

```
packagingOptions {
    pickFirst 'lib/armeabi/*'
}
```

위와 같은 에러는 다 pickFirst로 추가해준다.

그래도 더 에러가 심해진다 ...

---

## 출처

[ListView](https://blog.yena.io/studynote/2017/12/01/Android-Kotlin-ListView.html)

[RecyclerView](https://blog.yena.io/studynote/2017/12/06/Android-Kotlin-RecyclerView1.html)

[액티비티전환](https://shacoding.com/2019/08/15/android-anko-라이브러리로-액티비티-이동-쉽게-하기-with-코틀린/)

[RecyclerView 제스쳐](http://dudmy.net/android/2018/05/02/drag-and-swipe-recyclerview/)

[RealmSearchView](https://academy.realm.io/kr/posts/android-search-text-view/)