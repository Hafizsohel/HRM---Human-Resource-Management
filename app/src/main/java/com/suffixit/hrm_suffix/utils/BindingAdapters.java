package com.suffixit.hrm_suffix.utils;

import android.widget.TextView;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("totalHrsFormatted")
    public static void setTotalHrsFormatted(TextView textView, double totalHrs) {
        if (textView != null) {
            String formattedTotalHrs = formatTotalHrsString(totalHrs);
            textView.setText(formattedTotalHrs);
        }
    }

    private static String formatTotalHrsString(double totalHrs) {
        int hours = (int) totalHrs;
        int minutes = (int) ((totalHrs - hours) * 60);  // Extract minutes

        return String.format("%d:%02d %s", hours, minutes, (hours >= 12 ? "h" : "m"));
    }
}

