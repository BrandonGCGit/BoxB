package ucr.ac.cr.boxb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ucr.ac.cr.boxb.databinding.LytActBillingStatementBinding;
import ucr.ac.cr.boxb.ui.utils.Popup_AddBills;

public class act_billing_statement extends AppCompatActivity {
    private LytActBillingStatementBinding binding;
    FirebaseFirestore db;
    Popup_AddBills popupAddBills = new Popup_AddBills();
    Button btn_Billing_addBill;
    TextView txtClientBill;

    String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.lyt_act_billing_statement);

        binding = LytActBillingStatementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        txtClientBill = findViewById(R.id.txtClientBill);
        btn_Billing_addBill = findViewById(R.id.btn_Billing_addBill);

        BottomNavigationView navView = findViewById(R.id.nav_stantement);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_sales, R.id.navigation_purchases, R.id.navigation_expenses)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_billing_statement);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navStantement, navController);


        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Bundle bundle = new Bundle();
                bundle.putString("clientId", clientId );
                if (id == R.id.navigation_sales) {
                    NavController navController = Navigation.findNavController(act_billing_statement.this, R.id.nav_host_fragment_billing_statement);
                    bundle.putString("type", "Sale" );
                    navController.navigate(R.id.purchases_navigation, bundle);
                }else if (id == R.id.navigation_purchases){
                    NavController navController = Navigation.findNavController(act_billing_statement.this, R.id.nav_host_fragment_billing_statement);
                    bundle.putString("type", "Purchase" );
                    navController.navigate(R.id.purchases_navigation, bundle);
                    return true;
                }else if (id == R.id.navigation_expenses){
                    NavController navController = Navigation.findNavController(act_billing_statement.this, R.id.nav_host_fragment_billing_statement);
                    bundle.putString("type", "Expense");
                    navController.navigate(R.id.purchases_navigation, bundle);
                    return true;
                }

                return false;
            }
        });

        //Get Bundle with the info of the client
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            clientId = bundle.getString("IdClient");


            if (clientId != null) {
                // Reference from Firestore
                DocumentReference docRef = db.collection("Clients").document(clientId);

                // Get Document
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                Toast.makeText(act_billing_statement.this, "" + document.getData(), Toast.LENGTH_SHORT).show();
                                String clientName = document.getString("name") + " " + document.getString("lastName");
                                txtClientBill.setText(clientName);
                            } else {
                                Toast.makeText(act_billing_statement.this, "Could not find document", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(act_billing_statement.this, "Error getting the document " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                //Show popUp to create a Bill
                btn_Billing_addBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupAddBills.showPopupAddBill(act_billing_statement.this, clientId);
                    }
                });



            } else {
                Toast.makeText(act_billing_statement.this, "Id client is null", Toast.LENGTH_SHORT).show();
            }

        }




    }
}