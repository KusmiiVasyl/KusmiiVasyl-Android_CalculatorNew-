package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    private var expression: String = " "
    private var resultNumber: Double = 0.0
    private var isBtnOperationClick: Boolean = false
    private var isCurrentBtnOperationClick: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickBtnCalc(view: View) {
        val btn = view as Button
        btnAnimation(btn)
        when (btn.text) {
            button_clear.text -> {
                clearDisplayText()
            }

            button_plus_or_minus_sign.text -> {
                val text = textView_input_numbers.text
                if (text.contains('.') || text.first() != '-' && text.first() != '0') {
                    textView_input_numbers.text = "-$text"
                }
                if (text.first() == '-') {
                    textView_input_numbers.text = text.substring(1)
                    expression = expression.substring(1)
                }
            }

            button_percent.text -> {
                onOperationBtnClick(button_percent.text.toString())
            }

            button_division.text -> {
                onOperationBtnClick(button_division.text.toString())
            }

            button_multiplication.text -> {
                onOperationBtnClick(button_multiplication.text.toString())
            }

            button_subtraction.text -> {
                onOperationBtnClick(button_subtraction.text.toString())
            }

            button_addition.text -> {
                onOperationBtnClick(button_addition.text.toString())
            }

            button_equal.text -> {
                onOperationBtnClick(button_equal.text.toString())
            }

            button_dot.text -> {
                if (textView_input_numbers.text.length >= 16) return
                if (!textView_input_numbers.text.contains('.')) {
                    textView_input_numbers.text = "${textView_input_numbers.text}."
                }
            }

            button_zero.text -> {
                addDigitToDisplayNumber(button_zero.text.toString())
            }

            button_one.text -> {
                addDigitToDisplayNumber(button_one.text.toString())
            }

            button_two.text -> {
                addDigitToDisplayNumber(button_two.text.toString())
            }

            button_three.text -> {
                addDigitToDisplayNumber(button_three.text.toString())
            }

            button_four.text -> {
                addDigitToDisplayNumber(button_four.text.toString())
            }

            button_five.text -> {
                addDigitToDisplayNumber(button_five.text.toString())
            }

            button_six.text -> {
                addDigitToDisplayNumber(button_six.text.toString())
            }

            button_seven.text -> {
                addDigitToDisplayNumber(button_seven.text.toString())
            }

            button_eight.text -> {
                addDigitToDisplayNumber(button_eight.text.toString())
            }

            button_nine.text -> {
                addDigitToDisplayNumber(button_nine.text.toString())
            }

            button_ce.text -> {
                clearDisplayText()
            }

            button_fraction.text -> {
                textView_input_numbers.text =
                    ExpressionBuilder("1/${textView_input_numbers.text}")
                        .build().evaluate().toString()
            }

        }
    }

    private fun clearDisplayText() {
        textView_input_numbers.text = "0"
        expression = " "
        resultNumber = 0.0
        isBtnOperationClick = false
        isCurrentBtnOperationClick = false
    }

    private fun addDigitToDisplayNumber(btnDigit: String) {
        if (textView_input_numbers.text.length >= 16) return
        isCurrentBtnOperationClick = false
        if (textView_input_numbers.text == "0" || isBtnOperationClick) {
            textView_input_numbers.text = ""
            isBtnOperationClick = false
        }
        textView_input_numbers.text = "${textView_input_numbers.text}${btnDigit}"
    }

    private fun onOperationBtnClick(operation: String) {
        if (isCurrentBtnOperationClick) {
            expression = expression.dropLast(1)
        } else {
            if (expression.contains('=') || expression.contains('%')) expression = " "
            expression = "$expression${textView_input_numbers.text}"
        }

        if (operation == "%") {
            calculatePersentage()
            calculateExpression()
            expression = "$expression%"
            return
        }
        if (operation == "/") {
            calculateExpression()
            expression = "$expression/"
            return
        }
        if (operation == "x") {
            calculateExpression()
            expression = "$expression*"
            return
        }
        if (operation == "-") {
            calculateExpression()
            expression = "$expression-"
            return
        }
        if (operation == "+") {
            calculateExpression()
            expression = "$expression+"
            return
        }
        if (operation == "=") {
            calculateExpression()
            expression = "$expression="
        }
    }

    private fun calculateExpression() {
        isBtnOperationClick = true
        isCurrentBtnOperationClick = true
        resultNumber = ExpressionBuilder(expression).build().evaluate()
        val numFormat = DecimalFormat("#.##############")
        textView_input_numbers.text = numFormat.format(resultNumber)
        expression = "${textView_input_numbers.text}"
    }

    private fun calculatePersentage() {
        var curNumber: String = " "
        var prevNumber: String = ""
        curNumber = textView_input_numbers.text.toString()
        if (expression.length >= (curNumber.length + 1)) {
            prevNumber = expression.take(expression.length - (curNumber.length + 1))
        }
        expression = expression.take(prevNumber.length + 1)
        if (prevNumber == "") prevNumber = "0"
        expression = if (expression.contains("*") || expression.contains("/")) {
            "$expression($curNumber/100)"
        } else {
            "$expression(($prevNumber * $curNumber)/100)"
        }
    }

    private fun btnAnimation(btn: Button) {
        btn.alpha = 0.5f
        btn.animate().alpha(1f).duration = 200
    }
}




