package luhui1hao.xyz.dynamicanimatortest;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

class DynamicAnimatorView0 extends View {
    private Paint paint;
    private Position destinationPosition;
    private Position currentPosition;
    private static final float RADIUS = dpToPixel(20);

    public DynamicAnimatorView0(Context context) {
        super(context);
    }

    public DynamicAnimatorView0(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicAnimatorView0(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#03DAC5"));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        destinationPosition = new Position(RADIUS,RADIUS);
        currentPosition = new Position(RADIUS,RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dpToPixel(2));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dpToPixel(3));
        canvas.drawCircle(destinationPosition.getX(), destinationPosition.getY(), RADIUS, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(currentPosition.getX(), currentPosition.getY(), RADIUS, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                destinationPosition.setX(event.getX());
                destinationPosition.setY(event.getY());
                invalidate();
            }
                break;
            case MotionEvent.ACTION_MOVE:{
                destinationPosition.setX(event.getX());
                destinationPosition.setY(event.getY());
                invalidate();

//                Log.e("luhui", "MOVE------"+destinationPosition.toString());
            }
                break;
            case MotionEvent.ACTION_UP:{
//                ObjectAnimator animator = ObjectAnimator.ofObject(this, "currentPosition",
//                        new PositionEvaluator(), new PointF(currentPosition.getX(), currentPosition.getY()), new PointF(destinationPosition.getX(), destinationPosition.getY()));
//                animator.start();
            }
                break;
        }
        return true;
    }
    
    class Position{
        private float x;
        private float y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }

    private class PositionEvaluator implements TypeEvaluator<Position> {
        Position newPoint = new Position(0,0);

        @Override
        public Position evaluate(float fraction, Position startValue, Position endValue) {
            float x = startValue.x + (fraction * (endValue.x - startValue.x));
            float y = startValue.y + (fraction * (endValue.y - startValue.y));

            newPoint.setX(x);
            newPoint.setY(y);

            return newPoint;
        }
    }
}
