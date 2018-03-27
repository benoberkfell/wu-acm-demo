package com.example.bestsellers;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bestsellers.client.BestsellerClient;
import com.example.bestsellers.models.BestsellerResponse;
import com.example.bestsellers.models.Book;

import java.util.ArrayList;
import java.util.Locale;

public class BestsellerListActivity extends AppCompatActivity {

    private static final String LOG_TAG = BestsellerListActivity.class.getName();

    public static String EXTRA_LIST_NAME = "EXTRA_LIST_NAME";
    public static String EXTRA_LIST_DATE = "EXTRA_LIST_DATE";

    private ArrayList<Book> bookEntries = new ArrayList<>();
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestseller_list);

        adapter = new BookAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();

        Intent myIntent = getIntent();
        String listName = myIntent.getStringExtra(EXTRA_LIST_NAME);
        String publishDate = myIntent.getStringExtra(EXTRA_LIST_DATE);

        BestsellerClient bestsellerClient = BestsellerClient.getInstance();

        bestsellerClient.getBestsellersForListAndDate(listName, publishDate, new BestsellerClient.BestsellerClientCallback<BestsellerResponse>() {
            @Override
            public void onSuccess(BestsellerResponse response) {
                bookEntries.clear();
                bookEntries.addAll(response.getBooks());

                getSupportActionBar().setTitle(response.getDisplayName());
                getSupportActionBar().setSubtitle(response.getPublishedDate());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(BestsellerClient.BestsellerException error) {
                Log.e(LOG_TAG, "Error loading this bestseller list", error);
                Snackbar.make(findViewById(android.R.id.content), R.string.error_loading_list_contents, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    class BookAdapter extends RecyclerView.Adapter<BookItem> {

        @NonNull
        @Override
        public BookItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bestseller_book_list_item, parent, false);

            return new BookItem(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull BookItem holder, int position) {
            Book book = bookEntries.get(position);

            holder.setRank(book.getRank());
            holder.setAuthor(book.getAuthor());
            holder.setTitle(book.getTitle());
            holder.setAmazonUrl(book.getAmazonUrl());
        }

        @Override
        public int getItemCount() {
            return bookEntries.size();
        }
    }


    class BookItem extends RecyclerView.ViewHolder {

        private TextView rankTextView;
        private TextView titleTextView;
        private TextView authorTextView;

        BookItem(View itemView) {
            super(itemView);

            rankTextView = itemView.findViewById(R.id.rank);
            titleTextView = itemView.findViewById(R.id.title);
            authorTextView = itemView.findViewById(R.id.author);
        }

        public void setTitle(String title) {
            titleTextView.setText(title);
        }

        public void setRank(int rank) {
            rankTextView.setText(String.format(Locale.US, "%d.", rank));
        }

        public void setAuthor(String author) {
            authorTextView.setText(author);
        }

        public void setAmazonUrl(final String url) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
        }
    }
}
