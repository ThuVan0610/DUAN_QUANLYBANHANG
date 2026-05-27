package van.edu.duanquanlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    // ===== BANNER =====
    ViewPager2 viewPager;
    Handler handler = new Handler();
    int index = 0;

    int[] banners = {
            R.drawable.banner2,
            R.drawable.banne3,
            R.drawable.banner4,
            R.drawable.banner1,
            R.drawable.banner5,
            R.drawable.banner6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ===== BOTTOM NAV =====
        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;
            }

            else if (id == R.id.nav_product) {
                startActivity(new Intent(this, TableActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            else if (id == R.id.nav_order) {
                startActivity(new Intent(this, ManagerActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, AccountActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });

        // ===== BANNER SLIDER =====
        viewPager = findViewById(R.id.viewPagerBanner);

        BannerAdapter adapter = new BannerAdapter(banners);
        viewPager.setAdapter(adapter);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (index == banners.length) index = 0;

                viewPager.setCurrentItem(index++, true);

                handler.postDelayed(this, 3000);
            }
        };

        handler.post(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();

        bottomNav.setSelectedItemId(R.id.nav_home);
    }
}