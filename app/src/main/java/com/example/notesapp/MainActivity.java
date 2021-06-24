package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.notesapp.Activity.InsertNotesActivity;
import com.example.notesapp.Adapter.NotesAdapter;
import com.example.notesapp.Model.Notes;
import com.example.notesapp.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNoteBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;

    TextView nofilter,hightolow,lowtohigh;
    List<Notes> filternotesalllist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNoteBtn = findViewById(R.id.newNotesBtn);
        notesRecycler=findViewById(R.id.notesRecycler);

        nofilter=findViewById(R.id.nofilter);
        hightolow=findViewById(R.id.hightolow);
        lowtohigh=findViewById(R.id.lowtohigh);

        nofilter.setBackgroundResource(R.drawable.filter_selected);

        nofilter.setOnClickListener(v -> {
            loadData(0);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            nofilter.setBackgroundResource(R.drawable.filter_selected);
        });
        hightolow.setOnClickListener(v -> {
            loadData(1);
            hightolow.setBackgroundResource(R.drawable.filter_selected);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
        });
        lowtohigh.setOnClickListener(v -> {
            loadData(2);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_selected);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
        });

        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class);

        newNoteBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });
        notesViewModel.getallNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filternotesalllist = notes;
            }
        });
    }

    private void loadData(int i) {

        if(i==0) {
            notesViewModel.getallNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });
        }else if(i==1){
            notesViewModel.hightolow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });
        }else if(i==2){
            notesViewModel.lowtohigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });
        }
    }

    public void setAdapter(List<Notes> notes){

            notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            adapter=new NotesAdapter(MainActivity.this,notes);
            notesRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_notes,menu);

        MenuItem menuItem=menu.findItem(R.id.app_bar_search);

        SearchView searchView =(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search Notes here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {

        ArrayList<Notes> FilterNames = new ArrayList<>();

        for(Notes notes:this.filternotesalllist) {

            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText))
            {
                FilterNames.add(notes);

            }

        }
        this.adapter.searchNotes(FilterNames);


    }
}