package group.atomap.firebasedemo.firebasechat;

import android.util.Log;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MessageDataSource {
    private static final Firebase sRef = new Firebase("https://fir-demo-88ca7.firebaseio.com");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG = "MessageDataSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";


    public static void saveMessage(Message message, String convoId) {
        Date date = message.getmDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getmText());
        msg.put(COLUMN_SENDER, message.getmSender());
        sRef.child(convoId).child(key).setValue(msg);
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        int pmAm = c.get(Calendar.AM_PM);
        String pa;
        if (c.get(Calendar.HOUR_OF_DAY) <= 12) {
            pa = "AM";
        } else {
            pa = "PM";
        }

        return hour + ":" + min + ":" + seconds + " " + pa;
    }


    public static MessagesListener addMessagesListener(String convoId, final MessagesCallbacks callbacks) {

        MessagesListener listener = new MessagesListener(callbacks);
        sRef.child(convoId).addChildEventListener(listener);
        return listener;

    }

    public static void stop(MessagesListener listener) {
        sRef.removeEventListener(listener);
    }


    public static class MessagesListener implements ChildEventListener {

        private MessagesCallbacks callbacks;

        public MessagesListener(MessagesCallbacks callbacks) {
            this.callbacks = callbacks;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            HashMap<String, String> msg = (HashMap) dataSnapshot.getValue();
            Message message = new Message();
            message.setmSender(msg.get(COLUMN_SENDER));
            message.setmText(msg.get(COLUMN_TEXT));
            message.setTime(getCurrentTime());
            try {
                message.setmDate(sDateFormat.parse(dataSnapshot.getKey()));
            } catch (Exception e) {
                Log.d(TAG, "Couldn't parse date" + e);
            }
            if (callbacks != null) {
                callbacks.onMessageAdded(message);
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }

    }


    public interface MessagesCallbacks {
        public void onMessageAdded(Message message);
    }
}