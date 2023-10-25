package ucr.ac.cr.boxb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ucr.ac.cr.boxb.databinding.FragmentAddClientBinding;
import ucr.ac.cr.boxb.databinding.FragmentLoginBinding;

public class FragmentAddClient extends Fragment {


    private FragmentAddClientBinding binding;

    private FirebaseAuth mAuth;
    // Initialize Firebase Auth
    private EditText txtName, txtLastName, txtEmail;

    private Button btn_addClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddClientBinding.inflate(inflater,container,false);
        View root = binding.getRoot();


        txtEmail = binding.txtEmailAddClient;
        txtName = binding.txtNameAddClient;
        txtLastName = binding.txtLastNameAddClient;

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            System.out.println("El usuario esta login: " + currentUser.getUid());
        }else{
            System.out.println("El usuario no esta login");

        }
    }
}