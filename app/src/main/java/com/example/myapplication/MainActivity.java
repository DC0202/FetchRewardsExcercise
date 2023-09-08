package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private final String JSON_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    private ProgressBar progressBar;
    private List<Item> items = new ArrayList<>();

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fetch Rewards Exercise");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MenuDebug", "onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MenuDebug", "onOptionsItemSelected called with id: " + item.getItemId());
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            reverseList(recyclerView);
            return true;
        } else if (id == R.id.action_filter) {
            showFilterDialog(recyclerView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void reverseList(RecyclerView recyclerView) {
        Collections.reverse(items);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void showFilterDialog(RecyclerView recyclerView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort Data")
                .setItems(new String[]{"Name (Only)", "List + Name(Default Task)"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            items = sortItems("Name");
                            displayData(recyclerView);
                            break;
                        case 1:
                            items = sortItems("Both");
                            displayData(recyclerView);
                            break;
                    }
                });
        builder.show();
    }

    private List<Item> sortItems(String sortBy) {
        switch (sortBy) {
            case "Name":
                return items.stream()
                        .sorted(Comparator.comparing(Item::getId))
                        .collect(Collectors.toList());
            case "Both":
            default:
                return items.stream()
                        .sorted(Comparator.comparingInt(Item::getListId)
                                .thenComparing(Item::getId))
                        .collect(Collectors.toList());
        }
    }

    private void fetchData(RecyclerView recyclerView) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(JSON_URL).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    parseData(jsonResponse, recyclerView);
                    runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                }
            }
        });
    }

    private void parseData(String jsonData, RecyclerView recyclerView) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object nameObj = jsonObject.get("name");
                if (nameObj != JSONObject.NULL) {
                    String name = String.valueOf(nameObj);
                    if (!name.trim().isEmpty()) {
                        items.add(new Item(jsonObject.getInt("id"), jsonObject.getInt("listId"), name));
                    }
                }
            }
            items = sortItems("Both");
            displayData(recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayData(RecyclerView recyclerView) {
        runOnUiThread(() -> {
            ItemAdapter adapter = new ItemAdapter(items);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }
}
