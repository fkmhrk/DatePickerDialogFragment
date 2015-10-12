package jp.fkmsoft.libs.progress;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

/**
 * Dialog fragment for displaying Progress
 */
public class DatePickerDialogFragment extends DialogFragment {
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_YEAR = "year";
    private static final String ARGS_MONTH = "month";
    private static final String ARGS_DAY = "day";
    private static final String ARGS_EXTRA = "extra";

    public static final String EXTRA_YEAR = "year";
    public static final String EXTRA_MONTH = "month";
    public static final String EXTRA_DAY = "day";
    public static final String EXTRA_DATA = "extra";

    public static DatePickerDialogFragment newInstance(Fragment target, int requestCode,
                                                     String title, int year, int month, int day, Bundle extra) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setTargetFragment(target, requestCode);
        
        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        args.putInt(ARGS_YEAR, year);
        args.putInt(ARGS_MONTH, month);
        args.putInt(ARGS_DAY, day);
        args.putBundle(ARGS_EXTRA, extra);
        fragment.setArguments(args);
        
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) { return super.onCreateDialog(savedInstanceState); }

        Bundle args = getArguments();
        String title = args.getString(ARGS_TITLE);
        int year = args.getInt(ARGS_YEAR);
        int month = args.getInt(ARGS_MONTH);
        int day = args.getInt(ARGS_DAY);

        DatePickerDialog dialog = new DatePickerDialog(activity, mCallback, year, month, day);
        dialog.setTitle(title);

        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        Fragment target = getTargetFragment();
        if (target == null) { return; }

        Bundle args = getArguments();
        Bundle extra = args.getBundle(ARGS_EXTRA);

        Intent data = new Intent();
        data.putExtra(EXTRA_DATA, extra);

        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, data);
    }

    private final DatePickerDialog.OnDateSetListener mCallback = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Fragment target = getTargetFragment();
            if (target == null) { return; }

            Bundle args = getArguments();
            Bundle extra = args.getBundle(ARGS_EXTRA);

            Intent data = new Intent();
            data.putExtra(EXTRA_YEAR, year);
            data.putExtra(EXTRA_MONTH, monthOfYear);
            data.putExtra(EXTRA_DAY, dayOfMonth);
            data.putExtra(EXTRA_DATA, extra);

            target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        }
    };
}
