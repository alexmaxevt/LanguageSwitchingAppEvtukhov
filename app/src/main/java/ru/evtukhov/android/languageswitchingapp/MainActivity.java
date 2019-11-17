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
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initSpinnerLanguage();
    }

    private void initViews () {
        language = findViewById(R.id.spinner);
        ok = findViewById(R.id.button);
    }
    @IntDef({Language.RUSSIAN, Language.ENGLISH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Language {
        int RUSSIAN = 0;
        int ENGLISH = 1;
    }

    private void initSpinnerLanguage () {
        ArrayAdapter<CharSequence> spinnerLanguage = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        spinnerLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(spinnerLanguage);
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnOk(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void btnOk(@Language final int position) {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case Language.RUSSIAN:
                        Locale ru = new Locale("ru");
                        Configuration configRU = new Configuration();
                        configRU.setLocale(ru);
                        getResources().updateConfiguration(configRU, getBaseContext().getResources().getDisplayMetrics());
                        recreate();
                        break;
                    case Language.ENGLISH:
                        Locale en = new Locale("en");
                        Configuration configEN = new Configuration();
                        configEN.setLocale(en);
                        getResources().updateConfiguration(configEN, getBaseContext().getResources().getDisplayMetrics());
                        recreate();
                        break;
                }
            }
        });
    }
}
