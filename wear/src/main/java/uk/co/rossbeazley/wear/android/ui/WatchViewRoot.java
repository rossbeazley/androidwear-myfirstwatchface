package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.util.Calendar;

class WatchViewRoot extends FrameLayout {

    private WatchView.RedrawOnInvalidate redrawOnInvalidate;
    private WatchFaceService.CanLog logger;
    private Rect currentPeekCardPosition;

    private WatchViewState watchViewState;


    public WatchViewRoot(Context context, final WatchView.RedrawOnInvalidate redrawOnInvalidate, final WatchFaceService.CanLog logger) {
        this(context);
        this.redrawOnInvalidate = redrawOnInvalidate;
        this.logger = logger;

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

    private final Rect adjustedBounds = new Rect();
    @NonNull
    private Rect adjustDrawingAreaForAnyNotificationCards(Rect bounds, Rect peekCardPosition) {
        adjustedBounds.set(bounds);
        if (peekCardPosition != null) {
            adjustedBounds.bottom = bounds.bottom + (peekCardPosition.top - peekCardPosition.bottom);
        }
        return adjustedBounds;
    }


    public void drawToBounds(Canvas canvas, Rect bounds) {

        canvas.drawColor(watchViewState.background()); //reset canvas to base colour

        if (getChildCount() == 0 || !watchViewState.isVisible()) {
            logger.log("RWF fast exit from draw " + getChildCount() + watchViewState.isVisible());
            return; //fast exit //TODO invert isVisible boolean
        }

        invalidate();

        bounds = adjustDrawingAreaForAnyNotificationCards(bounds, currentPeekCardPosition);

        measureAndLayout(bounds);

        translateAndDrawToCanvas(canvas);

        logger.log("RWF " + watchViewState.toString());
    }

    private void translateAndDrawToCanvas(Canvas canvas) {
        Matrix matrix = this.getMatrix();
        canvas.save();
        canvas.setMatrix(matrix);
        this.draw(canvas);
        canvas.restore();
    }

    private void measureAndLayout(Rect bounds) {
        int widthSpec = MeasureSpec.makeMeasureSpec(bounds.width(), MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(bounds.height(), MeasureSpec.EXACTLY);
        this.measure(widthSpec, heightSpec);
        this.layout(0, 0, bounds.width(), bounds.height());
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
        currentPeekCardPosition=null;
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

