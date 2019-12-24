package com.kotlin.jaesungchi.kotlincalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var now_Operator:Int? = null //현재의 연산자를 갖기 위한 변수
    var before_Number:String? = null//전에 저장한 숫자 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_Zero.setOnClickListener{
            clickNumberBtn("0")
        }
        btn_One.setOnClickListener{
            clickNumberBtn("1")
        }
        btn_Two.setOnClickListener{
            clickNumberBtn("2")
        }
        btn_Three.setOnClickListener{
            clickNumberBtn("3")
        }
        btn_Four.setOnClickListener{
            clickNumberBtn("4")
        }
        btn_Five.setOnClickListener{
            clickNumberBtn("5")
        }
        btn_Six.setOnClickListener{
            clickNumberBtn("6")
        }
        btn_Seven.setOnClickListener{
            clickNumberBtn("7")
        }
        btn_Eight.setOnClickListener{
            clickNumberBtn("8")
        }
        btn_Nine.setOnClickListener{
            clickNumberBtn("9")
        }
        btn_CE.setOnClickListener{
            clickMenuBtn(Constants.CE)
        }
        btn_C.setOnClickListener{
            clickMenuBtn(Constants.C)
        }
        btn_Plus.setOnClickListener {
            clickOperatorBtn(Constants.PLUS)
        }
        btn_Minus.setOnClickListener {
            clickOperatorBtn(Constants.MINUS)
        }
        btn_Times.setOnClickListener {
            clickOperatorBtn(Constants.TIMES)
        }
        btn_Div.setOnClickListener {
            clickOperatorBtn(Constants.DIVISION)
        }
        btn_Result.setOnClickListener {
            clickMenuBtn(Constants.RESULT)
        }
        btn_Delete.setOnClickListener {
            clickNowBtn(Constants.DEL)
        }
        btn_Root.setOnClickListener {
            clickNowBtn(Constants.ROOT)
        }
        btn_Denominator.setOnClickListener {
            clickNowBtn(Constants.DENOMINATOR)
        }
        btn_Square.setOnClickListener {
            clickNowBtn(Constants.SQAURE)
        }
        btn_Percent.setOnClickListener {
            clickNowBtn(Constants.PERCENT)
        }
    }

    //숫자버튼 눌렀을때 동작.
    private fun clickNumberBtn(number: String){
        if(txt_Result.text == "0" && number != "0") //0인 경우에는 숫자를 바꾼다.
            txt_Result.setText(number)
        else if(txt_Result.text == "0" && number == "0")
            txt_Result.setText("0")
        else
            txt_Result.setText("${txt_Result.text}$number")
    }

    //CE 나 C 등 Menu버튼을 눌렀을때 동작
    private fun clickMenuBtn(operator: Int){
        when(operator){
            Constants.CE -> txt_Result.setText("0") //CE는 현재 적혀있는 것만 삭제한다.
            Constants.C -> initText() //C는 저장된 연산자도 삭제한다.
            Constants.RESULT -> calculate()
            else -> Log.d("ClickMenuBtn","Error Operator : $operator")
        }
    }

    //초기화 버튼 눌렀을시 동작
    private fun initText(){
        txt_Result.setText("0")
        now_Operator = null
    }

    private fun clickOperatorBtn(operator: Int){
        if(txt_Result.text == "0") //0이라면 아무것도 하지 않음
            return
        before_Number = txt_Result.text as String?
        now_Operator = operator //현재의 연산자를 담아 둔다.
        txt_Result.setText("0")
    }

    private fun calculate(){
        now_Operator?.let{ //operator가 null이 아닐때만 진행.
            Log.d("calculate","$before_Number and ${txt_Result.text}")
            var before:Int = before_Number!!.toInt() //2번 해야 안전성이 확보된다 ...?
            var after:Int = getResultTextToInt()
            Log.d("calculate","$before and $after")
            when(now_Operator){ //종류에 따라 값을 바꿔준다.
                Constants.PLUS ->txt_Result.setText("${before+after}")
                Constants.MINUS ->txt_Result.setText("${before-after}")
                Constants.TIMES ->txt_Result.setText("${before*after}")
                Constants.DIVISION ->txt_Result.setText("${before/after}")
            }
            before_Number = null
            now_Operator = null
        }
    }

    private fun clickNowBtn(operator: Int){
        if(txt_Result.text != "0"){ //0이 아닐때만 동작
            var nowResult = getResultTextToInt()
            var limitSize:Int = 1 //어디까지 줄일 수 있는지 확인해줌
            when(operator){
                Constants.PERCENT -> return //% 인경우
                Constants.DENOMINATOR -> txt_Result.setText("${ (1 / nowResult).toDouble()}") //1/x 인 경우
                Constants.SQAURE -> txt_Result.setText("${nowResult * nowResult}")
                Constants.ROOT -> txt_Result.setText("${Math.sqrt(nowResult.toDouble())}")
                Constants.DEL -> {
                    if(nowResult < 0) //0보다 작은 경우는
                        limitSize++ //-를 지우면 안되기 때문
                    if(txt_Result.text.length > limitSize)
                        txt_Result.setText("${txt_Result.text.subSequence(0,txt_Result.text.length - 1)}")
                    else
                        txt_Result.setText("0")
                }
            }
        }
    }

    private fun getResultTextToInt(): Int{
        return (txt_Result.text as String?)!!.toInt()
    }
}
