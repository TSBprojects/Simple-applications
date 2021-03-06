package ru.sstu.vak.tasks.notes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ru.sstu.vak.tasks.notes.SQLite.DatabaseWrapper;
import ru.sstu.vak.tasks.notes.SQLite.Entities.Note;

public class NotesFragment extends Fragment implements View.OnClickListener {

    private static final String ARGUMENT_PAGE_NUMBER = "page_number";

    private int clickedNoteId = -1;

    private int pageNumber;

    public int getClickedNoteId() {
        return clickedNoteId;
    }

    public void setClickedNoteId(int clickedNoteId) {
        this.clickedNoteId = clickedNoteId;
    }

    static NotesFragment newInstance(int pageNum) {
        NotesFragment pageFragment = new NotesFragment();
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
        View currentView = inflater.inflate(R.layout.notes_fragment, null);
        LinearLayout notes_linearLayout = (LinearLayout) currentView.findViewById(R.id.notes_linearLayout);
        NotesFactory nf = new NotesFactory(this.getContext(), notes_linearLayout, this);

        try (DatabaseWrapper dbw = new DatabaseWrapper(MainActivity.getInstance(), "myDB")) {
            Note[] notes = dbw.getAllNotes();
            for (int i = 0; i < notes.length; i++) {
                nf.addNoteToScreen(notes[i].getId(), notes[i].getDate(), notes[i].getTitle(), notes[i].getBody(), dbw.getTagsByNoteId(notes[i].getId()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentView;
    }

    @Override
    public void onClick(View view) {
        MainActivity.getInstance().initializeFragments();
        clickedNoteId = view.getId();
        Intent intent = new Intent(MainActivity.getInstance(), NoteEditActivity.class);
        startActivity(intent);
    }
}