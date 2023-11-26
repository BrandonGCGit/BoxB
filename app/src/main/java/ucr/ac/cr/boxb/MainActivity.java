package ucr.ac.cr.boxb;

import static com.android.volley.Request.Method.GET;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;
    //private static final String URL= "https://api.cambio.today/v1/quotes/USD/CRC/json?quantity="+1+"&key=45816|GOG4CB4u5GnqVvyTmR3m";

    FirebaseAuth boxBAuth;
    Button btn_Prueba_Login, btn_Billing, btnConversor;

    EditText txtDolares;
    TextView txtColones;
    RequestQueue requestQueue;
    Spinner spinner, spinner1;
    String divisa1, divisa2;


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

        spinner= findViewById(R.id.spinner);
        spinner1= findViewById(R.id.spinner1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.divisas, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.divisas, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter1);



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
                String URL = "https://api.cambio.today/v1/quotes/"+divisa1+"/"+divisa2+"/json?quantity="+txtDolares.getText()+"&key=45816|GOG4CB4u5GnqVvyTmR3m";
                //jsonArrayRequest();
                //stringRequest();
                jsonObjectRequest(URL);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisa1= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisa2= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initializeFirebase(){
        FirebaseApp.initializeApp(MainActivity.this);
    }

    private void replaceFragment(HomeFragment homeFragment) {
    }

    private void stringRequest(String URL){
        StringRequest request= new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        txtColones.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }


    private void jsonObjectRequest(String URL){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray jsonArray = response.getJSONArray("result");
                            //int size = jsonArray.length();
                            //for (int i=0; i<size; i++){
                                JSONObject jsonObject = new JSONObject(String.valueOf(response.getJSONObject("result")));
                                String amount = jsonObject.getString("amount");
                                txtColones.setText(amount);

                            //}
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}