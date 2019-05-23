package com.material.doc;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;


public class SwipToAction extends Activity {

    RecyclerView recyclerView;
    InboxAdapter adapter;
    SwipeToAction swipeToAction;

    List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // facebook image library
        Fresco.initialize(this);


        setContentView(R.layout.swip);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // heres the all work
        //get the data from the server into an adapter and set it to the recyclerview
        adapter = new InboxAdapter(this.messages);
        recyclerView.setAdapter(adapter);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Message>() {
            @Override
            public boolean swipeLeft(final Message itemData) {
                final int pos = removeRequist(itemData);
                displaySnackbar(itemData.getTitle() + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addRequist(pos, itemData);
                    }
                });
                //here you can do the reject requist action
                return true;
            }

            @Override
            public boolean swipeRight(Message itemData) {
                displaySnackbar(itemData.getTitle() + " loved", null, null);
                //here you can do the accept requist action
                return true;
            }

            @Override
            public void onClick(Message itemData) {
                displaySnackbar(itemData.getTitle() + " clicked", null, null);
            }

            @Override
            public void onLongClick(Message itemData) {
                displaySnackbar(itemData.getTitle() + " long clicked", null, null);
            }
        });


        populate();

        // use swipeLeft or swipeRight and the elem position to swipe by code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToAction.swipeRight(2);
            }
        }, 3000);
    }

    private void populate() {
        this.messages.add(new Message("Einstein: his life and universe", "Walter Isaacson", "http://static.bookstck.com/books/einstein-his-life-and-universe-400.jpg"));
        this.messages.add(new Message("Zero to One: Notes on Startups, or How to Build the Future", "Peter Thiel, Blake Masters", "http://static.bookstck.com/books/zero-to-one-400.jpg"));
        this.messages.add(new Message("Tesla: Inventor of the Electrical Age", "W. Bernard Carlson", "http://static.bookstck.com/books/tesla-inventor-of-the-electrical-age-400.jpg"));
        this.messages.add(new Message("Orwell's Revenge: The \"1984\" Palimpsest", "Peter Huber", "http://static.bookstck.com/books/orwells-revenge-400.jpg"));
        this.messages.add(new Message("How to Lie with Statistics", "Darrell Huff", "http://static.bookstck.com/books/how-to-lie-with-statistics-400.jpg"));
        this.messages.add(new Message("Abundance: The Future Is Better Than You Think", "Peter H. Diamandis, Steven Kotler", "http://static.bookstck.com/books/abundance-400.jpg"));
        this.messages.add(new Message("Where Good Ideas Come From", "Steven Johnson", "http://static.bookstck.com/books/where-good-ideas-come-from-400.jpg"));
        this.messages.add(new Message("The Information: A History, A Theory, A Flood", "James Gleick", "http://static.bookstck.com/books/the-information-history-theory-flood-400.jpg"));
        this.messages.add(new Message("Turing's Cathedral: The Origins of the Digital Universe", "George Dyson", "http://static.bookstck.com/books/turing-s-cathedral-400.jpg"));
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.secondary));
        ((TextView) v.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }

    private int removeRequist(Message book) {
        int pos = messages.indexOf(book);
        messages.remove(book);
        adapter.notifyItemRemoved(pos);
        return pos;
    }

    private void addRequist(int pos, Message book) {
        messages.add(pos, book);
        adapter.notifyItemInserted(pos);
    }
}
