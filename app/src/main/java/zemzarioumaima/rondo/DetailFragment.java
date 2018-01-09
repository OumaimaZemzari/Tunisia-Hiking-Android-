package zemzarioumaima.rondo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import zemzarioumaima.rondo.Entity.Rondonnee;
import zemzarioumaima.rondo.Entity.User;

import static zemzarioumaima.rondo.Page1.dat;
import static zemzarioumaima.rondo.Page1.des;
import static zemzarioumaima.rondo.Page1.id;
import static zemzarioumaima.rondo.Page1.image;
import static zemzarioumaima.rondo.Page1.name;
import static zemzarioumaima.rondo.Page1.prix;

/**
 * Created by ouma on 30/11/2016.
 */

public class DetailFragment extends Fragment {
    TextView nameTxt , c , p , d ;
    ImageView img;
    private RondosAdapter adapter;
    private List<Rondonnee> albumList;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detail, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
       final FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);



        nameTxt= (TextView) view.findViewById(R.id.nameTxtDetail);
        c = (TextView) view.findViewById(R.id.descDetailTxt);
        d = (TextView) view.findViewById(R.id.dateDetail);
        p = (TextView) view.findViewById(R.id.prix);
        img= (ImageView) view.findViewById(R.id.spacecraftImageDetail);
        PicassoClient.downloadImage(getContext(),image,img);




        nameTxt.setText("titre:"+ name);
        c.setText("description :" + des);
        p.setText("prix :" + prix +"D");
        d.setText("date:"+ dat);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), TrajectMap.class);
                startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "paticiper", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fab2.setTag(R.drawable.go);
                fab2.setImageResource(R.drawable.go);

                String userId = id;


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String k = user.getUid();
                    User participant = new User(user.getDisplayName(),user.getEmail()  );

                    mDatabase.child("Rondonne").child(userId).child("participants").child(k).setValue(participant);





            }
        });

        return view;
    }
























}
