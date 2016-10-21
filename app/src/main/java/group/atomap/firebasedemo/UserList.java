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


public class UserList extends AppCompatActivity {

    private UserListAdapter mAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView mRecyclerView;
    // private ArrayList<User> mUsers;

//    private DatabaseReference mDatabaseReference;
//    private ValueEventListener mValueEventListener;

    private static final String TAG = "UserList";
    private DatabaseReference userlistReference;
    private ValueEventListener mUserListListener;
    private ArrayList<String> usernamelist = new ArrayList<>();

    private Firebase mFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyle_view);
        mRecyclerView.setHasFixedSize(true);
        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        mFirebase = new Firebase("https://fir-demo-88ca7.firebaseio.com");


        //userlistReference = FirebaseDatabase.getInstance().getReference().child("users");

//        userlistReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                usernamelist = (ArrayList<String>) dataSnapshot.getValue();
//                //if(usernamelist==null)return;
//                Log.i(TAG, "onDataChange: " + dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "onCancelled: ", databaseError.toException());
//                Toast.makeText(UserList.this, "Failed to load User list.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
        getEmail();

        mAdapter = new UserListAdapter(UserList.this, usernamelist);
        mRecyclerView.setAdapter(mAdapter);

        //mUserListListener = userlistReference;

//        onStart();


    }

//    private ValueEventListener userListListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//
//            usernamelist = (ArrayList<String>) dataSnapshot.getValue();
//            if(usernamelist==null){
//                return;
//            }
//            for (String name : usernamelist) {
//                User u = new User();
//                u.setUserName(name);
//                mUsers.add(u);
//            }
//            mAdapter.notifyDataSetChanged();
//            usernamelist.remove(usernameOfCurrentUser());
//
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            Log.w("seelog", "onCancelled: ", databaseError.toException());
//            Toast.makeText(UserList.this, "Failed to load User list.",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//    };
//
////    @Override
////    protected void onStart() {
////        super.onStart();
////        mDatabaseReference.addValueEventListener(userListListener);
////        mValueEventListener = userListListener;
////    }
//
//    public String usernameOfCurrentUser() {
//        String email = LoginActivity.mAuth.getCurrentUser().getEmail();
//        if (email.contains("@")) {
//            return email.split("@")[0];
//        } else {
//            return email;
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // Remove post value event listener
//        if (mValueEventListener != null) {
//            mDatabaseReference.removeEventListener(mValueEventListener);
//        }
//
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
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
//        userlistReference.addValueEventListener(userListener);
//
//        mUserListListener = userListener;
//    }


    private void getEmail() {
//        String uid = mFirebase.getAuth().getUid();
//        mFirebase.addAuthStateListener(new Firebase.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(AuthData authData) {
//                authData.getUid();
//            }
//        });
        mFirebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                usernamelist = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // Remove post value event listener
//        if (mUserListListener != null) {
//            userlistReference.removeEventListener(mUserListListener);
//        }
//
//    }

}
