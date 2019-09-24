package com.biedin.calculator;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule public final ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addDigits() {
        press(R.id.buttonNumberZero);
        press(R.id.buttonNumberOne);
        press(R.id.buttonNumberTwo);
        press(R.id.buttonNumberThree);
        press(R.id.buttonNumberFour);
        press(R.id.buttonNumberFive);
        press(R.id.buttonNumberSix);
        press(R.id.buttonNumberSeven);
        press(R.id.buttonNumberEight);
        press(R.id.buttonNumberNine);
        checkValue("0123456789");
    }

    @Test
    public void addTwoNumbers() {
        press(R.id.buttonNumberNine);
        press(R.id.buttonAdd);
        press(R.id.buttonNumberFive);
        checkValue("9+5");
        checkResult("14");
        longPress(R.id.buttonDel);
        checkResult("");
        checkValue("");
    }

    @Test
    public void divideTwoNumbers() {
        press(R.id.buttonNumberFive);
        press(R.id.buttonNumberZero);
        press(R.id.buttonDivide);
        press(R.id.buttonNumberFour);
        press(R.id.buttonNumberThree);
        checkValue("50÷43");
        checkResult("1.16279");
    }

    @Test
    public void divideOnZero() {
        press(R.id.buttonNumberNine);
        press(R.id.buttonDivide);
        press(R.id.buttonNumberZero);
        checkValue("9÷0");
        checkResult("Неверное выражение!");
    }

    @Test
    public void multiplyThreeNumbers() {
        press(R.id.buttonNumberNine);
        press(R.id.buttonNumberOne);
        press(R.id.buttonMultiply);
        press(R.id.buttonNumberZero);
        press(R.id.buttonNumberFour);
        checkValue("91×04");
        checkResult("364");
    }

    @Test
    public void changeThemeAndSubtract() {
        press(R.id.switchNightMode);
        press(R.id.buttonNumberFour);
        press(R.id.buttonNumberFour);
        press(R.id.buttonSubtract);
        press(R.id.buttonNumberFour);
        press(R.id.buttonNumberFive);
        checkValue("44-45");
        checkResult("-1");
    }

    private void press(int id) {
        onView(withId(id)).perform(click());
    }

    private void longPress(int id) {
        onView(withId(id)).perform(longClick());
    }

    private void checkValue(String desired) {
        onView(withId(R.id.textViewValue)).check(matches(withText(desired)));
    }

    private void checkResult(String desired) {
        onView(withId(R.id.textViewResult)).check(matches(withText(desired)));
    }
}