package com.caragiz_studioz.tool.toolbartest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

/**
 * Created by amits on 07-10-2017.
 */

public class CurvedToolbar extends FrameLayout {
    private CuredToolbarSettings settings;
    private int height = 0;
    private int width = 0;
    private Path clipPath;

    public CurvedToolbar(@NonNull Context context) {
        super(context);
        init(context , null);
    }

    public CurvedToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context , attrs);
    }

    public CurvedToolbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context , attrs);
    }

    public CurvedToolbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context , attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        settings = new CuredToolbarSettings(context, attrs);
        settings.setElevation(ViewCompat.getElevation(this));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2)
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private Path createClipPath() {

        final Path path = new Path();
        float archeight = settings.getArcHeight();

        switch (settings.getPosition()) {
            case CuredToolbarSettings.POSITION_BOTTOM: {
                if (settings.isCropInside()) {
                    path.moveTo(0, 0);
                    path.lineTo(0, height);
                    path.quadTo(width / 2, height - 2 * archeight, width, height);
                    path.lineTo(width, 0);
                    path.close();
                } else {
                    path.moveTo(0, 0);
                    path.lineTo(0, height - archeight);
                    path.quadTo(width / 2, height + archeight, width, height - archeight);
                }
            }
            case CuredToolbarSettings.POSITION_TOP:
                if (settings.isCropInside()) {
                    path.moveTo(0, height);
                    path.lineTo(0, 0);
                    path.quadTo(width / 2, 2 * archeight, width, 0);
                    path.lineTo(width, height);
                    path.close();
                } else {
                    path.moveTo(0, archeight);
                    path.quadTo(width / 2, -archeight, width, archeight);
                    path.lineTo(width, height);
                    path.lineTo(0, height);
                    path.close();
                }
                break;
            case CuredToolbarSettings.POSITION_LEFT:
                if (settings.isCropInside()) {
                    path.moveTo(width, 0);
                    path.lineTo(0, 0);
                    path.quadTo(archeight * 2, height / 2, 0, height);
                    path.lineTo(width, height);
                    path.close();
                } else {
                    path.moveTo(width, 0);
                    path.lineTo(archeight, 0);
                    path.quadTo(-archeight, height / 2, archeight, height);
                    path.lineTo(width, height);
                    path.close();
                }
                break;
            case CuredToolbarSettings.POSITION_RIGHT:
                if (settings.isCropInside()) {
                    path.moveTo(0, 0);
                    path.lineTo(width, 0);
                    path.quadTo(width - archeight * 2, height / 2, width, height);
                    path.lineTo(0, height);
                    path.close();
                } else {
                    path.moveTo(0, 0);
                    path.lineTo(width - archeight, 0);
                    path.quadTo(width + archeight, height / 2, width - archeight, height);
                    path.lineTo(0, height);
                    path.close();
                }
                break;
        }

        return path;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateLayout();
        }
    }

    private void calculateLayout() {
        if (settings == null) {
            return;
        }
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        if (width > 0 && height > 0) {

            clipPath = createClipPath();
            ViewCompat.setElevation(this, settings.getElevation());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !settings.isCropInside()) {
                ViewCompat.setElevation(this, settings.getElevation());
                setOutlineProvider(new ViewOutlineProvider() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setConvexPath(clipPath);

                    }
                });
            }
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
try {
    canvas.clipPath(clipPath);
}catch(Exception e){}
        super.dispatchDraw(canvas);

        canvas.restore();
    }
}
