/*In the name of Allah*/
package sani.ango.bakingapp.data.presenter;


import android.app.Activity;
import android.content.res.Configuration;
import android.widget.Toast;

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

import sani.ango.bakingapp.app.AppController;
import sani.ango.bakingapp.data.model.Recipe;
import sani.ango.bakingapp.util.Util;

public class MainPresenter {
    private AppController app;
    Gson gsonParser;
    private List<Recipe> recipeData;
    private Activity activity;

    public MainPresenter(AppController app, Activity activity) {
        this.app = app;
        this.activity = activity;
    }

    public void downloadJSonData(){
        String url = Util.getJSonURL(activity);
        gsonParser = app.getGSonBuilder();

        JsonArrayRequest jsonReq = new JsonArrayRequest(
                url,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                parse(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(app.getBaseContext(), "Couldn't get movies", Toast.LENGTH_SHORT).show();
            }
        });

        jsonReq.setRetryPolicy(new DefaultRetryPolicy(1000, 1, 1.0f));
        app.addToRequestQueue(jsonReq);
    }

    void parse(String response){
        Type listType = new TypeToken<ArrayList<Recipe>>() {
        }.getType();
        recipeData = gsonParser.fromJson(response, listType);

        if (recipeData != null) {
            //mainFragment.loadRecipeList(recipeData);
        }
    }

    public boolean isPhoneTablet(){
        boolean isTablet = false;
        int screenSize = app.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;

        if(screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            isTablet = true;
        }

        return isTablet;
    }

}
