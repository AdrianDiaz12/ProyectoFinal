package com.idat.cine.fragment.user;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idat.cine.R;
import com.idat.cine.models.User;


public class RegisterFragment extends Fragment {
    private MaterialButton registerButton;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputLayout passwordTextInput;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyecto-final-db8ed-default-rtdb.firebaseio.com/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        email = view.findViewById(R.id.email_input);
        password = view.findViewById(R.id.password_input);

        passwordTextInput = view.findViewById(R.id.password_text);

        registerButton = view.findViewById(R.id.register_button);


        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = database.getReference();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (!isPasswordValid(password.getText())) {
                    passwordTextInput.setError("password must have 6 characters");
                } else {
                    mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "User create successfully", Toast.LENGTH_SHORT).show();


                                        FirebaseUser fbUser = mAuth.getCurrentUser();

                                        User user = new User("", emailText, "");

                                        ref.child("usuarios").child(fbUser.getUid()).setValue(user);


                                        ((com.idat.cine.NavigationHost) getActivity()).navigateTo(new ProfileFragment(), false);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }


            }
        });

        return view;

    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 6;
    }

}