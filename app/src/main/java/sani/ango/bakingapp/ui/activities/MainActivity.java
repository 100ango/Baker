package sani.ango.bakingapp.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sani.ango.bakingapp.R;
import sani.ango.bakingapp.app.AppController;
import sani.ango.bakingapp.data.model.Recipe;
import sani.ango.bakingapp.data.presenter.MainPresenter;
import sani.ango.bakingapp.util.Util;
import sani.ango.bakingapp.util.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity implements MainAdapter.ClickListener{

    @Bind(R.id.recipe_list)
    RecyclerView mRecipeList;

    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private List<Recipe> data;
    private MainAdapter adapter;
    private AppController app;
    private MainPresenter presenter;
    private Gson gsonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getString(R.string.main_title));

        data = new ArrayList<>();
        app = AppController.getInstance();
        presenter = new MainPresenter(app, this);
        ButterKnife.bind(this);

        if (savedInstanceState != null){
            data = savedInstanceState.getParcelableArrayList("data");
        }

        if (data.size() == 0) {
            downloadJSonData();
        }else{
            loadRecipeList();
        }

        int portrait = getResources().getConfiguration().orientation;

        if (presenter.isPhoneTablet() || portrait == Configuration.ORIENTATION_LANDSCAPE){
            mRecipeList.setLayoutManager(new GridLayoutManager(this, 3));
        }
        else {
            mRecipeList.setLayoutManager(new LinearLayoutManager(this));
        }
        mRecipeList.setHasFixedSize(true);


    }


    public void downloadJSonData(){
        String url = Util.getJSonURL(this);
        gsonParser = app.getGSonBuilder();
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonReq = new JsonArrayRequest(
                url,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                progressBar.setVisibility(View.INVISIBLE);
                noDataLayout.setVisibility(View.INVISIBLE);
                parse(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressBar.setVisibility(View.INVISIBLE);
                noDataLayout.setVisibility(View.VISIBLE);
            }
        });

        jsonReq.setRetryPolicy(new DefaultRetryPolicy(1000, 1, 1.0f));
        app.addToRequestQueue(jsonReq);
    }

    void parse(String response){
        Type listType = new TypeToken<ArrayList<Recipe>>() {
        }.getType();
        data = gsonParser.fromJson(response, listType);

        if (data != null) {
            loadRecipeList();
        }
    }

    public  void loadRecipeList(){
        adapter = new MainAdapter(data, this);
        mRecipeList.setAdapter(adapter);
    }

    @Override
    public void onClick(int index) {
        Intent masterListIntent = new Intent(this, RecipeMasterActivity.class);
        masterListIntent.putExtra("recipe", data.get(index));
        masterListIntent.putExtra("index", index);
        startActivity(masterListIntent);
    }

    @OnClick(R.id.refreshButton)
    public void refresh(){
        noDataLayout.setVisibility(View.INVISIBLE);
        downloadJSonData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("data", (ArrayList<Recipe>) data);
    }
}
