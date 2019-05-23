package com.material.doc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ramotion.foldingcell.FoldingCell;
import com.tmall.ultraviewpager.UltraViewPager;

import net.alhazmy13.mediagallery.library.activity.MediaGallery;
import net.alhazmy13.mediagallery.library.views.MediaGalleryView;

import co.dift.ui.SwipeToAction;
import devlight.io.library.ntb.NavigationTabBar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GIGAMOLE on 28.03.2016.
 */
public class BottomTabActivity extends Activity implements MediaGalleryView.OnImageClicked{
    RecyclerView recyclerView;
    InboxAdapter inboxAdapter;
    SwipeToAction swipeToAction;

    List<Message> books = new ArrayList<>();

    ArrayList<String> list;

    private UltraViewPager ultraViewPager;
    private PagerAdapter adapter;
    private UltraViewPager.Orientation gravity_indicator;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_coordinator_ntb);

        initUI();
    }

    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                switch (position){
                    case 0:
                        final View view = LayoutInflater.from(
                                getBaseContext()).inflate(R.layout.info, null, false);
                        container.addView(view);


                        return view;

                    case 1:
                        final View view3 = LayoutInflater.from(
                                getBaseContext()).inflate(R.layout.fragment_album, null, false);
                        container.addView(view3);

                        FloatingActionButton img=(FloatingActionButton) findViewById(R.id.fab);

                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, 200);
                            }
                        });



                        list = getFakeList();
                        MediaGalleryView view4 = (MediaGalleryView) findViewById(R.id.gallery);
                        view4.setImages(list);
                        view4.setOnImageClickListener(new MediaGalleryView.OnImageClicked() {
                            @Override
                            public void onImageClicked(int pos) {
                                MediaGallery.Builder(BottomTabActivity.this,list)
                                        .title("Media Gallery")
                                        .backgroundColor(R.color.white)
                                        .placeHolder(R.drawable.media_gallery_placeholder)
                                        .selectedImagePosition(pos)
                                        .show();
                            }
                        });
                        view4.notifyDataSetChanged();

                        return view3;

                    case 2:
                        final View view5 = LayoutInflater.from(
                                getBaseContext()).inflate(R.layout.graph_fragment, null, false);
                        container.addView(view5);

                        GraphView graph = (GraphView) findViewById(R.id.graph);
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                                new DataPoint(0, 1),
                                new DataPoint(1, 5),
                                new DataPoint(2, 3),
                                new DataPoint(3, 2),
                                new DataPoint(4, 6)
                        });
                        graph.addSeries(series);



                        return view5;
                    case 3:
                        final View view6 = LayoutInflater.from(
                                getBaseContext()).inflate(R.layout.swip, null, false);
                        container.addView(view6);

                        Fresco.initialize(BottomTabActivity.this);


                        recyclerView = (RecyclerView) findViewById(R.id.recycler);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(BottomTabActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);

                        inboxAdapter = new InboxAdapter(books);
                        recyclerView.setAdapter(inboxAdapter);

                        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Message>() {
                            @Override
                            public boolean swipeLeft(final Message itemData) {
                                final int pos = removeBook(itemData);
                                displaySnackbar(itemData.getTitle() + " removed", "Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addBook(pos, itemData);
                                    }
                                });
                                return true;
                            }

                            @Override
                            public boolean swipeRight(Message itemData) {
                                displaySnackbar(itemData.getTitle() + " loved", null, null);
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


                        return view6;

                    case 4:
                        final View view7 = LayoutInflater.from(
                                getBaseContext()).inflate(R.layout.foldable, null, false);
                        container.addView(view7);
                        // get our list view
                        ListView theListView = findViewById(R.id.mainListView);

                        // prepare elements to display
                        final ArrayList<Item> items = Item.getTestingList();

                        // add custom btn handler to first list item
                        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(BottomTabActivity.this, items);

                        // add default btn handler for each request btn on each item if custom handler not found
                        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // set elements to adapter
                        theListView.setAdapter(adapter);

                        // set on click event listener to list view
                        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                // toggle clicked cell state
                                ((FoldingCell) view).toggle(false);
                                // register in adapter that state for selected cell is toggled
                                adapter.registerToggle(pos);
                            }
                        });




                        return view7;

                }

                return null;
            }






        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .title("Heart")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .title("Cup")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .title("Diploma")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
                        .title("Inbox")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .title("Medal")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);

        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (position == 0) {

                }

                if (position == 3) {

                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);


        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#009F90AF"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#9f90af"));
    }

    @Override
    public void onImageClicked(int pos) {

    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }
    private ArrayList getFakeList() {

        ArrayList<String> imagesList = new ArrayList<>();
        Bitmap image = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        image.eraseColor(android.graphics.Color.GREEN);
        imagesList.add(bitMapToString(image));
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/12/23193634/tumblr_oiboua3s6F1slhhf0o1_500.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/08192732/tumblr_oev1qbnble1ted1sho1_500.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/18184202/tumblr_ntyttsx2Y51ted1sho1_500.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/25093310/2016-03-01-roman-drits-barnimages-008-768x512.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/25093310/2016-03-01-roman-drits-barnimages-008-768x512.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/25093331/tumblr_ofz20toUqd1ted1sho1_500.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/25093334/2016-11-21-roman-drits-barnimages-003-768x512.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/02093356/DSF1919-768x512.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/02093347/2016-11-21-roman-drits-barnimages-009-768x512.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/12/16094158/2016-12-05-roman-drits-barnimages-011-768x512.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/12/16094159/tumblr_o2z8oh0Ntt1ted1sho1_1280-683x1024.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/12/23193617/2016-11-13-barnimages-igor-trepeshchenok-01-768x509.jpg");
        imagesList.add("http://static0.passel.co/wp-content/uploads/2016/11/08192739/tumblr_ofem6n49F61ted1sho1_500.jpg");
        return imagesList;
    }

    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void populate() {
        this.books.add(new Message("Einstein: his life and universe", "Walter Isaacson", "http://static.bookstck.com/books/einstein-his-life-and-universe-400.jpg"));
        this.books.add(new Message("Zero to One: Notes on Startups, or How to Build the Future", "Peter Thiel, Blake Masters", "http://static.bookstck.com/books/zero-to-one-400.jpg"));
        this.books.add(new Message("Tesla: Inventor of the Electrical Age", "W. Bernard Carlson", "http://static.bookstck.com/books/tesla-inventor-of-the-electrical-age-400.jpg"));
        this.books.add(new Message("Orwell's Revenge: The \"1984\" Palimpsest", "Peter Huber", "http://static.bookstck.com/books/orwells-revenge-400.jpg"));
        this.books.add(new Message("How to Lie with Statistics", "Darrell Huff", "http://static.bookstck.com/books/how-to-lie-with-statistics-400.jpg"));
        this.books.add(new Message("Abundance: The Future Is Better Than You Think", "Peter H. Diamandis, Steven Kotler", "http://static.bookstck.com/books/abundance-400.jpg"));
        this.books.add(new Message("Where Good Ideas Come From", "Steven Johnson", "http://static.bookstck.com/books/where-good-ideas-come-from-400.jpg"));
        this.books.add(new Message("The Information: A History, A Theory, A Flood", "James Gleick", "http://static.bookstck.com/books/the-information-history-theory-flood-400.jpg"));
        this.books.add(new Message("Turing's Cathedral: The Origins of the Digital Universe", "George Dyson", "http://static.bookstck.com/books/turing-s-cathedral-400.jpg"));
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

    private int removeBook(Message book) {
        int pos = books.indexOf(book);
        books.remove(book);
        inboxAdapter.notifyItemRemoved(pos);
        return pos;
    }

    private void addBook(int pos, Message book) {
        books.add(pos, book);
        inboxAdapter.notifyItemInserted(pos);
    }

}
