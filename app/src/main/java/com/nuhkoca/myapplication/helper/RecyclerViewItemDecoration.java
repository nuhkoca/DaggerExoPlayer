package com.nuhkoca.myapplication.helper;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A helper class for {@link RecyclerView} and its {@link RecyclerView.ItemDecoration}
 * which adds either vertical or horizontal line under each item except the first one.
 *
 * @author nuhkoca
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mOrientation;
    private int margin;
    private Context context;

    public RecyclerViewItemDecoration(@NonNull Context context, int orientation, int margin) {
        this.margin = margin;
        this.context = context;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * Handles orientation
     * It can be either {@link RecyclerView.LayoutManager#VERTICAL} or {@link RecyclerView.LayoutManager#HORIZONTAL}
     * @param orientation 0 equals to {@link RecyclerView.LayoutManager#VERTICAL} and 1 equals to {@link RecyclerView.LayoutManager#HORIZONTAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
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
    @Override
    public void onDrawOver(@NotNull Canvas c, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        }
    }

    /**
     * Draws vertical line
     * @param c represents {@link Canvas}
     * @param parent represents {@link RecyclerView}
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left + dpToPx(margin), top, right - dpToPx(margin), bottom);
            mDivider.draw(c);
        }
    }

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     *
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     *
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    /**
     * Converts dp to px and returns the value accordingly
     * @param dp value in dp
     * @return returns the value accordingly
     */
    private int dpToPx(int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}