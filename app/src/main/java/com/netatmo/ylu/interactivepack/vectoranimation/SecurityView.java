package com.netatmo.ylu.interactivepack.vectoranimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;
import com.netatmo.ylu.interactivepack.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SecurityView extends View {

    private final List<PathUnit> pathUnits = new ArrayList<>();
    private final Paint paint = new Paint();
    private final Path dst = new Path();


    private float mAnimatedValue;

    public SecurityView(Context context) {
        super(context);
        this.init(context);
    }

    public SecurityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public SecurityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public SecurityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context);
    }

    private void init(Context context) {

        try {
            InputStream is = context.getResources().openRawResource(R.raw.ic_security);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(is);
            Element rootElement = doc.getDocumentElement();
            NodeList items = rootElement.getElementsByTagName("path");
            for (int i = 0; i < items.getLength(); i++) {
                Element element = (Element) items.item(i);
                String pathData = element.getAttribute("android:pathData");
                pathUnits.add(new PathUnit(PathParser.createPathFromPathData(pathData)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (Float) animation.getAnimatedValue();
                SecurityView.this.invalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("onMeasure", String.format("Width %s, Height %s", MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec)));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(10.0f, 10.0f);
        paint.setColor(this.getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        for (PathUnit unit : pathUnits) {
            float start = 0f;
            float distance = mAnimatedValue * unit.mLength;
            unit.pathMeasure.getSegment(start, distance, dst, true);
            canvas.drawPath(dst, paint);
            dst.reset();

            //canvas.drawPath(path, paint);
        }
        canvas.restore();
    }


    private class PathUnit {
        private final PathMeasure pathMeasure;
        private final float mLength;
        private final Path path;

        public PathUnit(Path path) {
            this.path = path;
            pathMeasure = new PathMeasure(path, true);
            mLength = pathMeasure.getLength();
        }
    }

}
