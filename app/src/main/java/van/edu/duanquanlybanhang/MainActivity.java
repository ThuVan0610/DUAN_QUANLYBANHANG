package van.edu.duanquanlybanhang;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ÁNH XẠ
        bottomNav = findViewById(R.id.bottomNav);

        // CHỌN HOME
        bottomNav.setSelectedItemId(R.id.nav_home);

        // CLICK MENU
        bottomNav.setOnItemSelectedListener(item -> {

            // HOME
            if (item.getItemId() == R.id.nav_home) {

                return true;
            }

            // CHỌN BÀN
            else if (item.getItemId() == R.id.nav_product) {

                Intent intent =
                        new Intent(
                                MainActivity.this,
                                TableActivity.class);

                startActivity(intent);

                return true;
            }

            // QUẢN LÝ
            else if (item.getItemId() == R.id.nav_order) {

                Intent intent =
                        new Intent(
                                MainActivity.this,
                                ManagerActivity.class);

                startActivity(intent);

                return true;
            }

            // TÀI KHOẢN
            else if (item.getItemId() == R.id.nav_profile) {

                Intent intent =
                        new Intent(
                                MainActivity.this,
                                AccountActivity.class);

                startActivity(intent);

                return true;
            }

            return false;
        });
    }
}