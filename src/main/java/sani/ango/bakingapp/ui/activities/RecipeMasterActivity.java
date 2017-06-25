/*In the name of Allah*/
package sani.ango.bakingapp.ui.activities;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        recipe = null;

        if (extras != null && extras.containsKey("recipe")){
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

    /**
     * Release ExoPlayer.
     */
    public void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
