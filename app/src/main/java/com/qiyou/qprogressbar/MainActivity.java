package com.qiyou.qprogressbar;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qiyou.qprogressbars.QProgressBar;

public class MainActivity extends AppCompatActivity {

    private QProgressBar mQProgressbar;
    private QProgressBar mCProgressbar;
    private QProgressBar mAProgressbar;
    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.getInstance().getRefWatcher().watch(this);
        mQProgressbar = findViewById(R.id.l_progressbar);
        mCProgressbar = findViewById(R.id.c_progressbar);
        mAProgressbar = findViewById(R.id.a_progressbar);
        mBtnStart = findViewById(R.id.btn_start);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator animator = ValueAnimator.ofInt(0, 100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int progress = (Integer) valueAnimator.getAnimatedValue();
                        mQProgressbar.setProgress(progress);
                        mCProgressbar.setProgress(progress);
                        mAProgressbar.setProgress(progress);
                    }
                });
                animator.setDuration(5000);
                animator.start();
            }
        });
    }
}
