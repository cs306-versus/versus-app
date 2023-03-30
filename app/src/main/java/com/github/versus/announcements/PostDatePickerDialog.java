package com.github.versus.announcements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.github.versus.posts.Timestamp;

import java.util.Calendar;

public class PostDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface PickDateListener extends CancelCreate {
        public void onPickPostDate(Timestamp ts);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog d = new DatePickerDialog(requireContext(), this, year, month, day);
//        d.getButton(DatePickerDialog.BUTTON_POSITIVE).setText("next");
       d.setMessage("When will you meet?");
        return d;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

    }
}
