package van.edu.duanquanlybanhang;
import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    Button btnTable1, btnTable2;
    static final int REQUEST_TABLE1 = 1;

    static final int REQUEST_TABLE2 = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNav = findViewById(R.id.bottomNav);

        drawerLayout =
                findViewById(R.id.drawerLayout);

        navigationView =
                findViewById(R.id.navigationView);

        btnTable1.setOnClickListener(v -> {

            // ĐỔI MÀU ĐỎ
            btnTable1.setBackgroundColor(
                    Color.RED);

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            OrderActivity.class);

            intent.putExtra(
                    "table",
                    "Bàn 1");

            startActivityForResult(
                    intent,
                    REQUEST_TABLE1);
        });

        btnTable2.setOnClickListener(v -> {

            btnTable2.setBackgroundColor(
                    Color.RED);

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            OrderActivity.class);

            intent.putExtra(
                    "table",
                    "Bàn 2");

            startActivityForResult(
                    intent,
                    REQUEST_TABLE2);
        });

        // CLICK BÀN 1
        btnTable1.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            OrderActivity.class);

            intent.putExtra(
                    "table",
                    "Bàn 1");

            startActivity(intent);
        });

        // CLICK BÀN 2
        btnTable2.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            OrderActivity.class);

            intent.putExtra(
                    "table",
                    "Bàn 2");

            startActivity(intent);
        });

        // MENU DƯỚI
        bottomNav.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_product){

                startActivity(
                        new Intent(
                                MainActivity.this,
                                ProductActivity.class));

                return true;
            }

            else if(item.getItemId() == R.id.nav_home){

                return true;
            }

            return false;
        });

        // SIDEBAR MENU
        navigationView.setNavigationItemSelectedListener(item -> {

            // DASHBOARD
            if(item.getItemId()
                    == R.id.menuDashboard){

                startActivity(
                        new Intent(
                                MainActivity.this,
                                DashboardActivity.class));
            }

            // HÓA ĐƠN
            else if(item.getItemId()
                    == R.id.menuBills){

                startActivity(
                        new Intent(
                                MainActivity.this,
                                BillActivity.class));
            }

            // ĐĂNG XUẤT
            else if(item.getItemId()
                    == R.id.menuLogout){

                finish();
            }
            // DARK MODE
            else if(item.getItemId()
                    == R.id.menuDark){

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);
            }
            else if(item.getItemId()
                    == R.id.menuLight){

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
            }
            drawerLayout.closeDrawers();
            // KIỂM TRA MAIL ĐỂ PHÂN QUYỀN
            String role =
                    getIntent().getStringExtra("role");

            if(role != null &&
                    role.equals("staff")){

                navigationView.getMenu()
                        .findItem(R.id.menuDashboard)
                        .setVisible(false);
            }
            return true;
        });

    }
    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data);

        if(resultCode == RESULT_OK){

            // RESET BÀN 1
            if(requestCode == REQUEST_TABLE1){

                btnTable1.setBackgroundColor(
                        Color.GREEN);
            }

            // RESET BÀN 2
            else if(requestCode == REQUEST_TABLE2){

                btnTable2.setBackgroundColor(
                        Color.GREEN);
            }
        }
    }
}