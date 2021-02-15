package com.ayata.purvamart.ui.Fragment.shop.stories;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;
import com.teresaholfeld.stories.StoriesProgressView;

import androidx.appcompat.app.AppCompatActivity;

public class StoryActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {
    //stories
    private static final int PROGRESS_COUNT = 4;
    StoriesProgressView storiesProgressView;
    ImageView image;
    private int counter = 0;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_story);
        storiesProgressView = findViewById(R.id.storiesProgressView);
        image = findViewById(R.id.image);
        storiesProgressView.setStoriesCount(PROGRESS_COUNT);
        storiesProgressView.setStoryDuration(3000L);
        // or
        // storiesProgressView.setStoriesCountWithDurations(durations);

        storiesProgressView.setStoriesListener(this);

        startStory();

        // bind reverse view
        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
        ImageView ivClose = findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void startStory() {

        counter = 0;//from which view to start
        storiesProgressView.startStories(counter);
        Glide.with(this).load(resources[counter]).into(image);
    }

    @Override
    public void onNext() {
        Toast.makeText(this, "onNext", Toast.LENGTH_SHORT).show();
        image.setImageResource(resources[++counter]);

    }

    @Override
    public void onPrev() {
        // Call when finished revserse animation.
        Toast.makeText(this, "onPrev", Toast.LENGTH_SHORT).show();
        if (counter - 1 < 0) return;
                image.setImageResource(resources[--counter]);
    }

    @Override
    public void onComplete() {
        Toast.makeText(this, "onComplete", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}