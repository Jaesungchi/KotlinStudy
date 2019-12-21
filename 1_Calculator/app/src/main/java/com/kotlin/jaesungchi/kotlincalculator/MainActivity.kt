package com.kotlin.jaesungchi.kotlincalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var now_Operator:String? = null //현재의 연산자를 갖기 위한 변수
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
            clickMenuBtn("CE")
        }
        btn_C.setOnClickListener{
            clickMenuBtn("C")
        }
        btn_Plus.setOnClickListener {
            clickOperatorBtn("+")
        }
        btn_Minus.setOnClickListener {
            clickOperatorBtn("-")
        }
        btn_Times.setOnClickListener {
            clickOperatorBtn("*")
        }
        btn_Div.setOnClickListener {
            clickOperatorBtn("/")
        }
        btn_Result.setOnClickListener {
            clickMenuBtn("=")
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
    private fun clickMenuBtn(operator: String){
        when(operator){
            "CE" -> txt_Result.setText("0") //CE는 현재 적혀있는 것만 삭제한다.
            "C" -> initText() //C는 저장된 연산자도 삭제한다.
            "=" -> calculate()
            else -> Log.d("ClickMenuBtn","Error Operator : $operator")
        }
    }

    //초기화 버튼 눌렀을시 동작
    private fun initText(){
        txt_Result.setText("0")
        now_Operator = null
    }

    private fun clickOperatorBtn(operator: String){
        if(txt_Result.text == "0") //0이라면 아무것도 하지 않음
            return
        before_Number = txt_Result.text as String?
        when(operator){
            "+" -> now_Operator = "+"
            "-" -> now_Operator = "-"
            "*" -> now_Operator = "*"
            "/" -> now_Operator = "/"
            else -> Log.d("ClickOperatorBtn","Error Operator : $operator")
        }
        txt_Result.setText("0")
    }

    private fun calculate(){
        now_Operator?.let{ //operator가 null이 아닐때만 진행.
            Log.d("calculate","$before_Number and ${txt_Result.text}")
            var before:Int = before_Number!!.toInt()
            var after:Int = (txt_Result.text as String?)!!.toInt()
            Log.d("calculate","$before and $after")
            when(now_Operator){
                "+" ->txt_Result.setText("${before+after}")
                "-" ->txt_Result.setText("${before-after}")
                "*" ->txt_Result.setText("${before*after}")
                "/" ->txt_Result.setText("${before/after}")
            }
            before_Number = null
            now_Operator = null
        }
    }
}
