package com.nuhkoca.myapplication.binding

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.nuhkoca.myapplication.helper.Constants
import javax.inject.Inject

/**
 * A [BindingAdapter] to bind spannable text into TextViews.
 *
 * @author nuhkoca
 */
class SpannableTextBindingAdapter @Inject constructor() {

    /**
     * A [BindingAdapter] method that makes bold text
     *
     * @param target      represents a [TextView]
     * @param duration    represents the text to be bind
     * @param placeholder represents the resource to be bold
     */
    @BindingAdapter(value = ["android:duration", "android:placeholder"])
    fun bindSpannableDuration(target: TextView, duration: Int, @StringRes placeholder: Int) {
        val context = target.context

        val min = duration / 60
        val sec = duration % 60

        val formattedDuration = String.format(Constants.locale, "%02d", min) + ":" + String.format(Constants.locale, "%02d", sec)

        val boldSpan = SpannableString(
                String.format(context.getString(placeholder), formattedDuration))

        val lengthToColon = boldSpan.toString().indexOf(":")
        boldSpan.setSpan(StyleSpan(Typeface.BOLD),
                0, lengthToColon, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        target.text = boldSpan
    }
}
