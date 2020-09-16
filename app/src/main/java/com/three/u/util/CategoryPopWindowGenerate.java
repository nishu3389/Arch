package com.three.u.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;

import com.three.u.R;

import java.util.ArrayList;


public class CategoryPopWindowGenerate {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static PopupWindow popupWindow(final ArrayList list, final View v, Context context, Drawable shape, int width, final IPopUpWindowCallbacks callbacks) {
        // initCardList a pop up window type
        final PopupWindow popupWindow = new PopupWindow(context);

        final ArrayAdapter adapter = new ArrayAdapter(context, R.layout.item_custom_popup_window, list);

        // the drop down list is a list view
        ListView listViewSort = new ListView(context);
        listViewSort.setDividerHeight(0);
        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        // set enable item selected
        listViewSort.setOnItemClickListener((adapterView, view, i, l) -> {
            callbacks.onPopupWindowItemClickListner(adapterView.getAdapter().getItem(i), v);
            popupWindow.dismiss();
        });

        // some other visual settings for popup window
        popupWindow.setFocusable(true);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

//        popupWindow.setWidth(displayMetrics.widthPixels / 2);
        popupWindow.setWidth(width);
        popupWindow.setBackgroundDrawable(shape);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(2f);
        }

        int dpHeight = displayMetrics.heightPixels / 2;
        popupWindow.setHeight(dpHeight > list.size() * 100 ? list.size() * 100 : dpHeight);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewSort);
        popupWindow.showAsDropDown(v, 0, 0);
        return popupWindow;
    }

    public interface IPopUpWindowCallbacks {
        /**
         * Method use to handle click of an item.
         *
         * @param e object value
         * @param v view which is passed
         */
        void onPopupWindowItemClickListner(Object e, View v);
    }
}
