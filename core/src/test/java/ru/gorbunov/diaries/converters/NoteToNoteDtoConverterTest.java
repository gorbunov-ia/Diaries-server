package ru.gorbunov.diaries.converters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.domain.Note;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * Test for NoteToNoteDtoConverter class.
 *
 * @author Gorbunov.ia
 */
class NoteToNoteDtoConverterTest {

    /**
     * Error message.
     */
    private static final String CONVERT_ERR_MSG = "Data lost when converted Note to NoteDto";

    /**
     * Converter instance.
     */
    private final NoteToNoteDtoConverter converter = new NoteToNoteDtoConverter();

    /**
     * Test convert empty Note object.
     */
    @Test
    void testConvertEmptyObject() {
        NoteDto noteDto = converter.convert(new Note());
        Assertions.assertThat(noteDto).as("NoteDto object is null, but Note not null.").isNotNull();
    }

    /**
     * Test convert Note object with filled field.
     */
    @Test
    void testConvertFullObject() {
        final Integer noteId = new Random().nextInt();
        final Note note = getTestNote(noteId);
        final NoteDto noteDto = converter.convert(note);

        Assertions.assertThat(noteDto.getId()).as(CONVERT_ERR_MSG).isEqualTo(noteId);
        Assertions.assertThat(noteDto.getDescription()).as(CONVERT_ERR_MSG).isEqualTo("unitTestNote");
        Assertions.assertThat(noteDto.getSortBy()).as(CONVERT_ERR_MSG).isEqualTo(Integer.valueOf(1));
        Assertions.assertThat(noteDto.getLastModified()).as(CONVERT_ERR_MSG).isEqualTo(Date.from(Instant.EPOCH));
    }

    /**
     * Test convert null Note object.
     */
    @Test
    void testConvertNullSource() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> converter.convert(null));
    }

    /**
     * Help method for creating test note object.
     *
     * @param noteId note id
     * @return note object
     */
    private Note getTestNote(final Integer noteId) {
        Note note = new Note();
        note.setId(noteId);
        note.setDescription("unitTestNote");
        note.setSortBy(1);
        note.setLastModified(Date.from(Instant.EPOCH));
        return note;
    }

}
