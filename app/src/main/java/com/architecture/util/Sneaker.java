package com.architecture.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;


import com.makeramen.roundedimageview.RoundedImageView;
import com.architecture.R;

import java.lang.ref.WeakReference;

/**
 * Created by Avinash Sharma on 2/20/2018.
 * at http://www.dotsquares.com/
 */
public class Sneaker implements View.OnClickListener {

    /**
     * The constant DEFAULT_VALUE.
     */
    private static final int DEFAULT_VALUE = -100000;
    /**
     * The constant mIcon.
     */
    private static int mIcon = DEFAULT_VALUE;
    /**
     * The constant mIconDrawable.
     */
    private static Drawable mIconDrawable = null;
    /**
     * The constant mBackgroundColor.
     */
    private static int mBackgroundColor = DEFAULT_VALUE;
    /**
     * The constant mHeight.
     */
    private static int mHeight = DEFAULT_VALUE;
    /**
     * The constant mIconColorFilterColor.
     */
    private static int mIconColorFilterColor = DEFAULT_VALUE;
    /**
     * The constant mIconSize.
     */
    private static int mIconSize = 24;
    /**
     * The constant mTitle.
     */
    private static String mTitle = "";
    /**
     * The constant mMessage.
     */
    private static String mMessage = "";
    /**
     * The constant mTitleColor.
     */
    private static int mTitleColor = DEFAULT_VALUE;
    /**
     * The constant mMessageColor.
     */
    private static int mMessageColor = DEFAULT_VALUE;
    /**
     * The constant mAutoHide.
     */
    private static boolean mAutoHide = true;
    /**
     * The constant mDuration.
     */
    private static int mDuration = 3000;
    /**
     * The Layout weak reference.
     */
    private static WeakReference<LinearLayout> layoutWeakReference;
    /**
     * The Context weak reference.
     */
    private static WeakReference<Activity> contextWeakReference;
    /**
     * The constant mIsCircular.
     */
    private static boolean mIsCircular = false;
    /**
     * The constant mListener.
     */
    private static OnSneakerClickListener mListener = null;
    /**
     * The constant mTypeFace.
     */
    private static Typeface mTypeFace = null;

    /**
     * Constructor
     *
     * @param activity the activity
     */
    private Sneaker(Activity activity) {
        contextWeakReference = new WeakReference<>(activity);
    }

    /**
     * Create Sneaker with activity reference
     *
     * @param activity the activity
     * @return sneaker
     */
    public static Sneaker with(Activity activity) {
        Sneaker sneaker = new Sneaker(activity);
        setDefault();
        return sneaker;
    }

