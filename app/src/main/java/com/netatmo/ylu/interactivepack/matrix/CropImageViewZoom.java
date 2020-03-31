package com.netatmo.ylu.interactivepack.matrix;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatImageView;
import com.netatmo.ylu.interactivepack.R;

public class CropImageViewZoom extends AppCompatImageView {
    private final Matrix matrix = new Matrix();
    private final Matrix currentMatrix = new Matrix();
    private Bitmap bitmap;
    private float oldLength;
    private final PointF midPoint = new PointF();
    private boolean isMorePoint;
    private float downX;
    private float downY;
    private double oldRotate;


    public CropImageViewZoom(Context context) {
        this(context, null);

    }

    public CropImageViewZoom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CropImageViewZoom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.my_image);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(bitmap, matrix, null);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                currentMatrix.set(matrix);
                isMorePoint = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isMorePoint = true;
                currentMatrix.set(matrix);
                oldLength = this.getLength(event);
                oldRotate = this.getRotate(event);
                this.getMidPoint(midPoint, event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMorePoint) {
                    matrix.set(currentMatrix);
                    double rotate = this.getRotate(event) - oldRotate;

                    matrix.setRotate((float) rotate, midPoint.x, midPoint.y);
                    float length = this.getLength(event) / oldLength;
                    matrix.postScale(length, length, midPoint.x, midPoint.y);

                } else {
                    matrix.set(currentMatrix);
                    float x = event.getX() - downX;
                    float y = event.getY() - downY;
                    matrix.postTranslate(x, y);
                }

                this.invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isMorePoint = false;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 取手势中心点
     */
    private void getMidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private float getLength(MotionEvent event) {
        float pointX1 = event.getX(0);
        float pointX2 = event.getX(1);
        float pointY1 = event.getY(0);
        float pointY2 = event.getY(1);

        return (float) Math.sqrt(Math.pow(pointX1 - pointX2, 2) + Math.pow(pointY1 - pointY2, 2));
    }

    private double getRotate(MotionEvent event) {
        float pointX1 = event.getX(0);
        float pointX2 = event.getX(1);
        float pointY1 = event.getY(0);
        float pointY2 = event.getY(1);

        return Math.toDegrees(Math.atan2((pointY1 - pointY2), (pointX1 - pointX2)));
    }
}