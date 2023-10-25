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

import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentGreetBinding;

public class GreetFragment extends Fragment {

    private FragmentGreetBinding binding;
    NavController navController;
    Button btn_Greet_getStarted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentGreetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }//onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Navigation Controller
        navController = Navigation.findNavController(requireView());

        //Initialize components
        btn_Greet_getStarted = binding.btnGreetGetStarted;


        //Button to initialize app
        btn_Greet_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to LoginFragment
                navController.navigate(R.id.action_greetFragment_to_loginFragment);
            }
        });
    }
}