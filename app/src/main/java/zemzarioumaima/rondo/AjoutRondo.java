package zemzarioumaima.rondo;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.DatePicker;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import zemzarioumaima.rondo.Entity.Rondonnee;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ouma on 23/11/2016.
 */

public class AjoutRondo extends Fragment implements OnClickListener {
   // private FirebaseAuth firebaseAuth;
   private static final int PICK_IMAGE_REQUEST = 234;
    private DatabaseReference mDatabase;
    private ImageView imageView;
    private Uri filePath;
    private EditText titre;
    private EditText description;
    private EditText img , datte, prix ;
    String dep,destin;
    private  Uri postPicUri;
    private static final int GALLERY_REQUEST = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    private DatePickerDialog fromDatePickerDialog;


    private SimpleDateFormat dateFormatter;
    private Button buttonSignup ,  btnAddPic;
    private ProgressDialog progressDialog;
    public  AjoutRondo(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();





    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //firebaseAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ajout_rondo, container, false);

        titre = (EditText)view.findViewById(R.id.titre);
       description = (EditText)view.findViewById(R.id.descri);
        img = (EditText)view.findViewById(R.id.image);
        datte = (EditText)view.findViewById(R.id.datttte);
        prix = (EditText)view.findViewById(R.id.prix);
        PlaceAutocompleteFragment autocompleteFragment
                = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        PlaceAutocompleteFragment autocompleteFragment2
                = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);



        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("rr", "Place: " + place.getName());
               // dep = place.getName().toString();//get place details here
                dep = String.valueOf(place.getLatLng().latitude) + "," + String.valueOf(place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("tt", "An error occurred: " + status);
            }
        });


        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("rr", "Place: " + place.getName());
                destin =String.valueOf(place.getLatLng().latitude) + "," + String.valueOf(place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("tt", "An error occurred: " + status);
            }
        });


        imageView = (ImageView) view.findViewById(R.id.photo);
        btnAddPic = (Button)view.findViewById(R.id.button2);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        datte.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                datte.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        btnAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

                


            }
        });


        buttonSignup = (Button)view.findViewById(R.id.button);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerRond();
                Fragment f1 = null;
                f1 = new Page1();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, f1);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        progressDialog = new ProgressDialog(this.getActivity());


        //attaching listener to button
        // buttonSignup.setOnClickListener(this);

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
             postPicUri = data.getData();
            imageView.setImageURI(postPicUri);
        }
    }

    private void registerRond() {



        //getting email and password from edit texts
        String tit = titre.getText().toString().trim();
        String des =description.getText().toString().trim();



        //checking if email and passwords are empty
        if (TextUtils.isEmpty(tit)) {
            Toast.makeText(this.getActivity(), " entrez email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(des)) {
            Toast.makeText(this.getActivity(), " entrez mot de passe", Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("veuillez patienter...");
        progressDialog.show();

      Ajouter();


    }



    private void Ajouter() {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String path ="Rondonnees/" + UUID.randomUUID() + ".png";
        StorageReference storageRef = storage.getReferenceFromUrl("gs://oumaima-25255.appspot.com");
        StorageReference filepath = storageRef.child(path);
        filepath.putFile(postPicUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri postPicUrll = taskSnapshot.getDownloadUrl();
                String t = titre.getText().toString();
                String d =  description.getText().toString();
                //String c =  img.getText().toString();
                String pr =  prix.getText().toString();
                String dat =  datte.getText().toString();

                String key = mDatabase.child("Rondonne").push().getKey();

                Rondonnee rondonnee = new Rondonnee(t,pr,dat ,dep ,destin,d,postPicUrll.toString(),user.getUid().toString());
                Map<String, Object> postValues = rondonnee.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put( key, postValues);
                mDatabase.child("Rondonne").updateChildren(childUpdates);
                progressDialog.dismiss();

            }
        });







    }


    @Override
    public void onClick(View v) {
        if(v == datte) {
            fromDatePickerDialog.show();

        }
    }
}


