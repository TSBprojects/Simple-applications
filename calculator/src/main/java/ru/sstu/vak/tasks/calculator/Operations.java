package ru.sstu.vak.tasks.calculator;

/**
 * Created by Anton on 30.03.2018.
 */

public enum Operations {
    Sum,
    Sub,
    Div,
    Mult;

    public String getValue() {
        switch (this) {
            case Sum: {
                return "+";
            }
            case Sub: {
                return "-";
            }
            case Mult: {
                return "x";
            }
            case Div: {
                return "÷";
            }
            default:
                return "";
        }
    }
}
