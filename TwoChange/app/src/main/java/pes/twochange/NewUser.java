package pes.twochange;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //Sign Up button + Pressed button listener
        Button signUpBtn = (Button)findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                EditText nameText = (EditText)findViewById(R.id.nameField);
                EditText mailText = (EditText)findViewById(R.id.mailField);
                EditText passText = (EditText)findViewById(R.id.passField);
                EditText repePassText = (EditText)findViewById(R.id.repeatPassField);
                String name = nameText.getText().toString().trim();
                String mail = mailText.getText().toString().trim();
                String pass = passText.getText().toString().trim();
                if (name.isEmpty() || nameText.getText() == null) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Fill in the name field", Toast.LENGTH_LONG).show();
                } else if (mail.isEmpty() || mailText.getText() == null) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Fill in the Email field", Toast.LENGTH_LONG).show();
                } else if (!mail.contains("@")) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Incorrect Email format", Toast.LENGTH_LONG).show();
                } else if (pass.isEmpty() || passText.getText() == null) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Fill in the password field", Toast.LENGTH_LONG).show();
                } else if (pass.isEmpty() || repePassText.getText() == null) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Fill in the repeated password field", Toast.LENGTH_LONG).show();
                } else if (!(repePassText.getText().toString().equals(passText.getText().toString()))) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Fill in the repeated password field", Toast.LENGTH_LONG).show();
                } else {
                    //ADRIIII BORRA LAS DOS LINEAS DE DEBAJO Y MANDA AQUI EL REGISTER
                    Intent mainMenuIntent = new Intent (getApplicationContext(), zWorking.class);
                    startActivity(mainMenuIntent);
                }
            }
        });

        //Matches both passwords in real time and changes foreground color
        final EditText repePassText = (EditText)findViewById(R.id.repeatPassField);
        repePassText.addTextChangedListener((new TextWatcher(){
            EditText passText = (EditText)findViewById(R.id.passField);
            public void afterTextChanged(Editable s) {
                if (!(repePassText.getText().toString().equals(passText.getText().toString()))) {
                    repePassText.setTextColor(Color.RED);
                } else {
                    repePassText.setTextColor(Color.BLACK);
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        }));
    }
}
