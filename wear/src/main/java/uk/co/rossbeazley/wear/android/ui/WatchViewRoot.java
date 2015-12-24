package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

class WatchViewRoot extends FrameLayout {

    public int colour;

    private WatchView.RedrawOnInvalidate redrawOnInvalidate;
    private Rect currentPeekCardPosition;



    public WatchViewRoot(Context context, WatchView.RedrawOnInvalidate redrawOnInvalidate) {
        this(context);

        this.redrawOnInvalidate = redrawOnInvalidate;

        colour = Color.WHITE;
    }


    public WatchViewRoot(Context context) {
        super(context);
    }

    public WatchViewRoot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WatchViewRoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WatchViewRoot(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @NonNull
    private static Rect adjustDrawingAreaForAnyNotificationCards(Rect bounds, Rect peekCardPosition) {
        Rect rtn = new Rect();
        rtn.set(bounds);

        if (peekCardPosition != null) {
            rtn.bottom = bounds.bottom + (peekCardPosition.top - peekCardPosition.bottom);
        }

        return rtn;
    }


    public void drawToBounds(Canvas canvas, Rect bounds) {
        if (getChildCount() == 0) return;

        bounds = adjustDrawingAreaForAnyNotificationCards(bounds, currentPeekCardPosition);

        canvas.drawColor(colour); //reset canvas to base colour

        int widthSpec = View.MeasureSpec.makeMeasureSpec(bounds.width(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(bounds.height(), View.MeasureSpec.EXACTLY);
        this.measure(widthSpec, heightSpec);
        this.layout(0, 0, bounds.width(), bounds.height());

        Matrix matrix = this.getMatrix();
        canvas.save();
        canvas.setMatrix(matrix);
        this.draw(canvas);
        canvas.restore();
    }

    public void destroy() {
        removeAllViews();
    }

    @Override
    public void invalidate() {
        this.redrawOnInvalidate.postInvalidate();
    }

    @Override
    public void postInvalidate() {
        this.redrawOnInvalidate.postInvalidate();
    }

    @Override
    public void postInvalidateOnAnimation() {
        this.redrawOnInvalidate.postInvalidate();
    }


    public void storeCurrentPeekCardPosition(Rect currentPeekCardPosition) {
        this.currentPeekCardPosition = currentPeekCardPosition;
    }

}

