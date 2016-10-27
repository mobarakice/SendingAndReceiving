package group.atomap.firebasedemo.firebasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import group.atomap.firebasedemo.R;
import group.atomap.firebasedemo.Util;

public class NewConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Message> mMessages;
    public static String mRecipient;
    public static String mSender;
    private MessageDataSource.MessagesListener mListener;

    private RecyclerView mView;
    private LinearLayoutManager manager;
    private MessageAdapter mAdapter;
    private EditText newMessageView;
    LinearLayout layout;
    private String previous = "";

    // Database reference.......

    private DatabaseReference dbReference;

    private String chat_msg, chat_user_name;
    private String temp_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_layout);

        // Retrive data from intent
        getBundle();
        // Database reference..........
        dbReference = FirebaseDatabase.getInstance().getReference().child(Util.ROOT);
        setUpView();

        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mListener = MessageDataSource.addMessagesListener("messages", this);

    }

    private void appendChatConversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {

            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();
            Message message = new Message();
            message.setmSender(chat_user_name);
            message.setmText(chat_msg);
            mMessages.add(message);

        }


    }

    private void sendMessage() {
        Map<String, Object> map = new HashMap<String, Object>();
//        temp_key = dbReference.push().getKey();
//        Log.d("temp_key>>>>>>>>", "seelog: " + temp_key);
        dbReference.updateChildren(map);
        DatabaseReference message_root = dbReference.child(mSender);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", mSender);
        map2.put("msg", newMessageView.getText().toString());
        message_root.updateChildren(map2);
    }

    private void setUpView() {
        mMessages = new ArrayList<>();
        mView = (RecyclerView) findViewById(R.id.recycler_view);
        mView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        mView.setLayoutManager(manager);
        mAdapter = new MessageAdapter(this, mMessages);
        mView.setAdapter(mAdapter);
        layout = (LinearLayout) findViewById(R.id.overlay);

        getSupportActionBar().setTitle(mRecipient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newMessageView = (EditText) findViewById(R.id.new_message);
        Button sendMessage = (Button) findViewById(R.id.send_message);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        sendMessage.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        previous = newMessageView.getText().toString();
    }

    private void getBundle() {
        Bundle bundle = getIntent().getBundleExtra(Util.FROM_USER_LIST);
        mRecipient = bundle.getString(Util.RECEIVER);
        mSender = bundle.getString(Util.SENDER);
    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        String pa;
        if (c.get(Calendar.HOUR_OF_DAY) < 12) {
            pa = "AM";
        } else {
            pa = "PM";
        }
        return hour + ":" + min + ":" + seconds + " " + pa;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message:
                sendMessage();
                newMessageView.setText("");
                break;
            case R.id.imageButton:
                layout.setVisibility(View.VISIBLE);
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                break;
        }

    }


    private void append(String previous, String addedS) {
        previous = previous + addedS;
        newMessageView.setText(previous);
    }

    public void onClickAppend(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                previous = newMessageView.getText().toString();
                append(previous, "1");
                break;
            case R.id.btn_two:
                previous = newMessageView.getText().toString();
                append(previous, "2");
                break;
            case R.id.btn_three:
                previous = newMessageView.getText().toString();
                append(previous, "3");
                break;
            case R.id.btn_four:
                previous = newMessageView.getText().toString();
                append(previous, "4");
                break;
            case R.id.btn_five:
                previous = newMessageView.getText().toString();
                append(previous, "5");
                break;
            case R.id.btn_six:
                previous = newMessageView.getText().toString();
                append(previous, "6");
                break;
            case R.id.btn_seven:
                previous = newMessageView.getText().toString();
                append(previous, "7");
                break;
            case R.id.btn_eight:
                previous = newMessageView.getText().toString();
                append(previous, "8");
                break;
            case R.id.btn_nine:
                previous = newMessageView.getText().toString();
                append(previous, "9");
                break;
            case R.id.btn_zero:
                previous = newMessageView.getText().toString();
                append(previous, "0");
                break;
            case R.id.btn_dot:
                previous = newMessageView.getText().toString();
                append(previous, ".");
                break;
            case R.id.btn_done:
                previous = newMessageView.getText().toString();
                newMessageView.setText(previous + " ");
                layout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}