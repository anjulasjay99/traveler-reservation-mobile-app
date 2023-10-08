package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelerreservation.managers.ContextManager;
import com.example.travelerreservation.managers.DatabaseManager;
import com.example.travelerreservation.managers.SignUpManager;
import com.example.travelerreservation.managers.UpdateProfileManager;
import com.example.travelerreservation.models.UserEntity;

import java.util.List;

public class UpdateProfile extends AppCompatActivity {

    EditText nicEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText dateOfBirthEditText;
    EditText phoneEditText;
    EditText emailEditText;
    Button updateBtn;

    UpdateProfileManager updateProfileManager;

    DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());
        this.updateProfileManager = UpdateProfileManager.getInstance();

        this.databaseManager = DatabaseManager.getInstance();

        this.nicEditText = findViewById(R.id.updateNicEditText);
        this.firstNameEditText = findViewById(R.id.updateFirstNameEditText);
        this.lastNameEditText = findViewById(R.id.updateLastNameEditText);
        this.dateOfBirthEditText = findViewById(R.id.updateDateOfBirthEditText);
        this.phoneEditText = findViewById(R.id.updatePhoneEditText);
        this.emailEditText = findViewById(R.id.updateEmailEditText);
        this.updateBtn = findViewById(R.id.updateBtn);
        this.updateBtn.setOnClickListener(view -> update());

        this.getUserDetails();
    }

    private void update() {
        String nic = this.nicEditText.getText().toString();
        String firstName = this.firstNameEditText.getText().toString();
        String lastName = this.lastNameEditText.getText().toString();
        String dateOfBirth = this.dateOfBirthEditText.getText().toString();
        String phoneNo = this.phoneEditText.getText().toString();
        String email = this.emailEditText.getText().toString();


        if (!nic.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !phoneNo.isEmpty() && !dateOfBirth.isEmpty() && !email.isEmpty()) {

                updateProfileManager.updateProfile(nic, firstName, lastName, dateOfBirth, Integer.parseInt(phoneNo), email, () -> handleUpdateSuccessful(),
                        error -> handleUpdateFailed(error)
                );

        } else {
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
        }
    }

    private void handleUpdateSuccessful(){
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
    }

    private void handleUpdateFailed(String error){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void getUserDetails() {
        new Thread(() -> {
            List<UserEntity> users = databaseManager.db().userDao().getAll();
            Log.i("USERS", String.valueOf(users.get(0).nic));
            this.nicEditText.setText(users.get(0).nic.toString());
            this.firstNameEditText.setText(users.get(0).firstName.toString());
            this.lastNameEditText.setText(users.get(0).lastName.toString());
            this.phoneEditText.setText(String.valueOf(users.get(0).phoneNo));
            this.emailEditText.setText(users.get(0).email.toString());
            this.dateOfBirthEditText.setText(users.get(0).dateOfBirth.toString());


        }).start();
    }
}