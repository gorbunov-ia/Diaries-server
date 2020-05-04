package ru.gorbunov.diaries.converters;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.domain.Note;

/**
 * Converter Note class to NoteDto class.
 *
 * @author Gorbunov.ia
 */
@Mapper(config = DefaultConfig.class)
public interface NoteToNoteDtoConverter extends Converter<Note, NoteDto> {

    @Override
    NoteDto convert(Note source);

}
