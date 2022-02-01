package com.idat.cine.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.idat.cine.R;
import com.idat.cine.ViewPagerAdapter;
import com.idat.cine.activity.DashboardActivity;
import com.idat.cine.activity.MovieActivity;
import com.squareup.picasso.Picasso;

import java.util.Map;


public class BillboardFragment extends Fragment  {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyecto-final-db8ed-default-rtdb.firebaseio.com/");
    ImageView rImage;

    ViewPager mViewPager;
    ImageButton imageView;
    ViewPagerAdapter mViewPagerAdapter;


    String[] images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_billboard, container, false);
        View viewSlider = inflater.inflate(R.layout.slider_billboard, container, false);
        rImage = viewSlider.findViewById(R.id.imageViewMain);



        DatabaseReference ref = database.getReference("cartelera").child("pelicula");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        images = collectPelicula(dataSnapshot);

                        mViewPager = view.findViewById(R.id.viewPagerMain);

                        // Initializing the ViewPagerAdapter
                        mViewPagerAdapter = new ViewPagerAdapter(getContext(), images);

                        // Adding the Adapter to the ViewPager
                        mViewPager.setAdapter(mViewPagerAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


        DatabaseReference myRef = database.getReference("cartelera").child("pelicula/1");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (entry.getKey().equals("imagen")) {
                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




        return view;

    }

    public void myClickMethod(View v){

        Intent i = new Intent(getActivity(), MovieActivity.class);
        startActivity(i);
        getActivity().finish();



    }

    private String[] collectPelicula(DataSnapshot dataSnapshot) {

        long cData = dataSnapshot.getChildrenCount();

        String[] strLink = new String[(int) cData];
        int i = 0;
        for (DataSnapshot values : dataSnapshot.getChildren()) {
            //query for the parent of the date "Eminem"
            String key = values.getKey();
            System.out.println(key);
            //query for location in the music category
            String link = values.child("imagen").getValue().toString();
            System.out.println(values.child("imagen").getValue().toString());
            //query for date in the music category
            System.out.println(values.child("nombre").getValue().toString());
            strLink[i] = link;

            i++;
        }

        return strLink;
    }

}