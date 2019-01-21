package com.nuhkoca.myapplication.binding;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.nuhkoca.myapplication.helper.Constants;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;

/**
 * A {@link BindingAdapter} to bind spannable text into TextViews.
 *
 * @author nuhkoca
 */
public class SpannableTextBindingAdapter {

    /**
     * A default constructor that gets several dependents
     */
    @Inject
    public SpannableTextBindingAdapter() {
    }

    /**
     * A {@link BindingAdapter} method that makes bold text
     *
     * @param target      represents a {@link TextView}
     * @param duration    represents the text to be bind
     * @param placeholder represents the resource to be bold
     */
    @BindingAdapter(value = {"android:duration", "android:placeholder"})
    public void bindSpannableDuration(@NonNull TextView target, int duration, @StringRes int placeholder) {
        Context context = target.getContext();

        int min = (duration / 60);
        int sec = (duration % 60);

        String formattedDuration =
                String.format(Constants.locale, "%02d", min) + ":" + String.format(Constants.locale, "%02d", sec);

        Spannable boldSpan = new SpannableString(
                String.format(context.getString(placeholder), formattedDuration));

        int lengthToColon = boldSpan.toString().indexOf(":");
        boldSpan.setSpan(new StyleSpan(Typeface.BOLD),
                0, lengthToColon, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        target.setText(boldSpan);
    }
}
