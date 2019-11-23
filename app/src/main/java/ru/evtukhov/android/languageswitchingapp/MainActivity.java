package ru.evtukhov.android.languageswitchingapp;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner language;
    private Spinner colors;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        initViews();
        initSpinnerLanguage();
    }

    private void initViews() {
        language = findViewById(R.id.spinner);
        colors = findViewById(R.id.spinnerColor);
        ok = findViewById(R.id.button);
    }

    @IntDef({Language.RUSSIAN, Language.ENGLISH})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Language {
        int RUSSIAN = 0;
        int ENGLISH = 1;
    }

    @IntDef({Color.GREEN, Color.BLACK, Color.BLUE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Color {
        int GREEN = 0;
        int BLACK = 1;
        int BLUE = 2;
    }

    private void initSpinnerLanguage() {
        ArrayAdapter<CharSequence> spinnerLanguage = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        spinnerLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(spinnerLanguage);
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                initSpinnerColors(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerColors(final int languageNumber) {
        ArrayAdapter<CharSequence> spinnerColors = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        spinnerColors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colors.setAdapter(spinnerColors);
        colors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int color, long id) {
                btnOk(languageNumber, color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void btnOk(@Language final int lang, @Color final int color) {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = setLocale(lang);
                setLanguage(locale);
                switch (color) {
                    case Color.GREEN:
                        Utils.changeToTheme(MainActivity.this, Utils.THEME_GREEN);
                        break;
                    case Color.BLACK:
                        Utils.changeToTheme(MainActivity.this, Utils.THEME_BLACK);
                        break;
                    case Color.BLUE:
                        Utils.changeToTheme(MainActivity.this, Utils.THEME_BLUE);
                        break;
                }
            }
        });
    }

    private Locale setLocale(@Language final int lang) {
        String language = "";
        switch (lang) {
            case Language.RUSSIAN:
                language = "ru";
                break;
            case Language.ENGLISH:
                language = "en";
                break;
        }
        return new Locale(language);
    }

    private void setLanguage(final Locale language) {
        Configuration configuration = new Configuration();
        configuration.setLocale(language);
        getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }
}
