package com.nuhkoca.myapplication.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A helper class for [RecyclerView] and its [RecyclerView.ItemDecoration]
 * which adds either vertical or horizontal line under each item except the first one.
 *
 * @author nuhkoca
 */
class RecyclerViewItemDecoration(private val context: Context, orientation: Int = 1, private val margin: Int = 0) :
    RecyclerView.ItemDecoration() {

    private val mDivider: Drawable?
    private var mOrientation: Int = 0

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }

    /**
     * Handles orientation
     * It can be either [RecyclerView.LayoutManager.VERTICAL] or [RecyclerView.LayoutManager.HORIZONTAL]
     * @param orientation 0 equals to [RecyclerView.LayoutManager.VERTICAL] and 1 equals to [RecyclerView.LayoutManager.HORIZONTAL]
     */
    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn after the item views are drawn
     * and will thus appear over the views.
     *
     * @param c Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state The current state of RecyclerView.
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        }
    }

    /**
     * Draws vertical line
     * @param c represents [Canvas]
     * @param parent represents [RecyclerView]
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount - 1
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left + dpToPx(margin), top, right - dpToPx(margin), bottom)
            mDivider.draw(c)
        }
    }

    /**
     * Retrieve any offsets for the given item. Each field of `outRect` specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     *
     *
     *
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of `outRect` (left, top, right, bottom) to zero
     * before returning.
     *
     *
     *
     * If you need to access Adapter for additional data, you can call
     * [RecyclerView.getChildAdapterPosition] to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
        } else {
            outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
        }
    }

    /**
     * Converts dp to px and returns the value accordingly
     * @param dp value in dp
     * @return returns the value accordingly
     */
    private fun dpToPx(dp: Int): Int {
        val r = context.resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)

        private const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        private const val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }
}