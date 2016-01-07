package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Calendar;

class WatchViewRoot extends FrameLayout {

    private Rect currentPeekCardPosition;

    private WatchViewState watchViewState;


    public WatchViewRoot(Context context, final WatchView.RedrawOnInvalidate redrawOnInvalidate) {
        this(context);
        this.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                redrawOnInvalidate.postInvalidate();
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                redrawOnInvalidate.postInvalidate();
            }
        });
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
         if (peekCardPosition != null) {
            bounds.bottom = bounds.bottom + (peekCardPosition.top - peekCardPosition.bottom);
        }
        return bounds;
    }


    public void drawToBounds(Canvas canvas, Rect bounds) {

        canvas.drawColor(watchViewState.background()); //reset canvas to base colour

        if (getChildCount() == 0 || !watchViewState.isVisible()) return; //fast exit //TODO invert isVisible boolean

        bounds = adjustDrawingAreaForAnyNotificationCards(bounds, currentPeekCardPosition);

        invalidate();

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

    public void storeCurrentPeekCardPosition(Rect currentPeekCardPosition) {
        this.currentPeekCardPosition = currentPeekCardPosition;
    }

    public void toInvisible() {
        watchViewState.toInvisible();
    }

    public void toActiveOffset() {
        watchViewState.toActiveOffset();
    }

    public void toAmbient() {
        watchViewState.toAmbient();
    }

    public void toActive() {
        watchViewState.toActive();
    }

    public void registerView(View watchViewImpl, WatchView.RedrawOnInvalidate redrawOnInvalidate) {
        this.addView(watchViewImpl);
        this.watchViewState = new WatchViewState((WatchView) watchViewImpl);
        this.watchViewState.registerInvalidator(redrawOnInvalidate);
        this.watchViewState.toActive();
    }

    public void timeTick(Calendar instance) {
        watchViewState.timeTick(instance);
    }

}

