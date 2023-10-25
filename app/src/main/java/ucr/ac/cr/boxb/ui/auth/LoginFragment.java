package ucr.ac.cr.boxb.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentDashboardBinding;
import ucr.ac.cr.boxb.databinding.FragmentLoginBinding;


 
public class LoginFragment extends Fragment {

    //Reference form Firebase Authentification
    private FirebaseAuth boxBAuth;
    private FragmentLoginBinding binding;
    NavController navController;
    TextView tv_Login_signUp;
    EditText txt_Login_email, txt_Login_password;
    Button btn_Login_login;
    String email, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Initialize the authentification
        boxBAuth = FirebaseAuth.getInstance();
        
    }//End onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }//End onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Navigation Controller
        navController = Navigation.findNavController(requireView());

        //Initialize components
        tv_Login_signUp = binding.tvLoginSignUp;
        txt_Login_email = binding.txtLoginEmail;
        txt_Login_password = binding.txtLoginPassword;
        btn_Login_login = binding.btnLoginLogin;

        tv_Login_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to RegisterFragment
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        //--------------------------------------
        
        btn_Login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the user fill all with correct information, it will be logged in
                if (fillAllBlanks()){
                    boxBAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success
                                FirebaseUser user = boxBAuth.getCurrentUser();
                                Toast.makeText(getContext(), "User Logged In: " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getContext(), "Could not Login",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else {
                    Toast.makeText(getContext(), "Fill all the information", Toast.LENGTH_SHORT).show();
                }
                
            }//End onClick
        });
        
    }//End onViewCreated

    public boolean fillAllBlanks(){
        email = txt_Login_email.getText().toString();
        password = txt_Login_password.getText().toString();

        if (email.isEmpty()){
            txt_Login_email.setError("Required");
            return false;
        }else if (password.isEmpty()) {
            txt_Login_password.setError("Required");
            return false;
        }else{
            return true;
        }

    }//End checkBlanks
    
}//End Class