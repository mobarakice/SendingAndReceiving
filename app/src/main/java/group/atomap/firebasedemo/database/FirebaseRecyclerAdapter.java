package group.atomap.firebasedemo.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.Query;

/**
 * Created by Tauhid on 10/20/2016.
 */
public abstract class FirebaseRecyclerAdapter<Post, PostViewHolder> extends RecyclerView {
    private Context context;
    private Class<Post> postClass;
    private Class<PostViewHolder> postViewHolderClass;
    private Query postsQuery;
    private int item_post;

    public FirebaseRecyclerAdapter(Class<Post> postClass, int item_post, Class<PostViewHolder> postViewHolderClass, Query postsQuery) {
        super(null);

        this.postClass = postClass;
        this.item_post = item_post;
        this.postViewHolderClass = postViewHolderClass;
        this.postsQuery = postsQuery;
    }

    protected abstract void populateViewHolder(group.atomap.firebasedemo.database.PostViewHolder viewHolder, group.atomap.firebasedemo.database.Post model, int position);

    public void cleanup() {
    }
}
