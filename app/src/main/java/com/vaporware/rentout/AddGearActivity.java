package com.vaporware.rentout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGearActivity extends AppCompatActivity {
//TODO: Complete this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final dbHandler db = new dbHandler(this);
        setContentView(R.layout.activity_add_gear);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button buttonAddGearToDb = (Button) findViewById(R.id.buttonNewGearToDb);
        final EditText textIdField = (EditText) findViewById(R.id.editGearId);
        final EditText textTypeField = (EditText) findViewById(R.id.editGearType);
        buttonAddGearToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textIdField.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(),"Id field is not empty",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(),"Id field is empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
