package uk.co.rossbeazley.ui;

import android.widget.TextView;

class SetTextOnMainThread {
    private final TextView textView;

    public SetTextOnMainThread(TextView textView) {
        this.textView = textView;
    }

    public void to(final String text) {
         textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
}
