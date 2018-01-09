package zemzarioumaima.rondo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import static zemzarioumaima.rondo.ListFragment.mailPar;

/**
 * Created by manel on 02/01/2017.
 */

public class SendMail extends Fragment {

    EditText editTextTo,editTextSubject,editTextMessage;
    Button send;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sendmail, container, false);
        editTextTo=(EditText)view.findViewById(R.id.editText1);
        editTextSubject=(EditText)view.findViewById(R.id.editText2);
        editTextMessage=(EditText)view.findViewById(R.id.editText3);
        editTextTo.setText(mailPar);
        send=(Button)view.findViewById(R.id.button1);
        send.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                String to=editTextTo.getText().toString();
                String subject=editTextSubject.getText().toString();
                String message=editTextMessage.getText().toString();


                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choisir Email client :"));


            }

        });

        return view;
    }
}
