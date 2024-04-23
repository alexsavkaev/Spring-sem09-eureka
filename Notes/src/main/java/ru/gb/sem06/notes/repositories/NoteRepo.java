package ru.gb.sem06.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.sem06.notes.model.Note;

public interface NoteRepo extends JpaRepository<Note, Long> {
}
