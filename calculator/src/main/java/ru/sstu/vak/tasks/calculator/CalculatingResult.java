package ru.sstu.vak.tasks.calculator;

import java.util.ArrayList;

/**
 * Created by Anton on 01.04.2018.
 */

public class CalculatingResult {
    private ArrayList<String> extras;
    private ArrayList<String> numbers;

    public CalculatingResult(ArrayList<String> numbers, ArrayList<String> extras) {
        this.extras = extras;
        this.numbers = numbers;
    }

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public ArrayList<String> getExtras() {

        return extras;
    }
}
