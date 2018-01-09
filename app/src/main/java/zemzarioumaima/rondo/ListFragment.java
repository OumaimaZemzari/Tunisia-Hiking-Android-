package zemzarioumaima.rondo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import zemzarioumaima.rondo.Entity.Rondonnee;
import zemzarioumaima.rondo.Entity.User;

import static zemzarioumaima.rondo.page2.id;

/**
 * Created by manel on 01/01/2017.
 */

public class   ListFragment extends Fragment {

    private ArrayAdapter adapter;
    public static String mailPar = "";

    ArrayList<String> ar = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listemain, container, false);
        prepareAlbums();
          ar.add("aaaaaaa");
        ar.add("");

         adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.activity_listview, ar);

        final ListView listView = (ListView) view.findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                  mailPar=itemValue;

                // Show Alert
                Toast.makeText(getActivity(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
                Fragment f1 = null;
                f1 = new SendMail();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, f1);
                transaction.addToBackStack(null);

                transaction.commit();

            }
        });

        return view;
    }

    private void prepareAlbums() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

           String ss = id ;
        database.getReference("Rondonne").child(ss).child("participants").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w("TodoApp", "getRondonne:onCancelled " + dataSnapshot.toString());
                        Log.w("TodoApp", "count = " + String.valueOf(dataSnapshot.getChildrenCount()) + " values " + dataSnapshot.getKey());
                        ar.add("Liste De Paticipants  ( "+String.valueOf(dataSnapshot.getChildrenCount()) +" )");


                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            Log.w("TodoApp",data.child("email").getValue().toString() );

                                ar.add(data.child("email").getValue().toString());


                             }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                    }
                });

       // adapter.notifyDataSetChanged();
    }

}
