package ru.gorbunov.diaries.converters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.domain.NoteElement;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * Test for NoteElementToNoteElementDtoConverter class.
 *
 * @author Gorbunov.ia
 */
class NoteElementToNoteElementDtoConverterTest {

    /**
     * Error message.
     */
    private static final String CONVERT_ERR_MSG = "Data lost when converted NoteElement to NoteElementDto";

    /**
     * Converter instance.
     */
    private final NoteElementToNoteElementDtoConverter converter = new NoteElementToNoteElementDtoConverter();

    /**
     * Test convert empty Note Element object.
     */
    @Test
    void testConvertEmptyObject() {
        NoteElementDto noteElementDto = converter.convert(new NoteElement());
        Assertions.assertThat(noteElementDto)
                .as("NoteElementDto object is null, but NoteElement not null.")
                .isNotNull();
    }

    /**
     * Test convert Note Element object with filled field.
     */
    @Test
    void testConvertFullObject() {
        final Integer noteElementId = new Random().nextInt();
        final NoteElement noteElement = getTestNoteElement(noteElementId);
        final NoteElementDto noteElementDto = converter.convert(noteElement);

        Assertions.assertThat(noteElementDto.getId()).as(CONVERT_ERR_MSG).isEqualTo(noteElementId);
        Assertions.assertThat(noteElementDto.getDescription()).as(CONVERT_ERR_MSG).isEqualTo("unitTestNoteElement");
        Assertions.assertThat(noteElementDto.getSortBy()).as(CONVERT_ERR_MSG).isEqualTo(Integer.valueOf(1));
        Assertions.assertThat(noteElementDto.getLastModified()).as(CONVERT_ERR_MSG).isEqualTo(Date.from(Instant.EPOCH));
    }

    /**
     * Test convert null Note Element object.
     */
    @Test
    void testConvertNullSource() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> converter.convert(null));
    }

    /**
     * Help method for creating test note element object.
     *
     * @param noteElementId note element id
     * @return note element object
     */
    private NoteElement getTestNoteElement(final Integer noteElementId) {
        NoteElement noteElement = new NoteElement();
        noteElement.setId(noteElementId);
        noteElement.setDescription("unitTestNoteElement");
        noteElement.setSortBy(1);
        noteElement.setLastModified(Date.from(Instant.EPOCH));
        return noteElement;
    }

}
