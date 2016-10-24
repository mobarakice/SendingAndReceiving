package group.atomap.firebasedemo.firebasechat;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import group.atomap.firebasedemo.R;
import group.atomap.firebasedemo.Util;


/**
 * Created by Tauhid on 10/14/2016.
 */

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener, MessageDataSource.MessagesCallbacks {

    private ArrayList<Message> mMessages;
    public static String mRecipient;
    public static String mSender;
    private Date mLastMessageDate = new Date();
    private MessageDataSource.MessagesListener mListener;

    private RecyclerView mView;
    private LinearLayoutManager manager;
    private MessageAdapter mAdapter;
    private EditText newMessageView;
    LinearLayout layout;
    private String previous = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_layout);

        Bundle bundle = getIntent().getBundleExtra(Util.FROM_USER_LIST);
        mRecipient = bundle.getString(Util.RECEIVER);
        mSender = bundle.getString(Util.SENDER);

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

        mListener = MessageDataSource.addMessagesListener("messages", this);

    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        int pmAm = c.get(Calendar.AM_PM);
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
                String newMessage = newMessageView.getText().toString();
                newMessageView.setText("");
                Message msg = new Message();
                msg.setmDate(new Date());
                msg.setmText(newMessage);
                msg.setmSender(mSender);
                msg.setTime(getCurrentTime());

                MessageDataSource.saveMessage(msg, "messages");
                break;
            case R.id.imageButton:
//                mView.setVisibility(View.INVISIBLE);
                layout.setVisibility(View.VISIBLE);
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                break;
        }

    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageDataSource.stop(mListener);
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

