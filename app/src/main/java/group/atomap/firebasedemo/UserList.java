package group.atomap.firebasedemo;

import android.support.annotation.NonNull;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import group.atomap.firebasedemo.firebasechat.User;


public class UserList extends AppCompatActivity {

    private UserListAdapter mAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView mRecyclerView;
    private ArrayList<User> mUsers;

    private DatabaseReference dbReference;
    private ValueEventListener mUserListListener;

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
        mAdapter = new UserListAdapter(UserList.this, mUsers);
        mRecyclerView.setAdapter(mAdapter);

        dbReference.addValueEventListener(userListListener);
        mUserListListener = userListListener;
    }

    private ValueEventListener userListListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("dataSnapshot: >>>>", "seelog: " + dataSnapshot);
            getChild(dataSnapshot);
//            for (User user : mUsers) {
//                Log.d("User>>>>>", "seelog: " + user.getUserName() + "\n" + user.getUserId() + "\n" + user.getUserEmail());
//            }
            mAdapter.notifyDataSetChanged();

        }

        private void getChild(DataSnapshot dataSnapshot) {
            for (DataSnapshot dp : dataSnapshot.getChildren()) {
                ArrayList<String> singleUser = new ArrayList<>();
                for (DataSnapshot dp1 : dp.getChildren()) {
                    String s = (String) dp1.getValue();
                    singleUser.add(s);
                }
                User user = singleUser(singleUser);
                if (user != null && !user.getUserId().equalsIgnoreCase(currentUserId())) {
                    mUsers.add(user);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("seelog", "onCancelled: ", databaseError.toException());
            Toast.makeText(UserList.this, "Failed to load User list.",
                    Toast.LENGTH_SHORT).show();
        }

    };

    private User singleUser(ArrayList<String> singleUser) {
        return new User(singleUser.get(2), singleUser.get(1), singleUser.get(0));
    }

    public String currentUserId() {
        String uId = LoginActivity.mAuth.getCurrentUser().getUid();
        return uId;
    }

    //
    @Override
    public void onStop() {
        super.onStop();
        if (mUserListListener != null) {
            dbReference.removeEventListener(mUserListListener);
        }

    }

}
