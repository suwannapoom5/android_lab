package com.example.bmr;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bmr.DisplayMessageActivity;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.bmr.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.planets_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void calculateBMR(View view) {
        EditText weightEditText = findViewById(R.id.weightEditText);
        EditText heightEditText = findViewById(R.id.heightEditText);
        EditText ageEditText = findViewById(R.id.edit_message);
        Spinner activitySpinner = findViewById(R.id.planets_spinner);

        try {
            double weight = Double.parseDouble(weightEditText.getText().toString());
            double height = Double.parseDouble(heightEditText.getText().toString());
            int age = Integer.parseInt(ageEditText.getText().toString());

            double BMR;

            RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            RadioButton genderRadioButton = findViewById(selectedGenderId);

            if (genderRadioButton.getText().toString().equalsIgnoreCase("male")) {
                BMR = (10 * weight) + (6.25 * height) + (5 * age) + 5;
            } else {
                BMR = (10 * weight) + (6.25 * height) + (5 * age) - 161;
            }


            double activityMultiplier = 1.2;
            String activity = activitySpinner.getSelectedItem().toString();

            switch (activity.toLowerCase()) {
                case "1to2 days a week":
                case "ออกกําลังกาย 1-2 วันต่อสัปดาห์":
                    activityMultiplier = 1.375;
                    break;
                case "3to5 days a week":
                case "ออกกําลังกาย 3-5 วันต่อสัปดาห์":
                    activityMultiplier = 1.55;
                    break;
                case "6to7 days a week":
                case "ออกกําลังกาย 6-7 วันต่อสัปดาห์":
                    activityMultiplier = 1.725;
                    break;
                case "all the time":
                case "นักกีฬาอาชีพ":
                    activityMultiplier = 1.9;
                    break;
            }


            BMR *= activityMultiplier;

            String resultMessage = getString(R.string.result_message, BMR);
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra(EXTRA_MESSAGE, resultMessage);

            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid values for weight, height, and age", Toast.LENGTH_SHORT).show();
        }
    }
}