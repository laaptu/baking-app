package com.laaptu.baking.ui.recipedetail.steps.detail.presentation;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.laaptu.baking.R;
import com.laaptu.baking.common.image.ImageLoadOptions;
import com.laaptu.baking.common.image.ImageLoader;
import com.laaptu.baking.common.ui.BaseFragment;
import com.laaptu.baking.data.models.Step;

import org.parceler.Parcels;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import timber.log.Timber;

import static com.laaptu.baking.utils.GeneralUtils.isValidUrl;

public class StepDetailFragment extends BaseFragment {

    @BindView(R.id.container_description) View containerDescription;

    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView exoPlayerView;

    @BindView(R.id.iv_step_thumb) ImageView ivStepThumb;

    @BindView(R.id.txt_instruction) TextView txtInstruction;

    @Inject ImageLoader imageLoader;

    private static final String STEP = "Step";
    private static final String PLAYER_POSITION = "PLAYER_POSITION";
    private static final String PLAYER_AGENT = "Android-recipes-player";
    private Step step;
    private long playerPosition = 0;
    private ImageLoadOptions.Builder imageLoadOptionsBuilder = new ImageLoadOptions.Builder();

    private SimpleExoPlayer exoPlayer;


    public static StepDetailFragment getInstance(Step step) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP, Parcels.wrap(step));
        stepDetailFragment.setArguments(bundle);
        return stepDetailFragment;
    }

    private static Step getStepFromIntent(Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(STEP));
    }

    @Override protected int getLayoutId() {
        return R.layout.recipe_step_detail;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        playerPosition = getPlayerPosition(savedInstanceState);
    }

    @Override public void onResume() {
        super.onResume();
        step = getStepFromIntent(getArguments());
        if (step == null) {
            Timber.e("Doesn't contain any valid Step");
            return;
        }
        setupView();
    }

    private long getPlayerPosition(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(PLAYER_POSITION)) {
            return savedInstanceState.getLong(PLAYER_POSITION, 0);
        }
        return 0;
    }

    private void setupView() {
        txtInstruction.setText(step.description);
        if (isValidUrl(step.videoURL)) {
            exoPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(step.videoURL));
        } else {
            exoPlayerView.setVisibility(View.GONE);
            setupImage(step.thumbnailURL);
        }
    }

    private void setupImage(String imageUrl) {
        if (isValidUrl(imageUrl)) {
            ivStepThumb.setVisibility(View.VISIBLE);
            imageLoadOptionsBuilder.setImageUrl(step.thumbnailURL)
                    .setErrorDrawable(R.drawable.ic_image_error);
            imageLoader.loadImage(imageLoadOptionsBuilder.build(), ivStepThumb);
        } else {
            ivStepThumb.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            exoPlayerView.setPlayer(exoPlayer);

            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), PLAYER_AGENT,
                            bandwidthMeter);
            MediaSource videoSource =
                    new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);

            exoPlayer.prepare(videoSource);

            if (playerPosition != 0) {
                exoPlayer.seekTo(playerPosition);
            }

            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playerPosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(PLAYER_POSITION, playerPosition);
        super.onSaveInstanceState(outState);
    }

    @Override public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
