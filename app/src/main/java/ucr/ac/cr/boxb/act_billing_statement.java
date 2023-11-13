package ucr.ac.cr.boxb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ucr.ac.cr.boxb.databinding.LytActBillingStatementBinding;

public class act_billing_statement extends AppCompatActivity {
    private LytActBillingStatementBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_act_billing_statement);

        binding = LytActBillingStatementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_stantement);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.billing_statements, R.id.trial_blance, R.id.balance_sheets, R.id.income_statement)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_billing_statement);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navStantement, navController);



    }
}