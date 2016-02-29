package com.vaporware.rentout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddGearActivity extends AppCompatActivity {
//TODO: Figure out a better data model for equipment
    //Consider just picking a single store type (Ski shop)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final dbHandler db = new dbHandler(this);
        setContentView(R.layout.activity_add_gear);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Grab the objects from the xml
        Button addItem = (Button) findViewById(R.id.button_add_item);


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasSpinnerData()) { //Because right now the spinner is the only 'required' field
                 db.addGear(buildEquipmentObject());
                } else {
                    Toast.makeText(getApplicationContext(),"Please select a gear type.",Toast.LENGTH_LONG).show();
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

    private EquipmentOuterClass.Equipment buildEquipmentObject() {

        EquipmentOuterClass.Equipment.Builder builder = EquipmentOuterClass.Equipment.newBuilder();
        Spinner typeSpinner = (Spinner) findViewById(R.id.spinner_type);
        builder.setType(EquipmentOuterClass.Equipment.gearType
                .valueOf(typeSpinner.getSelectedItemPosition())); //I wonder if this will work.
        EditText sizeEdit = (EditText) findViewById(R.id.edit_size);
        String size = String.valueOf(sizeEdit.getText());
        builder.setSize(size); //build it explicitly, then it makes more sense.
        EditText stockEdit = (EditText) findViewById(R.id.edit_stock_num);
        String stockNum = String.valueOf(stockEdit.getText());
        builder.setStockNum(stockNum);



        return builder.build();
    }

    private boolean hasSpinnerData() {
        Spinner typeSpinner = (Spinner) findViewById(R.id.spinner_type);
        if (typeSpinner.getSelectedItemPosition() == 0) return false;

        return true;
    }
}
