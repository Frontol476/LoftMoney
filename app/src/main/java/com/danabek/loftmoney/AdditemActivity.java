package com.danabek.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdditemActivity extends AppCompatActivity {

    public final static String KEY_NAME = "name";
    public final static String KEY_PRICE = "price";

    private EditText name;
    private EditText price;
    private Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addbtn = findViewById(R.id.add_btn);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                String nameText = name.getText().toString();
                String priceText = price.getText().toString();
                intent.putExtra(KEY_NAME,nameText);
                intent.putExtra(KEY_PRICE,priceText);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addbtn.setEnabled(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(price.getText()));
            }
        };
        name.addTextChangedListener(textWatcher);
        price.addTextChangedListener(textWatcher);

    }
}
