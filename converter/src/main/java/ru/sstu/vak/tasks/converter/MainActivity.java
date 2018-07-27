package ru.sstu.vak.tasks.converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_NUM_LENGTH = 15;

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
    Button del_btn;
    Switch switch_lang;
    Spinner spinner_from;
    Spinner spinner_to;
    TextView from_field;
    TextView to_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeTitle();
        setContentView(R.layout.activity_main);
        setActivitiesItems();

        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num0: {
                try {
                    if (canAddZero()) {
                        numClick("0");
                    }
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
            case R.id.del: {
                try {
                    delClick();
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.switch_lang: {
                try {
                    switchClick();
                } catch (ArithmeticException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    private void refreshValues() {
        double res = calculate(Double.parseDouble(from_field.getText().toString()),
                getUnit(spinner_from.getSelectedItemPosition()), getUnit(spinner_to.getSelectedItemPosition()));
        to_text.setText(getNormalNum(round(res)));
    }

    private double calculate(double value, Units from, Units to) throws ArithmeticException {
        if (from == Units.Meters && to == Units.Centimeters) {
            return value * 100;
        }
        if (from == Units.Meters && to == Units.Feet) {
            return value * 3.28084;
        }
        if (from == Units.Meters && to == Units.Kilometers) {
            return value * 0.001;
        }
        if (from == Units.Meters && to == Units.Miles) {
            return value * 0.00062137119223733;
        }
        if (from == Units.Meters && to == Units.Inches) {
            return value * 39.3701;
        }
        if (from == Units.Centimeters && to == Units.Meters) {
            return value * 0.01;
        }
        if (from == Units.Centimeters && to == Units.Kilometers) {
            return value * 0.00001;
        }
        if (from == Units.Centimeters && to == Units.Feet) {
            return value * 0.0328084;
        }
        if (from == Units.Centimeters && to == Units.Miles) {
            return value * 0.00000621371192;
        }
        if (from == Units.Centimeters && to == Units.Inches) {
            return value * 0.39370078;
        }
        if (from == Units.Kilometers && to == Units.Meters) {
            return value * 1000;
        }
        if (from == Units.Kilometers && to == Units.Centimeters) {
            return value * 100000;
        }
        if (from == Units.Kilometers && to == Units.Feet) {
            return value * 3280.84;
        }
        if (from == Units.Kilometers && to == Units.Miles) {
            return value * 0.621371;
        }
        if (from == Units.Kilometers && to == Units.Inches) {
            return value * 39370.07874;
        }
        if (from == Units.Feet && to == Units.Meters) {
            return value * 0.3048;
        }
        if (from == Units.Feet && to == Units.Kilometers) {
            return value * 0.0003048;
        }
        if (from == Units.Feet && to == Units.Centimeters) {
            return value * 30.48;
        }
        if (from == Units.Feet && to == Units.Miles) {
            return value * 0.000189394;
        }
        if (from == Units.Feet && to == Units.Inches) {
            return value * 12;
        }
        if (from == Units.Miles && to == Units.Meters) {
            return value * 1609.34;
        }
        if (from == Units.Miles && to == Units.Kilometers) {
            return value * 1.609344;
        }
        if (from == Units.Miles && to == Units.Centimeters) {
            return value * 160934.4;
        }
        if (from == Units.Miles && to == Units.Feet) {
            return value * 5280;
        }
        if (from == Units.Miles && to == Units.Inches) {
            return value * 63360;
        }
        if (from == Units.Inches && to == Units.Meters) {
            return value * 0.0254;
        }
        if (from == Units.Inches && to == Units.Kilometers) {
            return value * 2.54e-5;
        }
        if (from == Units.Inches && to == Units.Centimeters) {
            return value * 2.54;
        }
        if (from == Units.Inches && to == Units.Feet) {
            return value * 0.0833333333;
        }
        if (from == Units.Inches && to == Units.Miles) {
            return value * 1.57828282;
        }
        return value;
    }

    private Units getUnit(int position) {
        switch (position) {
            case (0): {
                return Units.Meters;
            }
            case (1): {
                return Units.Feet;
            }
            case (2): {
                return Units.Kilometers;
            }
            case (3): {
                return Units.Miles;
            }
            case (4): {
                return Units.Centimeters;
            }
            case (5): {
                return Units.Inches;
            }
            default: {
                return null;
            }
        }
    }

    public void numClick(String num) throws ArithmeticException {
        String text = from_field.getText().toString();
        if (text.length() == 1 && getLastSymbol(from_field.getText().toString()).equals("0")) {
            from_field.setText("");
        }
        overflowTracking();
        from_field.append(num);
        refreshValues();
    }

    public void commaClick() throws ArithmeticException {
        if (canAddComma()) {
            overflowTracking();
            from_field.append(".");
        }
    }

    public void delClick() throws ArithmeticException {
        String text = from_field.getText().toString();
        if (text.length() > 0) {
            from_field.setText(text.substring(0, text.length() - 1));
        }
        text = from_field.getText().toString();
        if (text.length() == 0) {
            from_field.setText("0");
        }
        refreshValues();
    }

    public void switchClick() throws ArithmeticException {
        String[] elements;
        ArrayAdapter<String> adapter;
        if (switch_lang.isChecked()) {
            elements = getResources().getStringArray(R.array.ru_values);
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, elements);
            switch_lang.setText(getResources().getString(R.string.ru_ru));
            spinner_from.setAdapter(adapter);
            spinner_to.setAdapter(adapter);
        } else {
            elements = getResources().getStringArray(R.array.en_values);
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, elements);
            switch_lang.setText(getResources().getString(R.string.ru_en));
            spinner_from.setAdapter(adapter);
            spinner_to.setAdapter(adapter);
        }
    }

    private boolean canAddComma() {
        String text = from_field.getText().toString();
        Pattern p = Pattern.compile("\\.");
        Matcher m = p.matcher(text);
        return !m.find() && !getLastSymbol(from_field.getText().toString()).equals(".");
    }

    private boolean canAddZero() {
        String text = from_field.getText().toString();
        Pattern p = Pattern.compile("0{0,1}\\.|[1-9]+0{0,}\\.?\\d{0,}");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    private String getNormalNum(double value) {
        int roundN = (int) Math.round(value);
        if (Math.abs(value - roundN) <= 0.000000001) {
            return Integer.toString(roundN);
        }
        return Double.toString(value);
    }

    private double round(double value) {
        double pow = Math.pow(10, 10);
        return Math.round(value * pow) / pow;
    }

    private String getLastSymbol(String str) {
        return !str.equals("") ? str.substring(str.length() - 1, str.length()) : "";
    }

    private void overflowTracking() {
        if (from_field.getText().length() > MAX_NUM_LENGTH) {
            throw new ArithmeticException("Превышено максимальное число цифр");
        }
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
        del_btn = (Button) findViewById(R.id.del);
        del_btn.setOnClickListener(this);
        switch_lang = (Switch) findViewById(R.id.switch_lang);
        switch_lang.setOnClickListener(this);

        spinner_from = (Spinner) findViewById(R.id.spinner_from);
        spinner_to = (Spinner) findViewById(R.id.spinner_to);
        from_field = (TextView) findViewById(R.id.from_field);
        to_text = (TextView) findViewById(R.id.to_text);
    }

    private void removeTitle() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
