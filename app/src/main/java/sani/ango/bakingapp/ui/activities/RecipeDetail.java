/*In the name of Allah*/
package sani.ango.bakingapp.ui.activities;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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
import sani.ango.bakingapp.ui.fragments.StepListFragment;

//import sani.ango.bakingapp.data.presenter.DetailPresenter;

public class RecipeDetail extends AppCompatActivity {
    @Bind(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    private SimpleExoPlayer mExoPlayer;
    //private DetailPresenter presenter;
    private AppController app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        app = AppController.getInstance();

        Bundle extras = getIntent().getExtras();
        Recipe recipe = null;
        int index = 0;
        if (extras != null && extras.containsKey("recipe")){
            recipe = extras.getParcelable("recipe");
            index = extras.getInt("index");
        }

        //Toolbar menuToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        //setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle(recipe.getRecipeName());

        StepListFragment fragment = new StepListFragment();
        fragment.setStepTextView(recipe.getSteps().get(index).getStepDescription());

        //presenter  = new DetailPresenter();
        initializePlayer(Uri.parse(recipe.getSteps().get(index).getVideoURL()));
        //mExoPlayer.addListener(presenter);
        showSteps(fragment);
    }

    public void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
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


    private void showSteps(StepListFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.step_container, fragment)
                .commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer(mExoPlayer);
    }

    public void onPause(){
        super.onPause();
        releasePlayer(mExoPlayer);
    }

    public void onStop(){
        super.onStop();
        releasePlayer(mExoPlayer);
    }

    public void releasePlayer(SimpleExoPlayer mExoPlayer) {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
