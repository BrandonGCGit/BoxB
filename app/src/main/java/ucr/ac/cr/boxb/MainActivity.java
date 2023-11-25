package ucr.ac.cr.boxb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ucr.ac.cr.boxb.databinding.ActivityMainBinding;
import ucr.ac.cr.boxb.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    FirebaseAuth boxBAuth;
    Button btn_Prueba_Login, btn_Billing;

    EditText txtDolares, txtColones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeFirebase();
        //Initialize the authentification
        boxBAuth = FirebaseAuth.getInstance();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_add_client)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        btn_Prueba_Login = binding.btnPruebaLogin;
        btn_Billing = binding.btnTry;
        txtColones= findViewById(R.id.txtColones);
        txtDolares=findViewById(R.id.txtDolares);


        btn_Prueba_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxBAuth.signOut();
                Toast.makeText(MainActivity.this, "User sign out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, act_Auth.class);
                startActivity(intent);
            }
        });
        btn_Billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, act_billing_statement.class);
                startActivity(intent);
            }
        });






    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(MainActivity.this);
    }

    private void replaceFragment(HomeFragment homeFragment) {
    }

}