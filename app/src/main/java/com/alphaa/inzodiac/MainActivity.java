package com.alphaa.inzodiac;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.LookingSpinnerAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_filter);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Private");
        arrayList.add("Group");
        arrayList.add("Both");

        Spinner spinner  = findViewById(R.id.looksp);
        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(this, arrayList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.setVisibility(View.GONE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }



    
}