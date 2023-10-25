package ucr.ac.cr.boxb.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {

    //Reference form Firebase Authentification
    private FirebaseAuth boxAuth;

    private FragmentRegisterBinding binding;
    NavController navController;
    TextView tv_Register_login;
    EditText txt_Register_email, txt_Register_password;
    Button btn_Register_signUp;
    String email, password;
    ImageView img_Register_userPhoto;
    Uri photoTemp;
    String imgpath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize the authentification
        boxAuth = FirebaseAuth.getInstance();
    }//End OnCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }//End onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Navigation Controller
        navController = Navigation.findNavController(requireView());

        //Initialize components
        tv_Register_login = binding.tvRegisterLogin;

        txt_Register_email = binding.txtRegisterEmail;
        txt_Register_password = binding.txtRegisterPassword;
        btn_Register_signUp = binding.btnRegisterSignUp;

        img_Register_userPhoto = binding.imgRegisterUserPhoto;

        tv_Register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to LoginFragment
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        //--------------------------------------

        btn_Register_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the user fill all the information, an user will be created
                if (fillAllBlanks()){
                    boxAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success
                                        Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getContext(), "Could not create User", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else {
                    Toast.makeText(getContext(), "Fill all the information", Toast.LENGTH_LONG).show();
                }
            }//End onClick
        });

        //--------------------------------------
        img_Register_userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //--------------------------------------

    }//End onViewCreated

    public boolean fillAllBlanks(){
        email = txt_Register_email.getText().toString();
        password = txt_Register_password.getText().toString();

        if (email.isEmpty()){
            txt_Register_email.setError("Required");
            return false;
        }else if (password.isEmpty()) {
            txt_Register_password.setError("Required");
            return false;
        }else{
            return true;
        }

    }//End checkBlanks



}//End Class