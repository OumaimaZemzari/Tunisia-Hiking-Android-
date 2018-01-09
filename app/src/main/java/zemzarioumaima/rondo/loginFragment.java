package zemzarioumaima.rondo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
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

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ouma on 08/11/2016.
 */

public class loginFragment extends Fragment {
    private FirebaseAuth mAuth;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;


    public loginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.login, container, false);

        editTextEmail = (EditText)view.findViewById(R.id.editText);
        editTextPassword = (EditText)view.findViewById(R.id.editText2);


        buttonSignup = (Button)view.findViewById(R.id.button);
       /* buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        }); */
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        progressDialog = new ProgressDialog(this.getActivity());


        //attaching listener to button
        // buttonSignup.setOnClickListener(this);

        return view;
    }

    private void login() {
        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this.getActivity(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this.getActivity(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("veuillez patienter...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();


                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Log.w(TAG, "echec", task.getException());
                            Toast.makeText(getActivity(),"echec",
                                    Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "echec", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(), "signInWithEmail:failed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.d(TAG, ":" + task.isSuccessful());
                            Toast.makeText(getActivity(), "reussir", Toast.LENGTH_SHORT).show();
                            Fragment f1 = null;
                            f1 = new Page1();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, f1);
                            transaction.addToBackStack(null);

                            transaction.commit();
                            MainActivity.nav_user.setText(editTextEmail.getText().toString().trim() );

                        }

                        // ...
                    }
                });


    }


}
