package ucr.ac.cr.boxb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import ucr.ac.cr.boxb.databinding.LytActBillingStatementBinding;

public class act_billing_statement extends AppCompatActivity {
    private LytActBillingStatementBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.lyt_act_billing_statement);

        binding = LytActBillingStatementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_stantement);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_sales, R.id.navigation_purchases, R.id.navigation_expenses)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_billing_statement);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navStantement, navController);


        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_sales) {
                    NavController navController = Navigation.findNavController(act_billing_statement.this, R.id.nav_host_fragment_billing_statement);
                    navController.navigate(R.id.sales_navigation);
                }else if (id == R.id.navigation_purchases){
                    NavController navController = Navigation.findNavController(act_billing_statement.this, R.id.nav_host_fragment_billing_statement);
                    navController.navigate(R.id.purchases_navigation);
                    return true;
                }else if (id == R.id.navigation_expenses){
                    NavController navController = Navigation.findNavController(act_billing_statement.this, R.id.nav_host_fragment_billing_statement);
                    navController.navigate(R.id.expenses_navigation);
                    return true;
                }

                return false;
            }
        });


    }
}