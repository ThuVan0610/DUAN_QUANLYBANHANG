package van.edu.duan_quanlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity{
    TextInputEditText edtStore, edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtStore = findViewById(R.id.edtStore);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String store = edtStore.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(store)){
                    edtStore.setError("Nhập tên gian hàng");
                    return;
                }

                if(TextUtils.isEmpty(username)){
                    edtUsername.setError("Nhập tên đăng nhập");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    edtPassword.setError("Nhập mật khẩu");
                    return;
                }

                // Demo đăng nhập
                if(username.equals("admin") && password.equals("123456")){

                    Toast.makeText(LoginActivity.this,
                            "Đăng nhập thành công",
                            Toast.LENGTH_SHORT).show();

                    Intent intent =
                            new Intent(LoginActivity.this,
                                    MainActivity.class);

                    startActivity(intent);

                } else {

                    Toast.makeText(LoginActivity.this,
                            "Sai tài khoản hoặc mật khẩu",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
