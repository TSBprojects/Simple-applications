package ru.sstu.vak.tasks.notes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.sstu.vak.tasks.notes.SQLite.DatabaseWrapper;
import ru.sstu.vak.tasks.notes.SQLite.Entities.Note;
import ru.sstu.vak.tasks.notes.SQLite.Entities.Tag;

public class TagsFragment extends Fragment implements View.OnClickListener {

    private static final String ARGUMENT_PAGE_NUMBER = "page_number";

    private int clickedTagId = -1;

    private int pageNumber;

    public int getClickedTagId() {
        return clickedTagId;
    }

    public void setClickedTagId(int clickedTagId) {
        this.clickedTagId = clickedTagId;
    }

    static TagsFragment newInstance(int pageNum) {
        TagsFragment pageFragment = new TagsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, pageNum);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.tags_fragment, null);
        LinearLayout tags_linearLayout = (LinearLayout) currentView.findViewById(R.id.tags_linearLayout);
        TagsFactory tf = new TagsFactory(this.getContext(), tags_linearLayout, this);

        try (DatabaseWrapper dbw = new DatabaseWrapper(MainActivity.getInstance(), "myDB")) {
            Tag[] tags = dbw.getAllTags();
            for (int i = 0; i < tags.length; i++) {
                tf.addTagToScreen(tags[i].getId(), tags[i].getName(), false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentView;
    }


    @Override
    public void onClick(View view) {
        MainActivity.getInstance().initializeFragments();
        clickedTagId = view.getId();
        Intent intent = new Intent(MainActivity.getInstance(), TagEditActivity.class);
        startActivity(intent);
    }

}