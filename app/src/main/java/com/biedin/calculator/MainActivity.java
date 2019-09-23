package com.biedin.calculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.mariuszgromada.math.mxparser.Expression;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewValue;
    private TextView textViewResult;
    private Vibrator vibratorButtons;
    private Animation animation;

    private boolean canOperate = false;
    private boolean hasDot = false;
    private boolean canOperators = false;

    private static final String CAN_OPERATE = "canOperate";
    private static final String HAS_DOT = "hasDot";
    private static final String HAS_OPERATION = "hasOperation";
    private static final String RESULT = "result";
    private static final String VALUE = "value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibratorButtons = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        textViewValue = findViewById(R.id.textViewValue);
        textViewResult = findViewById(R.id.textViewResult);
        final Button button0 = findViewById(R.id.buttonNumberZero);
        final Button button1 = findViewById(R.id.buttonNumberOne);
        final Button button2 = findViewById(R.id.buttonNumberTwo);
        final Button button3 = findViewById(R.id.buttonNumberThree);
        final Button button4 = findViewById(R.id.buttonNumberFour);
        final Button button5 = findViewById(R.id.buttonNumberFive);
        final Button button6 = findViewById(R.id.buttonNumberSix);
        final Button button7 = findViewById(R.id.buttonNumberSeven);
        final Button button8 = findViewById(R.id.buttonNumberEight);
        final Button button9 = findViewById(R.id.buttonNumberNine);
        final Button buttonDivide = findViewById(R.id.buttonDivide);
        final Button buttonAdd = findViewById(R.id.buttonAdd);
        final Button buttonMultiply = findViewById(R.id.buttonMultiply);
        final Button buttonSubtract = findViewById(R.id.buttonSubtract);
        final Button buttonDEL = findViewById(R.id.buttonDel);
        final Button buttonEqual = findViewById(R.id.buttonEqual);
        final Button buttonDot = findViewById(R.id.buttonDot);
        animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClick = (Button) v;
                int tempId = buttonClick.getId();
                switch (tempId) {
                    case R.id.buttonAdd:
                        vibratorButtons.vibrate(10);
                        addOperation();
                        break;
                    case R.id.buttonDivide:
                        vibratorButtons.vibrate(10);
                        divideOperation();
                        break;
                    case R.id.buttonMultiply:
                        vibratorButtons.vibrate(10);
                        multiplyOperation();
                        break;
                    case R.id.buttonSubtract:
                        vibratorButtons.vibrate(10);
                        subtractOperation();
                        break;
                    case R.id.buttonDel:
                        vibratorButtons.vibrate(10);
                        buttonClick.startAnimation(animation);
                        delOperation();
                        break;
                    case R.id.buttonEqual:
                        vibratorButtons.vibrate(10);
                        equalOperation();
                        break;
                    case R.id.buttonDot:
                        vibratorButtons.vibrate(10);
                        dotOperation(buttonClick);
                        break;
                    default:
                        vibratorButtons.vibrate(10);
                        addNumber(buttonClick);
                        break;
                }
            }
        };
        button0.setOnClickListener(click);
        button1.setOnClickListener(click);
        button2.setOnClickListener(click);
        button3.setOnClickListener(click);
        button4.setOnClickListener(click);
        button5.setOnClickListener(click);
        button6.setOnClickListener(click);
        button7.setOnClickListener(click);
        button8.setOnClickListener(click);
        button9.setOnClickListener(click);
        buttonDivide.setOnClickListener(click);
        buttonAdd.setOnClickListener(click);
        buttonSubtract.setOnClickListener(click);
        buttonMultiply.setOnClickListener(click);
        buttonEqual.setOnClickListener(click);
        buttonDEL.setOnClickListener(click);
        buttonDot.setOnClickListener(click);
        buttonDEL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteAll();
                return true;
            }
        });
    }

    private void deleteAll() {
        vibratorButtons.vibrate(1300);
        textViewValue.setText("");
        hasDot = false;
        canOperate = false;
        canOperators = false;
        textViewResult.setText("");
        textViewValue.setTextSize(50);
        textViewResult.setTextColor(Color.BLACK);
        textViewValue.setTextColor(Color.BLACK);
        Toast.makeText(getApplicationContext(), R.string.deleteAll, Toast.LENGTH_LONG).show();
    }

    public void addNumber(Button buttonClick) {
        if (textViewValue.getText().length() <= 30) {
            textViewValue.append(buttonClick.getText().toString());
            canOperate = true;
            canOperators = true;
            doCalc();
        } else {
            Toast.makeText(getApplicationContext(), R.string.limit_of_numbers, Toast.LENGTH_LONG).show();
        }
        if (textViewValue.length() > 10) {
            textViewValue.setTextSize(35);
        } else {
            textViewValue.setTextSize(50);
        }
    }

    private void doCalc() {
        if (canOperate) {
            if (textViewValue.getText().length() > 0) {
                String textView = textViewValue.getText().toString();
                String temp = textView.replace("÷", "/");
                String expressionText = temp.replace("×", "*");
                Expression expression = new Expression(expressionText);
                if (hasDot) {
                    double result = expression.calculate();
                    textViewResult.setText(String.valueOf(result));
                    if (textViewResult.getText().toString().equals("NaN")) {
                        textViewResult.setText(R.string.wrong_expression);
                        textViewResult.setTextColor(Color.RED);
                    }
                } else {
                    int result = (int) expression.calculate();
                    textViewResult.setText(String.valueOf(result));
                    if (textViewResult.getText().toString().equals("NaN")) {
                        textViewResult.setText(R.string.wrong_expression);
                        textViewResult.setTextColor(Color.RED);
                    }
                }
            } else {
                textViewResult.setText("");
            }
        }
    }


    public char lastCharacter() {
        char c = '0';
        if (textViewValue.getText().length() > 0) {
            String textOfExpressions = textViewValue.getText().toString();
            c = textOfExpressions.charAt(textOfExpressions.length() - 1);
        }
        return c;
    }

    public void dotOperation(Button buttonClick) {
        if (canOperators && textViewValue.getText().length() > 0 && !(lastCharacter() == '.')) {
            textViewValue.append(buttonClick.getText());
            hasDot = true;
        } else if (textViewValue.getText().length() == 0 && !canOperators) {
            textViewValue.append("0.");
        } else {
            Toast.makeText(getApplicationContext(), R.string.dot_already_exists, Toast.LENGTH_LONG).show();
        }
    }

    public void equalOperation() {
        String value = textViewValue.getText().toString();
        if (value.contains("÷0")) {
            textViewResult.setText(R.string.wrong_expression);
            textViewResult.setTextColor(Color.RED);
            textViewValue.setTextColor(Color.RED);
        } else {
            canOperate = true;
            doCalc();
            String result= textViewResult.getText().toString();
            textViewResult.setText("");
            textViewValue.setText(result);
        }
        if (textViewValue.length() > 10) {
            textViewValue.setTextSize(35);
        } else {
            textViewValue.setTextSize(50);
        }
    }

    public void subtractOperation() {
        if (canOperators && lastCharacter() != '.' && lastCharacter() != '+' && lastCharacter() != '-' && lastCharacter() != '×' && lastCharacter() != '÷') {
            textViewValue.append("-");
            canOperators = false;
        } else {
            Toast.makeText(getApplicationContext(), R.string.operator_already_exists, Toast.LENGTH_LONG).show();
        }
    }

    public void delOperation() {
        if (textViewResult.getText().toString().equals("Неверное выражение!")) {
            deleteAll();
        }
        if (textViewValue.length() > 0) {
            String temporary = textViewValue.getText().toString();
            textViewValue.setText(temporary.substring(0, temporary.length() - 1));
        }
        if (!(lastCharacter() == '.') && textViewValue.length() > 0) {
            canOperate = false;
            doCalc();
        }
        if (textViewValue.getText().length() == 0) {
            textViewResult.setText("");
        }
        if (textViewValue.length() > 10) {
            textViewValue.setTextSize(35);
        } else {
            textViewValue.setTextSize(50);
        }
        canOperators = true;
    }

    public void multiplyOperation() {
        if (canOperators && lastCharacter() != '.' && lastCharacter() != '+' && lastCharacter() != '-' && lastCharacter() != '×' && lastCharacter() != '÷') {
            textViewValue.append("×");
            canOperators = false;
        } else {
            Toast.makeText(getApplicationContext(), R.string.operator_already_exists, Toast.LENGTH_LONG).show();
        }
    }

    public void divideOperation() {
        if (canOperators && lastCharacter() != '.' && lastCharacter() != '+' && lastCharacter() != '-' && lastCharacter() != '×' && lastCharacter() != '÷') {
            textViewValue.append("÷");
            canOperators = false;
        } else {
            Toast.makeText(getApplicationContext(), R.string.operator_already_exists, Toast.LENGTH_LONG).show();
        }
    }

    public void addOperation() {
        if (canOperators && lastCharacter() != '.' && lastCharacter() != '+' && lastCharacter() != '-' && lastCharacter() != '×' && lastCharacter() != '÷') {
            textViewValue.append("+");
            canOperators = false;
        } else {
            Toast.makeText(getApplicationContext(), R.string.operator_already_exists, Toast.LENGTH_LONG).show();
        }
    }

    public void onClickTurnOnNightMode(View view) {
        Intent switchToNightMode = new Intent(this, NightMode.class);
        startActivity(switchToNightMode);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(RESULT, textViewResult.getText().toString());
        outState.putString(VALUE, textViewValue.getText().toString());
        outState.putBoolean(HAS_DOT, hasDot);
        outState.putBoolean(HAS_OPERATION, canOperators);
        outState.putBoolean(CAN_OPERATE, canOperate);
        super.onSaveInstanceState(outState);
    }
}
