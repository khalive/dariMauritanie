package mr.example.darimaritanie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText edtEmailAddressReg ,edtFullNameReg , edtPasswordReg,edtDOB,edtPhoneNumber,edtBio;
    Button btnLoginReg , btnRegisterReg ;
    TextView txtDisplayInfoReg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);// R.layout.activity_register fait référence au layout pour l'inscription
        edtEmailAddressReg = findViewById(R.id.edtEmailAddressReg);
        edtFullNameReg = findViewById(R.id.edtFullnameReg);
        edtPasswordReg =  findViewById(R.id.edtPasswordReg);
        edtDOB = findViewById(R.id.edtDOBReg);
        edtPhoneNumber = findViewById(R.id.edtPhonNumberReg);
        edtBio = findViewById(R.id.edtBioReg);
        btnLoginReg = findViewById(R.id.btnLoginReg);
        btnRegisterReg = findViewById(R.id.btnRegisterReg);
        txtDisplayInfoReg = findViewById(R.id.txtDisplayInfReg);
        btnLoginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this ,MainActivity.class);
                startActivity(i);
            }
        });
        btnRegisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strFullname = edtFullNameReg.getText().toString();
                String strEmail = edtEmailAddressReg.getText().toString();
                String strPassword = edtPasswordReg.getText().toString();
                String strDOB = edtDOB.getText().toString();
                String strPhoneNumber = edtPhoneNumber.getText().toString();
                String strBio = edtBio.getText().toString();

                if (strFullname.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strDOB.isEmpty() || strPhoneNumber.isEmpty() || strBio.isEmpty()) {
                    Toast.makeText(Register.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
