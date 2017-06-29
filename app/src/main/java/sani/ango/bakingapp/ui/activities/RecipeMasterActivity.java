/*In the name of Allah*/
package sani.ango.bakingapp.ui.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import sani.ango.bakingapp.R;
import sani.ango.bakingapp.app.AppController;
import sani.ango.bakingapp.data.model.Recipe;
import sani.ango.bakingapp.ui.fragments.IngredientFragment;
import sani.ango.bakingapp.ui.fragments.StepListFragment;
import sani.ango.bakingapp.util.adapter.MasterAdapter;

public class RecipeMasterActivity extends AppCompatActivity
        implements MasterAdapter.ClickListener{

    @Bind(R.id.master_list)
    RecyclerView list;

    private AppController app;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private Recipe recipe;
    private boolean twoPane;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ButterKnife.bind(this);
        pref = getSharedPreferences("last_visited", MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        int index = 0;
        recipe = null;

        if (extras != null && extras.containsKey("recipe")){
            index = extras.getInt("index");
            recipe = extras.getParcelable("recipe");
        }

        getSupportActionBar().setTitle("  " + recipe.getRecipeName());

        if (findViewById(R.id.two_pane_layout) != null){
            app = AppController.getInstance();
            twoPane = true;
            mPlayerView = (SimpleExoPlayerView)findViewById(R.id.playerView);
            initializePlayer(Uri.parse(recipe.getSteps().get(0).getVideoURL()));
            createStepFragment(0);
        }

        IngredientFragment fragment = new IngredientFragment();
        fragment.setIngredient(recipe.getIngredients());
        attachFragment(fragment, R.id.ingredient_container);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("icon_index", index);
        editor.putString("ingredient", fragment.getIngredientList());
        editor.commit();

        Intent intent = new Intent(this, BakingAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), BakingAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);

        MasterAdapter adapter = new MasterAdapter(recipe.getSteps(), this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.hasFixedSize();
        list.setAdapter(adapter);
    }

    public void createStepFragment(int index){
        StepListFragment fragment = new StepListFragment();
        fragment.setStepTextView(recipe.getSteps().get(index).getStepDescription());
        attachFragment(fragment, R.id.step_container);
    }

    @Override
    public void onClick(int index) {
        if (twoPane){
            //releasePlayer();
            StepListFragment fragment = new StepListFragment();
            fragment.setStepTextView(recipe.getSteps().get(index).getStepDescription());
            detachFragment(fragment, R.id.step_container);
            releasePlayer();
            initializePlayer(Uri.parse(recipe.getSteps().get(index).getVideoURL()));
        }
        else {
            Intent masterListIntent = new Intent(this, RecipeDetail.class);
            masterListIntent.putExtra("recipe", recipe);
            masterListIntent.putExtra("index", index);
            startActivity(masterListIntent);
        }
    }


    public void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this,
                    app.getTrackSelector(), app.getLoadControl());

            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void attachFragment(Fragment fragment, int container) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(container, fragment)
                .commit();
    }

    private void detachFragment(Fragment fragment, int container) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (twoPane) {
            releasePlayer();
        }
    }

    public void onPause(){
        super.onPause();
        releasePlayer();
    }

    public void onStop(){
        super.onStop();
        releasePlayer();
    }

    /**
     * Release ExoPlayer.
     */
    public void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    public SharedPreferences getSharedPrefernces(){
        return pref;
    }
}
