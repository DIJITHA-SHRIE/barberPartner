package com.hair_beauty.partner.utility;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

public class AnimationUtil {
    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown) {

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown==true ? 100: -100, 0);
        animatorTranslateY.setDuration(1000);

        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();

    }
}