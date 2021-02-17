package com.ayata.purvamart.ui.Fragment.shop.stories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;
import com.teresaholfeld.stories.StoriesProgressView;

import androidx.fragment.app.Fragment;

public class FragmentStory extends Fragment implements StoriesProgressView.StoriesListener {
    //stories
    private static final int PROGRESS_COUNT = 4;
    private static final String TAG ="FragmentStory" ;
    StoriesProgressView storiesProgressView;
    ImageView image;
    private static int counter = 0;
    private int[] resources = {
            R.drawable.signup,
            R.drawable.esewa,
            R.drawable.esewa,
            R.drawable.esewa,
    };
    private Long pressTime = 0L;
    private Long limit = 500L;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        storiesProgressView = view.findViewById(R.id.storiesProgressView);
        image = view.findViewById(R.id.image);
        View reverse = view.findViewById(R.id.reverse);
        View skip = view.findViewById(R.id.skip);
        ImageView ivClose = view.findViewById(R.id.ivClose);

        //
        storiesProgressView.setStoriesCount(PROGRESS_COUNT);
        storiesProgressView.setStoryDuration(3000L);
        // or
        // storiesProgressView.setStoriesCountWithDurations(durations);
        startStory();
        storiesProgressView.setStoriesListener(this);
        // bind reverse view
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        storiesProgressView.resume();

    }

    private void startStory() {

        counter = 0;//from which view to start
        storiesProgressView.startStories(counter);
        Glide.with(this).load(resources[counter]).into(image);
    }

    @Override
    public void onNext() {
        Toast.makeText(getContext(), "onNext", Toast.LENGTH_SHORT).show();
        image.setImageResource(resources[++counter]);

    }

    @Override
    public void onPrev() {
        // Call when finished revserse animation.
        Toast.makeText(getContext(), "onPrev", Toast.LENGTH_SHORT).show();
        if (counter - 1 < 0) return;
        image.setImageResource(resources[--counter]);
    }

    @Override
    public void onComplete() {
        Toast.makeText(getContext(), "onComplete", Toast.LENGTH_SHORT).show();
//        finish();
    }

    @Override
    public void onDestroy() {
        // Very important !
        Log.d(TAG, "onDestroy: ");
        storiesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        storiesProgressView.pause();
        super.onPause();
    }
}