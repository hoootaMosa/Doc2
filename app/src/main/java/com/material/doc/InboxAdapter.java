package com.material.doc;

import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import co.dift.ui.SwipeToAction;


public class InboxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> items;


    /** References to the views for each data item **/
    public class BookViewHolder extends SwipeToAction.ViewHolder<Message> {
        public TextView titleView;
        public TextView authorView;
        public SimpleDraweeView imageView;

        public BookViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            authorView = (TextView) v.findViewById(R.id.author);
            imageView = (SimpleDraweeView) v.findViewById(R.id.image);
        }
    }

    /** Constructor **/
    public InboxAdapter(List<Message> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message item = items.get(position);
        BookViewHolder vh = (BookViewHolder) holder;
        vh.titleView.setText(item.getTitle());
        vh.authorView.setText(item.getAuthor());
        vh.imageView.setImageURI(Uri.parse(item.getImageUrl()));
        vh.data = item;
    }
}