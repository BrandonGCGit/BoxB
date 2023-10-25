package ucr.ac.cr.boxb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class act_Auth extends AppCompatActivity {

    //Reference form Firebase Authentification
    private FirebaseAuth boxAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_act_auth);

        //Initialize the authentification
        boxAuth = FirebaseAuth.getInstance();
    }//End onCreate

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = boxAuth.getCurrentUser();
        if(currentUser != null){
//            reload();
        }
    }

}//End Class