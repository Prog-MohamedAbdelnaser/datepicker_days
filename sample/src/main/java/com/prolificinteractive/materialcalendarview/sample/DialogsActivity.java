package com.prolificinteractive.materialcalendarview.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Shows off the most basic usage
 */
public class DialogsActivity extends AppCompatActivity {

  ArrayList<CalendarDay> calendarDays =new ArrayList<CalendarDay>();

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialogs);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.button_normal_dialog)
  void onNormalDialogClick() {
    new SimpleDialogFragment().show(getSupportFragmentManager(), "test-normal");
  }

  @OnClick(R.id.button_simple_dialog)
  void onSimpleCalendarDialogClick() {
    Dialog dialog =new Dialog(this);
    dialog.setContentView(R.layout.dialog_basic);

    MaterialCalendarView calendarView = dialog.findViewById(R.id.calendarView);
    calendarView.setSelectedDates(calendarDays);

    calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
      @Override
      public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if (selected) calendarDays.add(date);
        else calendarDays.remove(date);
      }
    });
    dialog.show();
   // new SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
  }

  public static class SimpleDialogFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      return new AlertDialog.Builder(getActivity())
          .setTitle(R.string.title_activity_dialogs)
          .setMessage("Test Dialog")
          .setPositiveButton(android.R.string.ok, null)
          .create();
    }
  }

  public static class SimpleCalendarDialogFragment extends AppCompatDialogFragment
      implements OnDateSelectedListener {

    private TextView textView;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      LayoutInflater inflater = getActivity().getLayoutInflater();

      //inflate custom layout and get views
      //pass null as parent view because will be in dialog layout
      View view = inflater.inflate(R.layout.dialog_basic, null);

      textView = view.findViewById(R.id.textView);

      MaterialCalendarView widget = view.findViewById(R.id.calendarView);

      widget.setOnDateChangedListener(this);

      return new AlertDialog.Builder(getActivity())
          .setTitle(R.string.title_activity_dialogs)
          .setView(view)
          .setPositiveButton(android.R.string.ok, null)
          .create();
    }

    @Override
    public void onDateSelected(
        @NonNull MaterialCalendarView widget,
        @NonNull CalendarDay date,
        boolean selected) {
      textView.setText(FORMATTER.format(date.getDate()));
    }
  }
}
