package zemzarioumaima.rondo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import zemzarioumaima.rondo.Entity.User;


/**
 * Created by ouma on 08/11/2016.
 */

public class inscriFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextnom;
    private EditText editTextnombre;

    private Button buttonSignup;
    private ProgressDialog progressDialog;
    public  inscriFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();





    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //firebaseAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscri, container, false);

        editTextEmail = (EditText)view.findViewById(R.id.email);
        editTextPassword = (EditText)view.findViewById(R.id.pass);
        editTextnom = (EditText)view.findViewById(R.id.name);
        editTextnombre = (EditText)view.findViewById(R.id.num);

        buttonSignup = (Button)view.findViewById(R.id.save);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             registerUser();
            }
        });

        progressDialog = new ProgressDialog(this.getActivity());


        //attaching listener to button
       // buttonSignup.setOnClickListener(this);

        return view;
    }

    private void registerUser() {



        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this.getActivity(), " entrez email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this.getActivity(), " entrez mot de passe", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("veuillez patienter...");
        progressDialog.show();

        //creating a new user

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    createNewUser((task.getResult().getUser()));
                     progressDialog.dismiss();
                    Toast.makeText(getActivity(), " reussire", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



    private void createNewUser(FirebaseUser userFromRegistration) {
        String username = editTextnom.getText().toString();
        String pwd =  editTextPassword.getText().toString();
        String cc = userFromRegistration.getEmail();
        String userId = userFromRegistration.getUid();
        String numbre = editTextnombre.getText().toString();

        User user = new User(username, cc , pwd , numbre);

      mDatabase.child("users").child(userId).setValue(user);
    }


















}
