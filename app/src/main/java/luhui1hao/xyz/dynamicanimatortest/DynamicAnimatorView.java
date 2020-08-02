package luhui1hao.xyz.dynamicanimatortest;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

public class DynamicAnimatorView extends RelativeLayout {
    private Context mContext;
    private SpringAnimation animateX;
    private SpringAnimation animateY;
    private ImageView ivLead;
    private ImageView ivFollow;
    private float mDampingRatio = 1.0f;
    private float mStiffness = 15.0f;
    private float startX;
    private float startY;
    private static final float RADIUS = dpToPixel(15);

    public DynamicAnimatorView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public DynamicAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public DynamicAnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dynamic_animator_view, (ViewGroup) getParent(), false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
        ivLead = view.findViewById(R.id.iv_lead);
        ivFollow = view.findViewById(R.id.iv_follow);

        animateX = new SpringAnimation(ivFollow, DynamicAnimation.X, ivLead.getX());
        animateY = new SpringAnimation(ivFollow, DynamicAnimation.Y, ivLead.getY());
        animateX.getSpring().setDampingRatio(mDampingRatio).setStiffness(mStiffness);
        animateY.getSpring().setDampingRatio(mDampingRatio).setStiffness(mStiffness);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        startX = ivLead.getX();
        startY = ivLead.getY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:{
                ivLead.setX(event.getX() - RADIUS);
                ivLead.setY(event.getY() - RADIUS);

                animateX.animateToFinalPosition(ivLead.getX());
                animateY.animateToFinalPosition(ivLead.getY());
            }
            break;
        }
        return true;
    }



    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }
}
