package com.example.gasmyr.isbusy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gasmyr.isbusy.utils.Constants;

public class MessageSettings extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText smsMessage, callMessage;
    private Button saveButton;
    private ImageButton clearButton, editButton;
    private CheckBox isSmsResponder, isCallResponder;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        saveButton = (Button) findViewById(R.id.save);
        clearButton = (ImageButton) findViewById(R.id.clear);
        editButton = (ImageButton) findViewById(R.id.edit);
        smsMessage = (EditText) findViewById(R.id.smsMessage);
        callMessage = (EditText) findViewById(R.id.callMessage);
        isSmsResponder = (CheckBox) findViewById(R.id.smsCheckBox);
        isCallResponder = (CheckBox) findViewById(R.id.callCheckBox);
        radioGroup = (RadioGroup) findViewById(R.id.radioGround);
        init();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                editor = sharedPreferences.edit();
                if (checkedId == R.id.mtm) {
                    editor.putString(Constants.APPLICATION_USER_SIM_OPERATOR, Constants.APPLICATION_OPERATOR_MTN);
                    Toast.makeText(getApplicationContext(), "MTN", Toast.LENGTH_LONG).show();
                } else if (checkedId == R.id.orange) {
                    editor.putString(Constants.APPLICATION_USER_SIM_OPERATOR, Constants.APPLICATION_OPERATOR_ORANGE);
                } else if (checkedId == R.id.nexttel) {
                    editor.putString(Constants.APPLICATION_USER_SIM_OPERATOR, Constants.APPLICATION_OPERATOR_NEXTTEL);
                } else {
                    editor.putString(Constants.APPLICATION_USER_SIM_OPERATOR, Constants.APPLICATION_OPERATOR_ALL);
                }
                editor.commit();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageForSms = smsMessage.getText().toString();
                String messageForCall = callMessage.getText().toString();
                if (!messageForSms.trim().isEmpty() && !messageForCall.trim().isEmpty()) {
                    editor = sharedPreferences.edit();
                    editor.putBoolean(Constants.APPLICATION_SMS_RESPONDER_IS_ACTIVATE, isSmsResponder.isChecked());
                    editor.putBoolean(Constants.APPLICATION_CALL_RESPONDER_IS_ACTIVATE, isCallResponder.isChecked());
                    editor.putString(Constants.APPLICATION_SMS_RESPONDER_MESSAGE, messageForSms);
                    editor.putString(Constants.APPLICATION_CALL_RESPONDER_MESSAGE, messageForCall);
                    editor.commit();
                } else {
                    smsMessage.setText(sharedPreferences.getString(
                            Constants.APPLICATION_SMS_RESPONDER_MESSAGE, getResources().getString(R.string.defaultSmsMessage)));
                    callMessage.setText(sharedPreferences.getString(
                            Constants.APPLICATION_CALL_RESPONDER_MESSAGE, getResources().getString(R.string.defaultCallMessage)));
                }
            Toast.makeText(getApplicationContext(), R.string.saveSettingsInfo,Toast.LENGTH_LONG).show();
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsMessage.setFocusable(true);
                smsMessage.setCursorVisible(true);
                smsMessage.setText("");
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsMessage.setFocusable(true);
                smsMessage.setCursorVisible(true);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getResources().getString(R.string.author), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        sharedPreferences = getSharedPreferences(Constants.APPLICATION_SHARE_PREF, MODE_PRIVATE);
        isSmsResponder.setChecked(
                sharedPreferences.getBoolean(Constants.APPLICATION_SMS_RESPONDER_IS_ACTIVATE, false));
        isCallResponder.setChecked(
                sharedPreferences.getBoolean(Constants.APPLICATION_CALL_RESPONDER_IS_ACTIVATE, false));
        smsMessage.setText(sharedPreferences.getString(
                Constants.APPLICATION_SMS_RESPONDER_MESSAGE, getResources().getString(R.string.defaultSmsMessage)));
        callMessage.setText(sharedPreferences.getString(
                Constants.APPLICATION_CALL_RESPONDER_MESSAGE, getResources().getString(R.string.defaultCallMessage)));
        String operator = sharedPreferences.getString(Constants.APPLICATION_USER_SIM_OPERATOR, Constants.APPLICATION_OPERATOR_ALL);
        if (operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_MTN)) {
            radioGroup.check(R.id.mtm);
        } else if (operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_ORANGE)) {
            radioGroup.check(R.id.orange);
        } else if (operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_NEXTTEL)) {
            radioGroup.check(R.id.nexttel);
        } else {
            radioGroup.check(R.id.all);
        }
    }

}
