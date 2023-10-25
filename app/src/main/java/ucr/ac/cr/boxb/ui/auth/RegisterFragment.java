package ucr.ac.cr.boxb.ui.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ucr.ac.cr.boxb.MainActivity;
import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {

    //Reference form Firebase Authentification
    private FirebaseAuth boxAuth;

    //Reference form Firebase Storage
    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference imageRef;

    private FragmentRegisterBinding binding;
    NavController navController;
    TextView tv_Register_login;
    EditText txt_Register_email, txt_Register_password, txt_Register_username;
    Button btn_Register_signUp;
    String email, password, username;
    ImageView img_Register_userPhoto;
    Uri photoTemp = null;
    String imgpath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize the authentification
        boxAuth = FirebaseAuth.getInstance();

        //Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
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
        txt_Register_username = binding.txtRegisterUsername;
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
                                        FirebaseUser user = boxAuth.getCurrentUser();
                                        if (photoTemp!=null){
                                            addInformation(user);
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(getContext(), "Select an Image", Toast.LENGTH_SHORT).show();
                                        }
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
                //Crear un mensaje de confirmacion que se modifica para que tenga dos opciones(Camara o Galeria)
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Select a profile photo");
                alertDialog.setIcon(android.R.drawable.ic_menu_camera);
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                });

                alertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 2);
                    }
                });

                //IMPORTANTE MOSTRAR EL CUADRO DE DIALOGO
                alertDialog.show();

            }
        });

        //--------------------------------------

    }//End onViewCreated

    private void addInformation(FirebaseUser user) {

        imageRef = storageRef.child("images/" + user.getUid() + "/profile.jpg");

        if (photoTemp!=null){
            UploadTask uploadTask = imageRef.putFile(photoTemp);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Image uploaded successfully 
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    // Username and image to update the current user
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .setPhotoUri(Uri.parse(imageUrl)) 
                            .build();

                    // update current user with the information
                    user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "User updated: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error on creating user", Toast.LENGTH_SHORT).show();
                        }
                    });

                });
            }).addOnFailureListener(exception -> {
                Toast.makeText(getContext(), "Error uploading the image", Toast.LENGTH_SHORT).show();
            });


        }



    }//End addInformation

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            // get the selected Image
            photoTemp = data.getData();
            img_Register_userPhoto.setImageURI(photoTemp);
            Toast.makeText(getContext(), "Saved Image " + photoTemp, Toast.LENGTH_SHORT).show();
        }else if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img_Register_userPhoto.setImageBitmap(imageBitmap);

            File file = new File(getContext().getCacheDir(), "image.jpg");

            try {
                FileOutputStream fos = new FileOutputStream(file);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }

            photoTemp = Uri.fromFile(file);
        }
    }


    public boolean fillAllBlanks(){
        email = txt_Register_email.getText().toString();
        password = txt_Register_password.getText().toString();
        username = txt_Register_username.getText().toString();

        if (email.isEmpty()){
            txt_Register_email.setError("Required");
            return false;
        }else if (password.isEmpty()) {
            txt_Register_password.setError("Required");
            return false;
        }else if (username.isEmpty()) {
            txt_Register_username.setError("Required");
            return false;
        }else{
            return true;
        }

    }//End checkBlanks



}//End Class