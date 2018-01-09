package zemzarioumaima.rondo;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import zemzarioumaima.rondo.Entity.Rondonnee;
import zemzarioumaima.rondo.Entity.User;

import static zemzarioumaima.rondo.Page1.id;

/**
 * Created by ouma on 21/11/2016.
 */

public class  RondosAdapter extends RecyclerView.Adapter<RondosAdapter.MyViewHolder> {

private Context mContext;
private List<Rondonnee> albumList;
    private   String s ;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, count;
    public ImageView thumbnail, overflow;
    public ImageView likeImageView;
    public ImageView shareImageView;


    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);


        //count = (TextView) view.findViewById(R.id.count);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        likeImageView = (ImageView) view.findViewById(R.id.likeImageView);
        shareImageView = (ImageView) view.findViewById(R.id.shareImageView);


       // overflow = (ImageView) view.findViewById(R.id.overflow);
        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int id = (int)likeImageView.getTag();
                if( id == R.drawable.ic_like){

                    likeImageView.setTag(R.drawable.ic_liked);
                    likeImageView.setImageResource(R.drawable.ic_liked);


                    firebaseAuth = FirebaseAuth.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference();



                    FirebaseUser user =  firebaseAuth.getCurrentUser();
                    String k = user.getUid();
                    User participant = new User(user.getDisplayName(),user.getEmail()  );

                    mDatabase.child("users").child(k).child("favorie").child(s).setValue("favorite");

                }else{

                    likeImageView.setTag(R.drawable.ic_like);
                    likeImageView.setImageResource(R.drawable.ic_like);
                   // Toast.makeText(getActivity(),title.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();


                }

            }
        });











    }
}










    public  RondosAdapter (Context mContext, List<Rondonnee> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rondo_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String name=albumList.get(position).getTitre();
        final String image=albumList.get(position).getImage();
       Rondonnee album = albumList.get(position);
        holder.title.setText(album.getTitre());
        PicassoClient.downloadImage(mContext,albumList.get(position).getImage(),holder.thumbnail);
        holder.likeImageView.setTag(R.drawable.ic_like);
        s = album.getId();



    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
 * Click listener for popup menu items
 */



    public boolean onMenuItemClick() {
        return onMenuItemClick();
    }

    public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }/**
 * Click listener for popup menu items
 */





}



