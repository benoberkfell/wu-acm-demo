package com.example.bestsellers;

import android.content.Intent;
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
import com.example.bestsellers.models.BestsellerList;
import com.example.bestsellers.models.BestsellerListsResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String LOG_TAG = MainActivity.class.getName();

    private ArrayList<BestsellerList> bestsellerLists = new ArrayList<>();
    private RecyclerView recyclerView;
    private BestsellerListAdapter bestsellerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("NYT Bestsellers");
        getSupportActionBar().setSubtitle("Choose a list");

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bestsellerListAdapter = new BestsellerListAdapter();
        recyclerView.setAdapter(bestsellerListAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        BestsellerClient client = BestsellerClient.getInstance();
        client.getBestsellerLists(new BestsellerClient.BestsellerClientCallback<BestsellerListsResponse>() {
            @Override
            public void onSuccess(BestsellerListsResponse response) {
                bestsellerLists.clear();
                bestsellerLists.addAll(response.getResults());
                bestsellerListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(BestsellerClient.BestsellerException error) {
                Log.e(LOG_TAG, "uh oh, couldn't get list of bestseller lists.", error);
                Snackbar.make(findViewById(android.R.id.content), R.string.error_loading_bestseller_lists, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    class BestsellerListAdapter extends RecyclerView.Adapter<BestsellerListItem> {

        @NonNull
        @Override
        public BestsellerListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bestseller_list_item, parent, false);

            return new BestsellerListItem(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull BestsellerListItem holder, int position) {
            BestsellerList list = bestsellerLists.get(position);
            holder.setListLastUpdated(list.getNewestPublishedDate());
            holder.setListName(list.getName());
            holder.setInfoForFetching(list.getEncodedName(), list.getNewestPublishedDate());
        }

        @Override
        public int getItemCount() {
            return bestsellerLists.size();
        }
    }

    class BestsellerListItem extends RecyclerView.ViewHolder {

        private TextView listNameTextView;
        private TextView updatedDateTextView;

        public BestsellerListItem(View itemView) {
            super(itemView);

            listNameTextView = itemView.findViewById(R.id.bestseller_list_name);
            updatedDateTextView = itemView.findViewById(R.id.latest_list_date);
        }

        public void setListName(String listName) {
            listNameTextView.setText(listName);
        }

        public void setListLastUpdated(String date) {
            updatedDateTextView.setText(String.format("Latest: %s", date));
        }

        public void setInfoForFetching(final String listSlug, final String listDate) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BestsellerListActivity.class);
                    intent.putExtra(BestsellerListActivity.EXTRA_LIST_NAME, listSlug);
                    intent.putExtra(BestsellerListActivity.EXTRA_LIST_DATE, listDate);

                    startActivity(intent);
                }
            });
        }
    }
}
