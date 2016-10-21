package group.atomap.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import group.atomap.firebasedemo.firebasechat.User;


public class UserList extends AppCompatActivity {

    private UserListAdapter mAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView mRecyclerView;
    private ArrayList<User> mUsers;

//    private DatabaseReference mDatabaseReference;
//    private ValueEventListener mValueEventListener;

    private static final String TAG = "UserList";
    private DatabaseReference dbReference;
    private ValueEventListener mUserListListener;
    private ArrayList<String> userNamelist = new ArrayList<>();

    private Firebase mFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mUsers = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyle_view);
        mRecyclerView.setHasFixedSize(true);
        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        mFirebase = new Firebase("https://fir-demo-88ca7.firebaseio.com");


        dbReference = FirebaseDatabase.getInstance().getReference().child("users");
        onStart();
        mAdapter = new UserListAdapter(UserList.this, mUsers);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ValueEventListener userListListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("dataSnapshot: >>>>", "seelog: " + dataSnapshot);

            Map<String, User> mMap = (Map<String, User>) dataSnapshot.getValue();
//           ArrayList<User> users= new ArrayList<User>(mMap.values());

            for (Map.Entry m : mMap.entrySet()) {
                Log.d("User: >>>>", "seelog: " + m.getKey());
                User user = new User(m.getValue());
                Log.d("User: >>>>", "seelog: " + user.getUserName());
                // mUsers.add((User) m.getValue());
            }
            //mUsers.addAll(mMap.values());

//            mAdapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("seelog", "onCancelled: ", databaseError.toException());
            Toast.makeText(UserList.this, "Failed to load User list.",
                    Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onStart() {
        super.onStart();

        dbReference.addValueEventListener(userListListener);
        mUserListListener = userListListener;
//        final ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.d("seelog", "dataSnapshot: " + dataSnapshot);
//                usernamelist = new ArrayList<>((ArrayList) dataSnapshot.getValue());
//
//                // usernamelist.remove(usernameOfCurrentUser());
//                Log.i(TAG, "onDataChange: " + usernamelist.toString());
//
//                mAdapter = new UserListAdapter(UserList.this, usernamelist);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "onCancelled: ", databaseError.toException());
//                Toast.makeText(UserList.this, "Failed to load User list.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//        dbReference.addValueEventListener(userListListener);
//        mUserListListener = userListListener;
    }


    public String usernameOfCurrentUser() {
        String email = LoginActivity.mAuth.getCurrentUser().getEmail();
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    //
    @Override
    public void onStop() {
        super.onStop();
        // Remove post value event listener
        if (mUserListListener != null) {
            dbReference.removeEventListener(mUserListListener);
        }

    }

}
