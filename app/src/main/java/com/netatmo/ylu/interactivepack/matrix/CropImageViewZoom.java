package com.netatmo.ylu.interactivepack.matrix;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.Arrays;

public class CropImageViewZoom extends AppCompatImageView {
    /**
     * The downMatrix save the matrix data when receive action down.
     */
    private final Matrix downMatrix = new Matrix();
    private float downPointerDistance;
    private PointF downCenterPoint = new PointF();
    private boolean isMorePoint;
    private float downX;
    private float downY;
    private double downPointerAngle;
    private final boolean isRotateEnable = false;
    private final boolean isTransitionEnable = true;
    private final boolean isScaleEnable = true;
    private final float[] points = new float[4];


    public CropImageViewZoom(Context context) {
        this(context, null);

    }

    public CropImageViewZoom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CropImageViewZoom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        points[2] = this.getMeasuredWidth();
        points[3] = this.getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //copy current image matrix
        Matrix matrix = new Matrix();
        matrix.set(this.getImageMatrix());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downMatrix.set(matrix);
                isMorePoint = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isMorePoint = true;
                downMatrix.set(matrix);
                downPointerDistance = this.getDistance(event);
                downPointerAngle = this.getHorizontalAngle(event);
                downCenterPoint = this.getCenterPoint(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMorePoint) {
                    //Multi finger gesture
                    matrix.set(downMatrix);
                    //rotate
                    if (isRotateEnable) {
                        double rotate = this.getHorizontalAngle(event) - downPointerAngle;
                        matrix.postRotate((float) rotate, downCenterPoint.x, downCenterPoint.y);
                    }
                    //scale
                    if (isScaleEnable) {
                        float scale = this.getDistance(event) / downPointerDistance;
                        matrix.postScale(scale, scale, downCenterPoint.x, downCenterPoint.y);
                    }
                    //transition
                    if (isTransitionEnable) {
                        PointF centerPoint = this.getCenterPoint(event);
                        float deltaX = centerPoint.x - downCenterPoint.x;
                        float deltaY = centerPoint.y - downCenterPoint.y;
                        matrix.postTranslate(deltaX, deltaY);
                    }

                } else {
                    //Transition(the single finger drag case)
                    //The position diff calculation below is based on the ACTION_DOWN,
                    // so the matrix we base on must be the one which is saved from ACTION_DOWN
                    matrix.set(downMatrix);
                    if (isTransitionEnable) {
                        float deltaX = event.getX() - downX;
                        float deltaY = event.getY() - downY;
                        matrix.postTranslate(deltaX, deltaY);
                    }

                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() <= 2) {
                    downMatrix.set(matrix);
                    isMorePoint = false;
                    int indexUp = event.getActionIndex();
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (indexUp != i) {
                            //save the position the finger staying as the pivot
                            downX = event.getX(i);
                            downY = event.getY(i);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        if (!this.isMatrixOutRange(matrix)) {
            this.setImageMatrix(matrix);
            this.invalidate();
        }
        return true;
    }

    private boolean isMatrixOutRange(@NonNull Matrix matrix) {
        //Log.e("Previous point", String.format("left %f, top %f, right %f, bottom %f", points[0], points[1], points[2], points[3]));
        float[] nextPoints = Arrays.copyOf(points, points.length);
        matrix.mapPoints(nextPoints);
        //Log.e("Next point", String.format("left %f, top %f, right %f, bottom %f", nextPoints[0], nextPoints[1], nextPoints[2], nextPoints[3]));
        if (nextPoints[0] > 0 || nextPoints[1] > 0 || nextPoints[2] < this.getWidth() || nextPoints[3] < this.getHeight()) {
            return true;
        }
        return false;
    }

    @NonNull
    private PointF getCenterPoint(@NonNull MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }

    private float getDistance(MotionEvent event) {
        float pointX1 = event.getX(0);
        float pointX2 = event.getX(1);
        float pointY1 = event.getY(0);
        float pointY2 = event.getY(1);

        return (float) Math.sqrt(Math.pow(pointX1 - pointX2, 2) + Math.pow(pointY1 - pointY2, 2));
    }

    /**
     * Get the angle of the pointer from horizontal axis
     *
     * @param event the motion event
     * @return the angle (degree)
     */
    private double getHorizontalAngle(MotionEvent event) {
        float pointX1 = event.getX(0);
        float pointX2 = event.getX(1);
        float pointY1 = event.getY(0);
        float pointY2 = event.getY(1);

        return Math.toDegrees(Math.atan2((pointY1 - pointY2), (pointX1 - pointX2)));
    }
}