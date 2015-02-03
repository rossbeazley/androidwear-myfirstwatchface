package uk.co.rossbeazley.wear.android.ui;

import android.widget.TextView;

class SetTextOnMainThread {

    public void updateTextView(final String text, final TextView textView) {
         textView.post(new Runnable() {
             @Override
             public void run() {
                 textView.setText(text);
             }
         });
        textView.setText(text);
        textView.invalidate();
    }
}
