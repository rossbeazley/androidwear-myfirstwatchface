package uk.co.rossbeazley.wear.android.ui;

import android.os.Looper;
import android.widget.TextView;

class SetTextOnMainThread {

    public void updateTextView(final String text, final TextView textView) {

        Runnable updateTextAndInvalidate = new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
                textView.invalidate();
            }
        };


        if(onMainThread()) {
            updateTextAndInvalidate.run();
        } else {
            textView.post(updateTextAndInvalidate);
        }
    }

    private boolean onMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
