package ru.sstu.vak.tasks.calculator;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_NUM_LENGTH = 13;
    private static final int MAX_EXPRESSION_LENGTH = 99;

    private int currNumLength = 0;
    private int expressionLength = 0;

    private ArrayList<Operations> operations = new ArrayList<>();
    private ArrayList<String> numbers = new ArrayList<>();
    private ArrayList<String> extras = new ArrayList<>();


    private boolean fieldClear = true;
    private boolean bracketOpen = false;

    private String result;

    Animation anim;
    Button num0_btn;
    Button num1_btn;
    Button num2_btn;
    Button num3_btn;
    Button num4_btn;
    Button num5_btn;
    Button num6_btn;
    Button num7_btn;
    Button num8_btn;
    Button num9_btn;
    Button comma_btn;
    Button cancel_btn;
    Button sum_btn;
    Button sub_btn;
    Button div_btn;
    Button mult_btn;
    Button sqrt_btn;
    Button oneDivX_btn;
    Button plus_minus_btn;
    Button result_btn;
    TextView calculating_field;
    TextView pre_result_field;
    TextView cursor;
    CountDownTimer timer;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeTitle();
        setContentView(R.layout.activity_main);
        setActivitiesItems();
        anim = new AlphaAnimation(0.0f, 1.0f);
        cursorAnim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num0: {
                try {
                    numClick("0");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num1: {
                try {
                    numClick("1");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num2: {
                try {
                    numClick("2");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num3: {
                try {
                    numClick("3");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num4: {
                try {
                    numClick("4");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num5: {
                try {
                    numClick("5");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num6: {
                try {
                    numClick("6");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num7: {
                try {
                    numClick("7");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num8: {
                try {
                    numClick("8");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.num9: {
                try {
                    numClick("9");
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.comma: {
                try {
                    commaClick();
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.cancel: {
                cancelClick();
                break;
            }
            case R.id.sum: {
                try {
                    operationClick(Operations.Sum);
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.sub: {
                try {
                    operationClick(Operations.Sub);
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.div: {
                try {
                    operationClick(Operations.Div);
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.mult: {
                try {
                    operationClick(Operations.Mult);
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.sqrt: {
                try {
                    sqrtClick();
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.one_div_x: {
                try {
                    oneDivXClick();
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.plus_minus: {
                try {
                    plusminusClick();
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.result_btn: {
                try {
                    getResult(true);
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    public void getResult(boolean resBtn) {

        if (numbers.size() == 0) {
            //calculating_field.setText("0");
            if (resBtn) {
                pre_result_field.setText("");
            }
            return;
        } else if (this.numbers.size() == this.operations.size()) {
            //calculating_field.setText(getExpression());
            if (resBtn) {
                pre_result_field.setText("");
            }
            return;
        } else if (isRadicalHere()) {
            //calculating_field.setText(getExpression());
            if (resBtn) {
                pre_result_field.setText("");
            }
            return;
        } else if (isBracketHere()) {
            // calculating_field.setText(getExpression());
            if (resBtn) {
                pre_result_field.setText("");
            }
            return;
        }

        ArrayList<Operations> operations = (ArrayList<Operations>) this.operations.clone();
        ArrayList<String> extras = (ArrayList<String>) this.extras.clone();
        ArrayList<String> numbers = (ArrayList<String>) this.numbers.clone();
        //calcNumbers = (ArrayList<String>) this.numbers.clone();

        boolean multdivExist = true;

        CalculatingResult exR = executeExtra(numbers, extras);


        extras = exR.getExtras();
        numbers = exR.getNumbers();


        if (!isInputInRadicalDuring()) {
            this.extras = (ArrayList<String>) extras.clone();
            this.numbers = (ArrayList<String>) numbers.clone();
        }
        //calcNumbers = (ArrayList<String>) numbers.clone();

        for (int j = 0; j < 2; j++) {

            for (int i = 0; i < operations.size(); i++) {
                Operations op = operations.get(i);
                if (multdivExist && op == Operations.Mult || op == Operations.Div) {
                    double num1 = Double.parseDouble(numbers.get(i));
                    double num2 = Double.parseDouble(numbers.get(i + 1));
                    String res = getNormalNum(round(calculate(num1, num2, op)));
                    extras.remove(i);
                    numbers.remove(i);
                    numbers.remove(i);
                    numbers.add(i, res);
                    operations.remove(i);
                    i--;
                } else if (!multdivExist) {
                    double num1 = Double.parseDouble(numbers.get(i));
                    double num2 = Double.parseDouble(numbers.get(i + 1));
                    String res = getNormalNum(round(calculate(num1, num2, op)));
                    extras.remove(i);
                    numbers.remove(i);
                    numbers.remove(i);
                    numbers.add(i, res);
                    operations.remove(i);
                    i--;
                }
            }
            multdivExist = false;
        }
        result = numbers.get(0);
        if (resBtn) {
            bracketOpen = false;
            this.operations.clear();
            this.numbers.clear();
            this.extras.clear();
            this.numbers.add(result);
            this.extras.add("");
            currNumLength = result.length();
            expressionLength = result.length();
            calculating_field.setText(result);
            pre_result_field.setText("0");
        }
    }

    private CalculatingResult executeExtra(ArrayList<String> numbers, ArrayList<String> extras) {
        CalculatingResult calcR;
        for (int i = 0; i < extras.size(); i++) {
            calcR = extraCalculation(i, numbers, extras);
            extras = calcR.getExtras();
            numbers = calcR.getNumbers();
        }
        return new CalculatingResult(numbers, extras);
    }

    private CalculatingResult extraCalculation(int i, ArrayList<String> numbers, ArrayList<String> extras) {
        String normalNum = "";
        String extraItem = extras.get(i);
        double number = Double.parseDouble(numbers.get(i));
        if (!extraItem.equals("")) {
            switch (extraItem) {
                case "√": {
                    if (number < 0) {
                        throw new ArithmeticException("Корень из отрицательного числа не существует");
                    } else {
                        normalNum = getNormalNum(round(Math.sqrt(number)));
                        numbers.set(i, normalNum);
                        extras.set(i, "");
                    }
                    break;
                }
                case "+-": {
                    normalNum = getNormalNum(number * (-1));
                    numbers.set(i, normalNum);
                    extras.set(i, "");
                    break;
                }
                default:
                    throw new ArithmeticException("Неизвестная ошибка");
            }
        }
        return new CalculatingResult(numbers, extras);
    }

    public void numClick(String num) throws ArithmeticException {
        overflowTracking();
        if (numbers.size() == operations.size()) {
            if (num.equals("0") && canAddZero()) {
                currNumLength++;
                expressionLength++;

                numbers.add(num);
                extras.add("");

                prepareField();
                calculating_field.append(num);
            } else if (!num.equals("0")) {

                if (numbers.size() > 0 && numbers.size() > operations.size() && numbers.get(numbers.size() - 1).equals("0")) {
                    numbers.set(numbers.size() - 1, num);

                    calculating_field.setText(replaceLastSymbol(calculating_field.getText().toString(), num));
                } else {
                    currNumLength++;
                    expressionLength++;

                    numbers.add(num);
                    extras.add("");
                    prepareField();
                    calculating_field.append(num);
                }
            }
        } else {
            if (num.equals("0") && canAddZero()) {
                currNumLength++;
                expressionLength++;

                int lastEl = numbers.size() - 1;
                numbers.set(lastEl, numbers.get(lastEl) + num);

                prepareField();
                calculating_field.append(num);
            } else if (!num.equals("0")) {

                if (numbers.size() > 0 && numbers.size() > operations.size() && numbers.get(numbers.size() - 1).equals("0")) {
                    numbers.set(numbers.size() - 1, num);

                    calculating_field.setText(replaceLastSymbol(calculating_field.getText().toString(), num));
                } else {
                    currNumLength++;
                    expressionLength++;

                    int lastEl = numbers.size() - 1;
                    numbers.set(lastEl, numbers.get(lastEl) + num);
                    prepareField();
                    calculating_field.append(num);
                }
            }
        }

        if (!isDivZero()) {
            getResult(false);
            pre_result_field.setText(result);
        } else {
            pre_result_field.setText("");
        }

    }

    public void commaClick() throws ArithmeticException {
        overflowTracking();
        fieldClear = false;

        if (numbers.size() == 0) {
            currNumLength++;
            expressionLength++;
            numbers.add("0");
            extras.add("");
        } else if (numbers.size() == operations.size() || isRadicalHere() || isBracketHere()) {
            currNumLength++;
            expressionLength++;
            numClick("0");
        }
        if (canAddComma()) {
            int numLastEl = numbers.size() - 1;
            String lastEl = numbers.get(numLastEl);
            if (!isPointHere(lastEl)) {
                currNumLength++;
                expressionLength++;

                numbers.set(numLastEl, lastEl + ".");

                calculating_field.append(".");
            }
        }
    }

    public void operationClick(Operations op) throws ArithmeticException {
        currNumLength = 0;
        pre_result_field.setText("");

        if (canAddOp()) {
            expressionLength++;

            if (bracketOpen) {
                calculating_field.append(")");
                bracketOpen = false;
            }

            if (numbers.size() == operations.size()) {
                int lastEl = operations.size() - 1;
                operations.set(lastEl, op);

                calculating_field.setText(replaceLastSymbol(calculating_field.getText().toString(), op.getValue()));
            } else {
                operations.add(op);

                calculating_field.append(op.getValue());
            }
        }
    }

    public void sqrtClick() throws ArithmeticException {
        overflowTracking();
        if (extras.size() == 0 || !isRadicalHere() && !isBracketHere() && !isCommaHere()) {
            if (bracketOpen) {
                calculating_field.append(")");
                bracketOpen = false;
            }

            if (numbers.size() > operations.size()) {
                currNumLength++;
                expressionLength++;

                operations.add(Operations.Mult);
                calculating_field.append(Operations.Mult.getValue());
            }

            currNumLength++;
            expressionLength++;

            numbers.add("");
            extras.add("√");

            prepareField();
            calculating_field.append("√");
        }
    }

    public void oneDivXClick() throws ArithmeticException {
        overflowTracking();
        if (!isCommaHere()) {
            if (isRadicalHere()) {
                currNumLength++;
                expressionLength++;
                numClick("1");
            }
            if (!isBracketHere() && numbers.size() > operations.size()) {
                currNumLength++;
                expressionLength++;

                operations.add(Operations.Mult);
                calculating_field.append(Operations.Mult.getValue());
            }

            currNumLength += 2;
            expressionLength += 2;

            numClick("1");
            operationClick(Operations.Div);
        }
    }

    public void plusminusClick() throws ArithmeticException {
        if (isRadicalHere() || isInputInRadicalDuring()) {
            throw new ArithmeticException("Корень из отрицательного числа не существует");
        }
        if (numbers.size() > operations.size() && !numbers.get(numbers.size() - 1).equals("")) {
            int numLastEl = numbers.size() - 1;
            String lastEls = numbers.get(numLastEl);
            String possibleComma = "";
            if (getLastSymbol(lastEls).equals(".")) {
                possibleComma = ".";
            }

            double lastEld = Double.parseDouble(lastEls) * (-1);
            lastEls = getNormalNum(lastEld) + possibleComma;
            numbers.set(numLastEl, lastEls);

            if (lastEld < 0) {
                addedSign();
                bracketOpen = true;
            } else {
                removeAddedSign();
                bracketOpen = false;
            }

            getResult(false);
            pre_result_field.setText(result);
        } else if (numbers.size() > 0 && isBracketHere()) {
            numbers.remove(numbers.size() - 1);
            extras.remove(extras.size() - 1);

            bracketOpen = false;
            String text = calculating_field.getText().toString();
            if (text.equals("(-")) {
                fieldClear = true;
                calculating_field.setText("0");
            } else {
                calculating_field.setText(text.substring(0, text.length() - 2));
            }
        } else {
            numbers.add("");
            extras.add("+-");

            prepareField();
            String text = calculating_field.getText().toString();
            calculating_field.append("(-");
            bracketOpen = true;
        }
    }

    public void cancelClick() throws ArithmeticException {
        operations.clear();
        //calcNumbers.clear();
        numbers.clear();
        extras.clear();

        currNumLength = 0;
        expressionLength = 0;
        calculating_field.setText("\n0");
        pre_result_field.setText("0");
        fieldClear = true;
        bracketOpen = false;
        result = "0";
    }

    private boolean canAddZero() {
        if (numbers.size() == 0) {
            return false;
        } else {
            String numLastEl = numbers.get(numbers.size() - 1);
            String extrasLastEl = extras.get(extras.size() - 1);
            if (numbers.size() == 1 && !numbers.get(0).equals("0")) {
                return true;
            } else if (numbers.size() > 1 && !numLastEl.equals("0")) {
                return true;
            }
        }
        return false;
    }

    private boolean canAddComma() {
        if (numbers.size() > operations.size() && !isRadicalHere() && !isBracketHere()) {
            return true;
        }
        return false;
    }

    private boolean canAddOp() {
        if (numbers.size() == 0) {
            return false;
        }

        String extrasLastEl = extras.get(extras.size() - 1);
        if (!isCommaHere() && !isRadicalHere() &&
                !(extras.get(extras.size() - 1).equals("+-") &&
                        numbers.get(numbers.size() - 1).equals(""))) {
            return true;
        }
        return false;
    }

    private boolean isInputInRadicalDuring() {
        return numbers.size() > operations.size() && !numbers.get(numbers.size() - 1).equals("") && extras.get(extras.size() - 1).equals("√");
    }

    private boolean isCommaHere() {
        if (numbers.size() > 0 && getLastSymbol(numbers.get(numbers.size() - 1)).equals(".")) {
            return true;
        }
        return false;
    }

    private boolean isRadicalHere() {
        if (extras.size() == 0) {
            return false;
        }
        return extras.get(extras.size() - 1).equals("√") && numbers.get(numbers.size() - 1).equals("");
    }

    private boolean isBracketHere() {
        if (extras.size() == 0) {
            return false;
        }
        return extras.get(extras.size() - 1).equals("+-") && numbers.get(numbers.size() - 1).equals("");
    }

    private boolean isDivZero() {
        return numbers.size() > 0 && operations.size() > 0 && numbers.get(numbers.size() - 1).equals("0") && operations.get(operations.size() - 1) == Operations.Div;
    }

    private boolean isPointHere(String str) {
        return Pattern.compile("\\.").matcher(str).find() || Pattern.compile(",").matcher(str).find();
    }

    private double calculate(double num1, double num2, Operations op) throws ArithmeticException {
        switch (op) {
            case Sum: {
                return round(num1 + num2);
            }
            case Sub: {
                return round(num1 - num2);
            }
            case Mult: {
                return round(num1 * num2);
            }
            case Div: {
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на 0 невозможно");
                } else {
                    return round(num1 / num2);
                }
            }
            default:
                throw new ArithmeticException("Неизвестная ошибка");
        }
    }

    private String getNormalNum(double value) {
        int roundN = (int) Math.round(value);
        if (Math.abs(value - roundN) <= 0.000000001) {
            return Integer.toString(roundN);
        }
        return Double.toString(value);
    }

    private void overflowTracking() {
        if (currNumLength > MAX_NUM_LENGTH) {
            throw new ArithmeticException("Превышено максимальное число цифр");
        }
        if (expressionLength > MAX_EXPRESSION_LENGTH) {
            throw new ArithmeticException("Превышено максимальное число символов");
        }
    }


    private double round(double value) {
        double pow = Math.pow(10, 10);
        return Math.round(value * pow) / pow;
    }

    private String getLastSymbol(String str) {
        return !str.equals("") ? str.substring(str.length() - 1, str.length()) : "";
    }

    private int getArraySize(ArrayList<String> arr) {
        int k = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (!arr.get(i).equals("")) {
                k++;
            }
        }
        return k;
    }

    private String replaceLastSymbol(String str, String newSymbol) {
        return str.substring(0, str.length() - 1) + newSymbol;
    }

    private void prepareField() {
        if (fieldClear) {
            calculating_field.setText("");
            fieldClear = false;
        }
    }

    private void addedSign() {
        Pattern p = Pattern.compile("(.+[-+÷x√])(\\d+.?\\d{0,})");
        String text = calculating_field.getText().toString();
        Matcher m = p.matcher(text);

        if (m.matches()) {
            String firstPart = m.group(1);
            String secPart = m.group(2);
            calculating_field.setText(firstPart + "(-" + secPart);
        } else {
            calculating_field.setText("(-" + text);
        }


    }

    private void removeAddedSign() {
        Pattern p = Pattern.compile("(.+[0-9-+÷x√()]+)(\\(-)(\\d+.?\\d{0,})");
        String text = calculating_field.getText().toString();
        Matcher m = p.matcher(text);

        if (m.matches()) {
            String firstPart = m.group(1);
            String secPart = m.group(3);
            calculating_field.setText(firstPart + secPart);
        } else if (text.substring(0, 1).equals("-")) {
            calculating_field.setText(text.substring(1));
        } else {
            calculating_field.setText(text.substring(2));
        }
    }


    private void removeTitle() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setActivitiesItems() {
        num0_btn = (Button) findViewById(R.id.num0);
        num0_btn.setOnClickListener(this);
        num1_btn = (Button) findViewById(R.id.num1);
        num1_btn.setOnClickListener(this);
        num2_btn = (Button) findViewById(R.id.num2);
        num2_btn.setOnClickListener(this);
        num3_btn = (Button) findViewById(R.id.num3);
        num3_btn.setOnClickListener(this);
        num4_btn = (Button) findViewById(R.id.num4);
        num4_btn.setOnClickListener(this);
        num5_btn = (Button) findViewById(R.id.num5);
        num5_btn.setOnClickListener(this);
        num6_btn = (Button) findViewById(R.id.num6);
        num6_btn.setOnClickListener(this);
        num7_btn = (Button) findViewById(R.id.num7);
        num7_btn.setOnClickListener(this);
        num8_btn = (Button) findViewById(R.id.num8);
        num8_btn.setOnClickListener(this);
        num9_btn = (Button) findViewById(R.id.num9);
        num9_btn.setOnClickListener(this);
        comma_btn = (Button) findViewById(R.id.comma);
        comma_btn.setOnClickListener(this);
        cancel_btn = (Button) findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(this);
        sum_btn = (Button) findViewById(R.id.sum);
        sum_btn.setOnClickListener(this);
        sub_btn = (Button) findViewById(R.id.sub);
        sub_btn.setOnClickListener(this);
        div_btn = (Button) findViewById(R.id.div);
        div_btn.setOnClickListener(this);
        mult_btn = (Button) findViewById(R.id.mult);
        mult_btn.setOnClickListener(this);
        sqrt_btn = (Button) findViewById(R.id.sqrt);
        sqrt_btn.setOnClickListener(this);
        oneDivX_btn = (Button) findViewById(R.id.one_div_x);
        oneDivX_btn.setOnClickListener(this);
        plus_minus_btn = (Button) findViewById(R.id.plus_minus);
        plus_minus_btn.setOnClickListener(this);
        result_btn = (Button) findViewById(R.id.result_btn);
        result_btn.setOnClickListener(this);
        calculating_field = (TextView) findViewById(R.id.calculating);
        pre_result_field = (TextView) findViewById(R.id.pre_result);
        cursor = (TextView) findViewById(R.id.cursor);
        calculating_field.setMovementMethod(new ScrollingMovementMethod());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void cursorAnim() {
//        calculating_field.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                if (timer != null) {
//                    timer.cancel();
//                }
//                cursor.setText("");
//                timer = new CountDownTimer(1300, 1300) {
//                    public void onTick(long millisUntilFinished) {
//                    }
//
//                    public void onFinish() {
//                        cursor.setText("|");
//                    }
//                }.start();
//            }
//        });
        anim.setDuration(300);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        cursor.startAnimation(anim);
    }
}

