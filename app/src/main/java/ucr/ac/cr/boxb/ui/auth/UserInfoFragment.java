package ucr.ac.cr.boxb.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ucr.ac.cr.boxb.MainActivity;
import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.act_Auth;
import ucr.ac.cr.boxb.databinding.FragmentUserInfoBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.imageview.ShapeableImageView;


public class UserInfoFragment extends Fragment {

    private FragmentUserInfoBinding binding;
    private FirebaseAuth boxBAuth;

    ImageButton btn_UserInfo_logout;
    TextView tv_UserInfo_username, tv_UserInfo_email;
    ShapeableImageView img_UserInfo_profilePic;
    String imgUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boxBAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize components
        btn_UserInfo_logout = binding.btnUserInfoLogout;
        tv_UserInfo_email = binding.tvUserInfoEmail;
        tv_UserInfo_username = binding.tvUserInfoUsername;
        img_UserInfo_profilePic = binding.imgUserInfoProfilePic;

        FirebaseUser user = boxBAuth.getCurrentUser();
        imgUrl = String.valueOf(user.getPhotoUrl());


//        Toast.makeText(getActivity(), user.getDisplayName(), Toast.LENGTH_SHORT).show();

        tv_UserInfo_username.setText(user.getDisplayName());
        tv_UserInfo_email.setText(user.getEmail());
        Glide.with(this)
                .load(imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img_UserInfo_profilePic);
        btn_UserInfo_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxBAuth.signOut();
                Toast.makeText(getActivity(), "User sign out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), act_Auth.class);
                startActivity(intent);
            }
        });

    }
}