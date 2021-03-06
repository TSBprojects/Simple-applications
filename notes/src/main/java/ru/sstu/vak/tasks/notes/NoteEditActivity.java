package ru.sstu.vak.tasks.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ru.sstu.vak.tasks.notes.SQLite.DatabaseWrapper;
import ru.sstu.vak.tasks.notes.SQLite.Entities.Note;
import ru.sstu.vak.tasks.notes.SQLite.Entities.Tag;

public class NoteEditActivity extends AppCompatActivity implements View.OnClickListener {

    String initialDate;
    String initialTitle;
    String initialBody;

    ArrayList<Integer> additionTags;
    ArrayList<Integer> removalTags;
    int clickedNoteId;

    Menu toolbarMenu;
    TextView date_field;
    EditText title_field;
    EditText note_text_field;
    LinearLayout llBottomSheet;
    Toolbar edit_note_tool_bar;


    FloatingActionButton bottom_sheet_btn;
    int currentBottomSheetState = BottomSheetBehavior.STATE_COLLAPSED;

    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit_activity);
        setActivitiesItems();

        setStatusBar(this);
        setToolBar();

        bottom_sheet_btn.animate().scaleX(0).scaleY(0).setDuration(0).start();
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (currentBottomSheetState == BottomSheetBehavior.STATE_EXPANDED && BottomSheetBehavior.STATE_DRAGGING == newState) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    bottom_sheet_btn.animate().scaleX(1).scaleY(1).setDuration(200).start();
                    currentBottomSheetState = BottomSheetBehavior.STATE_EXPANDED;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.date_field: {
                setDateDialog();
                break;
            }
            case R.id.bottom_sheet_btn: {
                if (currentBottomSheetState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottom_sheet_btn.animate().scaleX(0).scaleY(0).setDuration(200).start();
                    currentBottomSheetState = BottomSheetBehavior.STATE_COLLAPSED;
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            }
            default: {
                if (isSelectedTag(findViewById(viewId))) {
                    removalTag(viewId);
                } else {
                    additionTag(viewId);
                }
            }
        }
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_tool_bar, menu);
        toolbarMenu = menu;
        setNoteData();
        return true;
    }

    @Override
    public void onBackPressed() {
        backBtnDialog();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_btn: {
                saveChanges();
                break;
            }
            case R.id.remove_btn: {
                removeNoteDialog();
                break;
            }
            case android.R.id.home: {
                backBtnDialog();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void backBtnDialog() {
        if (isNeedSave()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Сохранить изменения или удалить их?");
            builder.setNegativeButton("Удалить",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.setPositiveButton("Сохранить",
                    new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void onClick(DialogInterface dialog, int which) {
                            saveChanges();
                        }
                    });
            builder.setNeutralButton("Отмена", null);
            builder.show();
        } else {
            if (getCurrentFocus() != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().
                                getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            finish();
        }
    }

    private void removeNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Удалить заметку?")
                .setPositiveButton("Удалить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeNote();
                                finish();
                            }
                        }).setNegativeButton("Отмена", null).show();
    }

    private void removeNote() {
        try (DatabaseWrapper dbw = new DatabaseWrapper(MainActivity.getInstance(), "myDB")) {
            dbw.removeNote(clickedNoteId);
            MainActivity.getInstance().getPagerAdapter().notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveChanges() {
        if (note_text_field.getText().toString().equals("")) {
            showMessage("Зачем сохранять пустую заметку?");
            return;
        }
        if (!isNeedSave()) {
            return;
        }
        try (DatabaseWrapper dbw = new DatabaseWrapper(MainActivity.getInstance(), "myDB")) {
            String date = date_field.getText().toString();
            String title = title_field.getText().toString();
            String body = note_text_field.getText().toString();

            if (clickedNoteId != -1) {
                dbw.updateNote(clickedNoteId, date, title, body);
                for (int i = 0; i < removalTags.size(); i++) {
                    dbw.removeTagFromNote(removalTags.get(i), clickedNoteId);
                }
                for (int i = 0; i < additionTags.size(); i++) {
                    dbw.addTagToNote(additionTags.get(i), clickedNoteId);
                }
            } else {
                int noteId = dbw.addNote(date, title, body);
                for (int i = 0; i < additionTags.size(); i++) {
                    dbw.addTagToNote(additionTags.get(i), noteId);
                }
            }
            setInitialData();
            showMessage("Изменения сохранены");
            MainActivity.getInstance().getPagerAdapter().notifyDataSetChanged();

            TagEditActivity tagEditActivity = TagEditActivity.getInstance();
            if (tagEditActivity != null) {
                tagEditActivity.reload();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNeedSave() {
        String date = date_field.getText().toString();
        String title = title_field.getText().toString();
        String body = note_text_field.getText().toString();
        if (initialDate.equals(date) &&
                initialTitle.equals(title) &&
                initialBody.equals(body) &&
                additionTags.size() == 0 &&
                removalTags.size() == 0) {
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDateDialog() {
        final Calendar date = getDateFromString(date_field.getText().toString());

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, monthOfYear);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date_field.setText(getUnderlinedText(getStringFromDate(date)));
            }
        };

        new DatePickerDialog(this, dateListener,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getStringFromDate(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        return format.format(calendar.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Calendar getDateFromString(String str) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        try {
            calendar.setTime(format.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    private void additionTag(int tagId) {
        TextView tag = (TextView) findViewById(tagId);
        tag.setBackgroundColor(getResources().getColor(R.color.selected_tag));
        tag.setTextColor(getResources().getColor(android.R.color.white));

        for (int i = 0; i < removalTags.size(); i++) {
            if (tagId == removalTags.get(i)) {
                removalTags.remove(i);
                return;
            }
        }
        additionTags.add(tagId);
    }

    private void removalTag(int tagId) {
        TextView tag = (TextView) findViewById(tagId);
        tag.setBackground(null);
        tag.setTextColor(getResources().getColor(android.R.color.black));

        for (int i = 0; i < additionTags.size(); i++) {
            if (tagId == additionTags.get(i)) {
                additionTags.remove(i);
                return;
            }
        }
        removalTags.add(tagId);
    }

    private boolean isSelectedTag(View v) {
        if (v.getBackground() != null) {
            return true;
        }
        return false;
    }

    private String getCurrentDate() {
        long curTime = System.currentTimeMillis();
        return new SimpleDateFormat("dd.MM.yyyy").format(curTime);
    }

    private SpannableString getUnderlinedText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    private void setInitialData() {
        initialDate = date_field.getText().toString();
        initialTitle = title_field.getText().toString();
        initialBody = note_text_field.getText().toString();
        removalTags.clear();
        additionTags.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setNoteData() {
        Note note;
        Tag[] allTags;
        Tag[] noteTags;
        clickedNoteId = MainActivity.getInstance().getNotesFragment().getClickedNoteId();
        LinearLayout tags_linearLayout = (LinearLayout) findViewById(R.id.tags_linearLayout);
        TagsFactory tg = new TagsFactory(this, tags_linearLayout, this);

        if (clickedNoteId != -1) {
            toolbarMenu.findItem(R.id.remove_btn).setVisible(true);
            try (DatabaseWrapper dbw = new DatabaseWrapper(MainActivity.getInstance(), "myDB")) {
                note = dbw.getNote(clickedNoteId);
                date_field.setText(getUnderlinedText(note.getDate()));
                title_field.setText(note.getTitle());
                note_text_field.setText(note.getBody());

                allTags = dbw.getAllTags();
                noteTags = dbw.getTagsByNoteId(note.getId());

                for (int i = 0; i < allTags.length; i++) {
                    if (isTagBelongNote(allTags[i].getId(), noteTags)) {
                        tg.addTagToScreen(allTags[i].getId(), allTags[i].getName(), true);
                    } else {
                        tg.addTagToScreen(allTags[i].getId(), allTags[i].getName(), false);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            toolbarMenu.findItem(R.id.remove_btn).setVisible(false);
            try (DatabaseWrapper dbw = new DatabaseWrapper(MainActivity.getInstance(), "myDB")) {
                date_field.setText(getUnderlinedText(getCurrentDate()));
                title_field.setText("");
                note_text_field.setText("");

                allTags = dbw.getAllTags();
                for (int i = 0; i < allTags.length; i++) {
                    tg.addTagToScreen(allTags[i].getId(), allTags[i].getName(), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setInitialData();
    }

    private boolean isTagBelongNote(int tagId, Tag[] noteTags) {
        for (int j = 0; j < noteTags.length; j++) {
            if (tagId == noteTags[j].getId()) {
                return true;
            }
        }
        return false;
    }

    private static void setStatusBar(Activity activity) {
        View someView = activity.findViewById(R.id.edit_note_tool_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            someView.setSystemUiVisibility(someView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, android.R.color.white));
        }
    }

    private void setActivitiesItems() {
        date_field = (TextView) findViewById(R.id.date_field);
        date_field.setOnClickListener(this);
        title_field = (EditText) findViewById(R.id.title_field);
        note_text_field = (EditText) findViewById(R.id.note_text_field);
        edit_note_tool_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.edit_note_tool_bar);
        bottom_sheet_btn = (FloatingActionButton) findViewById(R.id.bottom_sheet_btn);
        bottom_sheet_btn.setOnClickListener(this);
        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        additionTags = new ArrayList<Integer>();
        removalTags = new ArrayList<Integer>();
    }

    private void setToolBar() {
        setSupportActionBar(edit_note_tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        edit_note_tool_bar.setSubtitleTextColor(getResources().getColor(R.color.theme_color));
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


