package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

    String password;

    UpdateProfileManager updateProfileManager;

    DatabaseManager databaseManager;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

    //Validate detials and update profile
    private void update() {
        String nic = this.nicEditText.getText().toString();
        String firstName = this.firstNameEditText.getText().toString();
        String lastName = this.lastNameEditText.getText().toString();
        String dateOfBirth = this.dateOfBirthEditText.getText().toString();
        String phoneNo = this.phoneEditText.getText().toString();
        String email = this.emailEditText.getText().toString();


        if (!nic.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !phoneNo.isEmpty() && !dateOfBirth.isEmpty() && !email.isEmpty()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
                updateProfileManager.updateProfile(nic, firstName, lastName, dateOfBirth, Integer.parseInt(phoneNo), email, this.password, () -> handleUpdateSuccessful(),
                        error -> handleUpdateFailed(error)
                );

        } else {
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
        }
    }

    //Called if update succeeded
    private void handleUpdateSuccessful(){
        progressDialog.dismiss();
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
    }

    //Called if update failed
    private void handleUpdateFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    //Get logged in user's details from db
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
            this.password = users.get(0).password;

        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}