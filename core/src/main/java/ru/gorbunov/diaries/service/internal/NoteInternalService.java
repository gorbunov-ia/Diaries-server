package ru.gorbunov.diaries.service.internal;

import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.domain.Note;

import java.util.List;
import java.util.Optional;

/**
 * Internal service for interaction with notes.
 *
 * @author Gorbunov.ia
 */
public interface NoteInternalService {

    /**
     * Method to get notes for current user with sorting.
     *
     * @param field     sorting field
     * @param isDesc    ascending or descending sort
     * @return          collection note entity
     */
    List<Note> getUserNotesWithSort(String field, boolean isDesc);

    /**
     * Method to get note for current user by note id.
     *
     * @param noteId note id in db
     * @return note entity or null if note id does not exist for current user
     */
    Optional<Note> getUserNoteById(Integer noteId);

    /**
     * Method to create note for current user.
     *
     * @param noteDto dto
     * @return created entity
     */
    Note createNote(NoteDto noteDto);

    /**
     * Method to delete note by id.
     *
     * @param noteId identifier of entity
     */
    void deleteNote(Integer noteId);

    /**
     * Method to update note.
     *
     * @param noteDto dto
     * @return updated entity
     */
    Note updateNote(NoteDto noteDto);

}
