package com.example.notesapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp.Model.Notes;
import com.example.notesapp.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> getallNotes;

    public LiveData<List<Notes>> hightolow;
    public LiveData<List<Notes>> lowtohigh;


    public NotesViewModel(Application application) {
        super(application);

        repository =new NotesRepository(application);
        getallNotes=repository.getallNotes;
        hightolow=repository.hightolow;
        lowtohigh=repository.lowtohigh;

    }
    public void insertNote(Notes notes){
        repository.insertNotes(notes);
    }
    public void deleteNote(int id) {
        repository.deleteNotes(id);
    }
    public void updateNote(Notes notes) {
        repository.updateNotes(notes);
    }

}
