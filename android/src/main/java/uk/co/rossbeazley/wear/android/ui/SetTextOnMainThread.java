package uk.co.rossbeazley.wear.android.ui;

import android.widget.TextView;

public class SetTextOnMainThread {

    public static Strategy strategy = new DefaultStrategy();

    public void updateTextView(final String text, final TextView textView) {
        strategy.invoke(text, textView);
    }

    public interface Strategy {
        void invoke(String text, TextView textView);
    }

    public static class DefaultStrategy implements SetTextOnMainThread.Strategy {

        @Override
        public void invoke(String text, TextView textView) {
            textView.setText(text);
            textView.invalidate();
        }
    }


    public static class PostingStrategy implements SetTextOnMainThread.Strategy {

        @Override
        public void invoke(final String text, final TextView textView) {
            Runnable runnable = new Runnable() {
                public void run() {
                    new DefaultStrategy().invoke(text,textView);
                }
            };
            textView.post(runnable);
        }
    }


}
