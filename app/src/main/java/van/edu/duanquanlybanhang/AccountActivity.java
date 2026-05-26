package van.edu.duanquanlybanhang;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class AccountActivity extends AppCompatActivity {

    MaterialCardView layoutTable;
    MaterialCardView layoutHistory;
    MaterialCardView layoutLogout;
    MaterialCardView layoutReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // ÁNH XẠ
        layoutTable = findViewById(R.id.layoutTable);
        layoutHistory = findViewById(R.id.layoutHistory);
        layoutReport = findViewById(R.id.layoutReport);
        layoutLogout = findViewById(R.id.layoutLogout);

        // ======================
        // CHỌN BÀN
        // ======================
        layoutTable.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AccountActivity.this,
                    TableActivity.class
            );
            startActivity(intent);
        });

        // ======================
        // LỊCH SỬ ĐƠN HÀNG
        // ======================
        layoutHistory.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AccountActivity.this,
                    HistoryActivity.class
            );
            startActivity(intent);
        });

        // ======================
        // BÁO CÁO / QUẢN LÝ
        // ======================
        layoutReport.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AccountActivity.this,
                    ManagerActivity.class
            );
            startActivity(intent);
        });

        // ======================
        // ĐĂNG XUẤT
        // ======================
        layoutLogout.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AccountActivity.this,
                    LoginActivity.class
            );
            startActivity(intent);
            finish();
        });
    }
}