package com.example.notesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;
import com.example.notesapp.ViewModel.NotesViewModel;
import com.example.notesapp.databinding.ActivityInsertNotesBinding;

import java.util.Date;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding;
    String title,subtitle,notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.tealPriority.setOnClickListener(v -> {
            binding.tealPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.purplePriority.setImageResource(0);
            priority="1";
        });
        binding.yellowPriority.setOnClickListener(v -> {
            binding.tealPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.purplePriority.setImageResource(0);
            priority="2";
        });
        binding.purplePriority.setOnClickListener(v -> {
            binding.tealPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.purplePriority.setImageResource(R.drawable.ic_baseline_done_24);

            priority="3";

        });


        binding.doneNotesBtn.setOnClickListener(v -> {

            title = binding.notesTitle.getText().toString();
            subtitle = binding.notesSubtitle.getText().toString();
            notes = binding.notesData.getText().toString();

            createNotes(title,subtitle,notes);

        });

    }

    private void createNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d,yyyy",date.getTime());

        Notes notes1 = new Notes();
        notes1.notesTitle=title;
        notes1.notesSubtitle=subtitle;
        notes1.notes=notes;
        notes1.notesPriority=priority;
        notes1.notesDate=sequence.toString();
        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}