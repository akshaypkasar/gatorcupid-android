package com.example.surfacepro2.gatorscupid.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.surfacepro2.gatorscupid.R;
import com.example.surfacepro2.gatorscupid.model.Card;
import com.example.surfacepro2.gatorscupid.model.User;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipeDirectionalView;
import com.mindorks.placeholderview.Utils;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import com.example.surfacepro2.gatorscupid.util.CardUtil;

import org.json.JSONException;

public class BrowseProfilesActivity extends AppCompatActivity implements Card.Callback {

    private SwipeDirectionalView swipeView;
    private Context context;
    private int mAnimationDuration = 300;
    private boolean isToUndo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_profiles);

        swipeView = (SwipeDirectionalView)findViewById(R.id.swipeCardView);
        context = getApplicationContext();

        int bottomMargin = CardUtil.dpToPx(160);
        Point windowSize = CardUtil.getDisplaySize(getWindowManager());
        swipeView.getBuilder()
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setSwipeVerticalThreshold(Utils.dpToPx(50))
                .setSwipeHorizontalThreshold(Utils.dpToPx(50))
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setSwipeAnimTime(mAnimationDuration)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.card_swipe_like_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.card_swipe_unlike_msg_view));


        Log.i("GatorCupid","Creating swipeView");
        Point cardViewHolderSize = new Point(windowSize.x, windowSize.y - bottomMargin);
        User user = (User)getIntent().getSerializableExtra("user");

        try {
            CardUtil.loadProfiles(getApplicationContext(),user, swipeView, cardViewHolderSize, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.unlikeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeView.doSwipe(false);
                //TODO call api dislike
            }
        });

        findViewById(R.id.likeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeView.doSwipe(true);
                //TODO call api like
            }
        });

        swipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                if (isToUndo) {
                    isToUndo = false;
                    swipeView.undoLastSwipe();
                }
            }
        });
    }

    @Override
    public void onSwipeUp() {
        Toast.makeText(this, "SUPER LIKE! Show any dialog here.", Toast.LENGTH_SHORT).show();
        isToUndo = true;
    }
}