    /**
     * Hides the sneaker
     */
    public static void hide() {
        if (getLayout() != null) {
            getLayout().startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.popup_hide));
            getActivityDecorView().removeView(getLayout());
        }
    }

    /**
     * Return activity parent view
     *
     * @return activity decor view
     */
    private static ViewGroup getActivityDecorView() {
        ViewGroup decorView = null;

        decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();

        return decorView;
    }

    /**
     * Sets the default values to the sneaker
     */
    private static void setDefault() {
        mTitle = "";
        mIcon = DEFAULT_VALUE;
        mIconDrawable = null;
        mIconColorFilterColor = DEFAULT_VALUE;
        mIconSize = 24;
        mBackgroundColor = DEFAULT_VALUE;
        mAutoHide = true;
        mTitleColor = DEFAULT_VALUE;
        mMessageColor = DEFAULT_VALUE;
        mHeight = DEFAULT_VALUE;
        mIsCircular = false;
        mListener = null;
        mTypeFace = null;
    }

    /**
     * Return activity weak reference
     *
     * @return context
     */
    private static Context getContext() {
        return contextWeakReference.get();
    }

    /**
     * Returns sneaker main layout weak reference
     *
     * @return layout
     */
    private static View getLayout() {
        return layoutWeakReference.get();
    }

    /**
     * Sets the title of the sneaker
     *
     * @param title string value of title
     * @return title
     */
    public Sneaker setTitle(String title) {
        mTitle = title;
        return this;
    }

    /**
     * Sets the title of the sneaker with color
     *
     * @param title string value of title
     * @param color Color resource for title text
     * @return title
     */
    public Sneaker setTitle(String title, int color) {
        mTitle = title;
        if (getContext() != null) {
            try {
                mTitleColor = ContextCompat.getColor(getContext(), color);
            } catch (Exception e) {
                mTitleColor = color;
            }
        }
        return this;
    }

    /**
     * Sets the message to sneaker
     *
     * @param message String value of message
     * @return message
     */
    public Sneaker setMessage(String message) {
        mMessage = message;
        return this;
    }

    /**
     * Sets the message to sneaker with color
     *
     * @param message String value of message
     * @param color   Color resource for message text
     * @return message
     */
    public Sneaker setMessage(String message, int color) {
        mMessage = message;
        if (getContext() != null) {
            try {
                mMessageColor = ContextCompat.getColor(getContext(), color);
            } catch (Exception e) {
                mMessageColor = color;
            }
        }
        return this;
    }

    /**
     * Sets the icon to sneaker
     *
     * @param icon Icon resource for sneaker
     * @return icon
     */
    public Sneaker setIcon(int icon) {
        mIconDrawable = null;
        mIcon = icon;
        return this;
    }

    /**
     * Sets the icon to sneaker
     *
     * @param icon Icon drawable for sneaker
     * @return icon
     */
    public Sneaker setIcon(Drawable icon) {
        mIcon = DEFAULT_VALUE;
        mIconDrawable = icon;
        return this;
    }

    /**
     * Sets the icon to sneaker with circular option
     *
     * @param icon       the icon
     * @param isCircular If icon is round or not
     * @return icon
     */
    public Sneaker setIcon(int icon, boolean isCircular) {
        mIconDrawable = null;
        mIcon = icon;
        mIsCircular = isCircular;
        return this;
    }

    /**
     * Sets the icon to sneaker with circular option
     *
     * @param icon       the icon
     * @param isCircular If icon is round or not
     * @return icon
     */
    public Sneaker setIcon(Drawable icon, boolean isCircular) {
        mIcon = DEFAULT_VALUE;
        mIconDrawable = icon;
        mIsCircular = isCircular;
        return this;
    }

    /**
     * Sets icon.
     *
     * @param icon      the icon
     * @param tintColor the tint color
     * @return the icon
     */
    public Sneaker setIcon(int icon, int tintColor) {
        mIconDrawable = null;
        mIcon = icon;
        if (getContext() != null) {
            try {
                mIconColorFilterColor = ContextCompat.getColor(getContext(), tintColor);
            } catch (Exception e) {
                mIconColorFilterColor = tintColor;
            }
        }
        return this;
    }

    /**
     * Sets icon.
     *
     * @param icon      the icon
     * @param tintColor the tint color
     * @return the icon
     */
    public Sneaker setIcon(Drawable icon, int tintColor) {
        mIcon = DEFAULT_VALUE;
        mIconDrawable = icon;
        if (getContext() != null) {
            try {
                mIconColorFilterColor = ContextCompat.getColor(getContext(), tintColor);
            } catch (Exception e) {
                mIconColorFilterColor = tintColor;
            }
        }
        return this;
    }

    /**
     * Sets the icon to sneaker with circular option and icon tint
     *
     * @param icon       the icon
     * @param tintColor  Icon tint color
     * @param isCircular If icon is round or not
     * @return icon
     */
    public Sneaker setIcon(int icon, int tintColor, boolean isCircular) {
        mIconDrawable = null;
        mIcon = icon;
        mIsCircular = isCircular;
        if (getContext() != null) {
            try {
                mIconColorFilterColor = ContextCompat.getColor(getContext(), tintColor);
            } catch (Exception e) {
                mIconColorFilterColor = tintColor;
            }
        }
        return this;
    }

    /**
     * Sets the icon to sneaker with circular option and icon tint
     *
     * @param icon       the icon
     * @param tintColor  Icon tint color
     * @param isCircular If icon is round or not
     * @return icon
     */
    public Sneaker setIcon(Drawable icon, int tintColor, boolean isCircular) {
        mIcon = DEFAULT_VALUE;
        mIconDrawable = icon;
        mIsCircular = isCircular;
        if (getContext() != null) {
            try {
                mIconColorFilterColor = ContextCompat.getColor(getContext(), tintColor);
            } catch (Exception e) {
                mIconColorFilterColor = tintColor;
            }
        }
        return this;
    }

    /**
     * Sets the size of the icon.
     *
     * @param size New icon size.
     * @return the icon size
     */
    public Sneaker setIconSize(int size) {
        mIconSize = size;
        return this;
    }

    /**
     * Disable/Enable auto hiding sneaker
     *
     * @param autoHide the auto hide
     * @return sneaker
     */
    public Sneaker autoHide(boolean autoHide) {
        mAutoHide = autoHide;
        return this;
    }

    /**
     * Sets the height to sneaker
     *
     * @param height Height value for sneaker
     * @return height
     */
    public Sneaker setHeight(int height) {
        mHeight = height;
        return this;
    }

    /**
     * Sets the duration for sneaker.
     * After this duration sneaker will disappear
     *
     * @param duration the duration
     * @return duration
     */
    public Sneaker setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    /**
     * Sets the click listener to sneaker
     *
     * @param listener the listener
     * @return on sneaker click listener
     */
    public Sneaker setOnSneakerClickListener(OnSneakerClickListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * Set font for title and message
     *
     * @param typeface the typeface
     * @return typeface
     */
    public Sneaker setTypeface(Typeface typeface) {
        mTypeFace = typeface;
        return this;
    }

    /**
     * Shows sneaker with custom color
     *
     * @param backgroundColor Color resource for sneaker background color
     */
    public void sneak(int backgroundColor) {
        if (getContext() != null) {
            try {
                mBackgroundColor = ContextCompat.getColor(getContext(), backgroundColor);
            } catch (Exception e) {
                mBackgroundColor = backgroundColor;
            }
            sneakView();
        }
    }

    /**
     * Shows warning sneaker with fixed icon, background color and icon color.
     * Icons, background and text colors for this are not customizable
     */
    public void sneakWarning() {
        mBackgroundColor = Color.parseColor("#f97544");
        mTitleColor = Color.parseColor("#ffffff");
        mMessageColor = Color.parseColor("#ffffff");
        mIconColorFilterColor = Color.parseColor("#ffffff");
        //mIcon = R.drawable.ic_warning;

        if (getContext() != null)
            sneakView();
    }

    /**
     * Shows error sneaker with fixed icon, background color and icon color.
     * Icons, background and text colors for this are not customizable
     *
     * @return the sneaker
     */
    public Sneaker sneakError() {
        mBackgroundColor = Color.parseColor("#F44336");
        mTitleColor = Color.parseColor("#FFFFFF");
        mMessageColor = Color.parseColor("#FFFFFF");
        mIconColorFilterColor = Color.parseColor("#FFFFFF");
        mIcon = R.drawable.ic_warning;

        if (getContext() != null)
            sneakView();
        return null;
    }

    /**
     * Shows success sneaker with fixed icon, background color and icon color.
     * Icons, background and text colors for this are not customizable
     *
     * @return the sneaker
     */
    public Sneaker sneakSuccess() {
        mBackgroundColor = Color.parseColor("#2bb600");
        mTitleColor = Color.parseColor("#FFFFFF");
        mMessageColor = Color.parseColor("#FFFFFF");
        mIconColorFilterColor = Color.parseColor("#FFFFFF");
        mIcon = R.drawable.ic_success;

        if (getContext() != null)
            sneakView();
        return null;
    }

    /**
     * Creates the view and sneaks in
     */
    private void sneakView() {

        // Main layout
        LinearLayout layout = new LinearLayout(getContext());


        layoutWeakReference = new WeakReference<>(layout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(46, getStatusBarHeight(), 46, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setElevation(6);
        }

        // Background color
        layout.setBackgroundColor(mBackgroundColor);

        // Icon
        // If icon is set
        if (mIcon != DEFAULT_VALUE || mIconDrawable != null) {
            if (!mIsCircular) {
                AppCompatImageView ivIcon = new AppCompatImageView(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(convertToDp(mIconSize), convertToDp(mIconSize));
                ivIcon.setLayoutParams(lp);

                if (mIcon == DEFAULT_VALUE) {
                    ivIcon.setImageDrawable(mIconDrawable);
                } else {
                    ivIcon.setImageResource(mIcon);
                }
                ivIcon.setClickable(false);
                if (mIconColorFilterColor != DEFAULT_VALUE) {
                    ivIcon.setColorFilter(mIconColorFilterColor);
                }
                layout.addView(ivIcon);
            } else {
                RoundedImageView ivIcon = new RoundedImageView(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(convertToDp(mIconSize), convertToDp(mIconSize));
                ivIcon.setLayoutParams(lp);

                if (mIcon == DEFAULT_VALUE) {
                    ivIcon.setImageDrawable(mIconDrawable);
                } else {
                    ivIcon.setImageResource(mIcon);
                }
                ivIcon.setClickable(false);
                if (mIconColorFilterColor != DEFAULT_VALUE) {
                    ivIcon.setColorFilter(mIconColorFilterColor);
                }


                layout.addView(ivIcon);
            }
        }

        // Title and description
        LinearLayout textLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayout.setLayoutParams(textLayoutParams);
        textLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lpTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!mTitle.isEmpty()) {
            TextView tvTitle = new TextView(getContext());
            tvTitle.setLayoutParams(lpTv);
            tvTitle.setGravity(Gravity.CENTER_VERTICAL);
            if (!mMessage.isEmpty())
                tvTitle.setPadding(20, 5, 26, 0); // Top padding if there is message
            else
                tvTitle.setPadding(20, 0, 26, 0); // No top padding if there is no message
            if (mTitleColor != DEFAULT_VALUE)
                tvTitle.setTextColor(mTitleColor);

            // typeface
            if (mTypeFace != null)
                tvTitle.setTypeface(mTypeFace);

            tvTitle.setTextSize(14);
            tvTitle.setText(mTitle);
            tvTitle.setClickable(false);
            textLayout.addView(tvTitle);
        }

        if (!mMessage.isEmpty()) {
            TextView tvMessage = new TextView(getContext());
            tvMessage.setLayoutParams(lpTv);
            tvMessage.setGravity(Gravity.CENTER_VERTICAL);
            if (!mTitle.isEmpty())
                tvMessage.setPadding(20, 15, 26, 15); // Bottom padding if there is title
            else
                tvMessage.setPadding(20, 0, 26, 0); // No bottom padding if there is no title
            if (mMessageColor != DEFAULT_VALUE)
                tvMessage.setTextColor(mMessageColor);

            // typeface
            if (mTypeFace != null)
                tvMessage.setTypeface(mTypeFace);

            tvMessage.setTextSize(13);
            tvMessage.setText(mMessage);
            tvMessage.setClickable(false);
            textLayout.addView(tvMessage);
        }
        layout.addView(textLayout);
        layout.setId(R.id.mainLayout);


        final ViewGroup viewGroup = getActivityDecorView();
        getExistingOverlayInViewAndRemove(viewGroup);

        layout.setOnClickListener(this);
        viewGroup.addView(layout);

        layout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.popup_show));
        if (mAutoHide) {
            Handler handler = new Handler();
            handler.removeCallbacks(null);
            handler.postDelayed(() -> {
                try {
                    getLayout().startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.popup_hide));
                    viewGroup.removeView(getLayout());
                }catch (Exception e){e.printStackTrace();}
            }, mDuration);
        }
    }

    /**
     * Gets the existing sneaker and removes before adding new one
     *
     * @param parent the parent
     */
    public void getExistingOverlayInViewAndRemove(ViewGroup parent) {

        for (int i = 0; i < parent.getChildCount(); i++) {

            View child = parent.getChildAt(i);
            if (child.getId() == R.id.mainLayout) {
                parent.removeView(child);
            }
            if (child instanceof ViewGroup) {
                getExistingOverlayInViewAndRemove((ViewGroup) child);
            }
        }
    }

    /**
     * Returns status bar height.
     *
     * @return status bar height
     */
    private int getStatusBarHeight() {
        Rect rectangle = new Rect();
        Window window = ((Activity) getContext()).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentViewTop - statusBarHeight;



        return statusBarHeight;
    }

    /**
     * Convert to dp int.
     *
     * @param sizeInDp the size in dp
     * @return the int
     */
    private int convertToDp(float sizeInDp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }

    /**
     * Sneaker on click
     *
     * @param view the view
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onSneakerClick(view);
        }
        getLayout().startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.popup_hide));
        getActivityDecorView().removeView(getLayout());
    }

    /**
     * The interface On sneaker click listener.
     */
    public interface OnSneakerClickListener {
        /**
         * On sneaker click.
         *
         * @param view the view
         */
        void onSneakerClick(View view);
    }
}
