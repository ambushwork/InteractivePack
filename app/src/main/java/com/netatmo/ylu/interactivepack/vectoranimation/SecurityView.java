package com.netatmo.ylu.interactivepack.vectoranimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
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

    List<Path> paths = new ArrayList<>();
    Paint paint = new Paint();

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
                paths.add(PathParser.createPathFromPathData(pathData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        paint.setColor(this.getResources().getColor(R.color.colorAccent));
        paint.setAntiAlias(true);
        for (Path path : paths) {
            canvas.drawPath(path, paint);
        }
        canvas.restore();

    }
}
