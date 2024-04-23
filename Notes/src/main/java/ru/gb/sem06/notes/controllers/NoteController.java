package ru.gb.sem06.notes.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.sem06.notes.model.Note;
import ru.gb.sem06.notes.repositories.NoteRepo;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Контроллер заметок
 */

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
@SuppressWarnings("unused")
public class NoteController {
    private final NoteRepo noteRepo;

    /**
     * Создание заметки
     * @param note тело заметки
     * @return созданную заметку
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {

        return ResponseEntity.ok(noteRepo.save(note));
    }

    /**
     * Получение всех заметок
     * @return список всех заметок
     */

    @GetMapping
    public ResponseEntity<List<Note>> getNotes() {
        return ResponseEntity.ok(noteRepo.findAll());
    }

    /**
     * Получение заметки по id
     * @param id идентификатор
     * @return искомую заметку
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        return ResponseEntity.of(noteRepo.findById(id));
    }

    /**
     * Обновление заметки. Можно обновлять только title и/или text, время обновления ставится автоматически
     * @param id идентификатор
     * @param updates новые данные
     * @return измененную заметку
     */

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Note oldNote = noteRepo.findById(id).orElseThrow();
        // пробегаемся по всем обновляемым полям в мапе
        updates.forEach((field, value) -> {
            try {
                // достаем поле из модели
                Field noteField = Note.class.getDeclaredField(field);
                // разрешаем доступ к полю
                noteField.setAccessible(true);
                // устанавливаем новое значение
                noteField.set(oldNote, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        // обновляем время обновления
        oldNote.setUpdated(LocalDateTime.now());
        // сохраняем изменения
        noteRepo.save(oldNote);
        return ResponseEntity.ok(noteRepo.save(oldNote));
    }

    /**
     * Удаление заметки
     * @param id идентификатор
     * @return  статус запроса
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
