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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idat.cine.R;
import com.idat.cine.models.User;

import java.util.Map;

public class ProfileFragment extends Fragment {
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText movile;
    private TextInputEditText fullname;
    private String Uid;
    private TextInputLayout passwordTextInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // login_cancel_button

        //email_profile_input
        //password_profile_input
        //phone_no_profile_input
        //full_name_profile_input

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        MaterialButton logoutButton = view.findViewById(R.id.login_cancel_button);
        MaterialButton updateButton = view.findViewById(R.id.update_button);

        email = view.findViewById(R.id.email_profile_input);
        password = view.findViewById(R.id.password_profile_input);
        movile = view.findViewById(R.id.phone_no_profile_input);
        fullname = view.findViewById(R.id.full_name_profile_input);

        passwordTextInput = view.findViewById(R.id.password_profile);

        password.getText().clear();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyecto-final-db8ed-default-rtdb.firebaseio.com/");


        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser fbaUser = mAuth.getCurrentUser();

        if (fbaUser != null) {
            System.out.println(fbaUser.getEmail());
            Toast.makeText(getActivity(), "User successfully", Toast.LENGTH_SHORT).show();

            Uid = fbaUser.getUid();

            DatabaseReference myRef = database.getReference("usuarios").child(fbaUser.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        User user = dataSnapshot.getValue(User.class);
                        fullname.setText(user.getName());
                        email.setText(user.getEmail());
                        movile.setText(user.getPhone());
                    } else {
                        User user = new User(fbaUser.getEmail());

                        DatabaseReference ref = database.getReference();

                        ref.child("usuarios").child(Uid).setValue(user);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {

                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        } else {
            ((com.idat.cine.NavigationHost) getActivity()).navigateTo(new RegisterFragment(), false);
        }


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                ((com.idat.cine.NavigationHost) getActivity()).navigateTo(new LoginFragment(), false);
                Toast.makeText(getActivity(), "Logout successfully", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getActivity(), "update successfully", Toast.LENGTH_SHORT).show();

                String uName = fullname.getText().toString();
                String uPhone = movile.getText().toString();
                String uEmail = email.getText().toString();


                User user = new User(uName, uEmail, uPhone);

                DatabaseReference ref = database.getReference();

                ref.child("usuarios").child(Uid).setValue(user);

                String uPassword = password.getText().toString();

                if (password.length() != 0) {
                    if (!isPasswordValid(password.getText())) {
                        passwordTextInput.setError("password must have 6 characters");
                    } else {

                        fbaUser.updatePassword(uPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User password updated.");
                                        }
                                    }
                                });
                    }
                }


            }
        });

        return view;

    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 6;
    }
}