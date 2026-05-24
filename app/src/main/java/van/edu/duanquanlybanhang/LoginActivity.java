package van.edu.duanquanlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtEmail, edtPassword;
    Button btnLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {

            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(email.isEmpty()){
                edtEmail.setError("Nhập email");
                return;
            }

            if(password.isEmpty()){
                edtPassword.setError("Nhập mật khẩu");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if(task.isSuccessful()){

                            Toast.makeText(this,
                                    "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent =
                                    new Intent(LoginActivity.this,
                                            MainActivity.class);

                            startActivity(intent);

                            finish();

                        }else{

                            Toast.makeText(this,
                                    "Sai email hoặc mật khẩu",
                                    Toast.LENGTH_SHORT).show();
                        }

                    });

        });

    }
}