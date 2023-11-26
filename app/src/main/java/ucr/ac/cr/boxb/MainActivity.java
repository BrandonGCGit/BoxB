package ucr.ac.cr.boxb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ucr.ac.cr.boxb.databinding.ActivityMainBinding;
import ucr.ac.cr.boxb.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String URL = "https://api.cambio.today/v1/quotes/USD/CRC/json?quantity="+1+"&key=45816|GOG4CB4u5GnqVvyTmR3m";

    FirebaseAuth boxBAuth;
    Button btn_Prueba_Login, btn_Billing, btnConversor;

    EditText txtDolares;
    TextView txtColones;

    RequestQueue requestQueue;


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
        btnConversor= findViewById(R.id.btnConversor);
        txtDolares=findViewById(R.id.txtDolares);
        txtColones=findViewById(R.id.txtColones);
        requestQueue = Volley.newRequestQueue(this);


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

        btnConversor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "https://api.cambio.today/v1/quotes/USD/CRC/json?quantity="+txtDolares.getText()+"&key=45816|GOG4CB4u5GnqVvyTmR3m";
                //jsonArrayRequest();

            }
        });
    }


    private void initializeFirebase(){
        FirebaseApp.initializeApp(MainActivity.this);
    }

    private void replaceFragment(HomeFragment homeFragment) {
    }

}