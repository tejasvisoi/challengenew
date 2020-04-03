package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class Profile extends Fragment {
    private static final int PICK_IMAGE = 121;
    EditText et1,et2,et3,et4;
    private DatabaseReference databaseReference;
    private DatabaseReference logReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ArrayList<users> entries;
    Button upload;
    Uri selectedImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        et1=rootView.findViewById(R.id.editText);
        et2=rootView.findViewById(R.id.editText2);
        et3=rootView.findViewById(R.id.editText3);
        et4=rootView.findViewById(R.id.editText4);
        upload=rootView.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        loadEntries();

        View view1 = (View) rootView.findViewById(R.id.next);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), registration.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
    private void getImage(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,PICK_IMAGE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();

        }

    }
    private void loadEntries() {
        entries = new ArrayList<>();
        logReference = databaseReference.child("Students").child(firebaseUser.getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String country = ds.child("depart").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    String number= ds.child("name").getValue(String.class);
                    et1.setText(name);
                    et2.setText(name);
                    et3.setText(name);
                    et4.setText(name);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        logReference.addListenerForSingleValueEvent(valueEventListener);

    }
}
