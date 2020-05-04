package ru.gorbunov.diaries.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.domain.NoteElement;

/**
 * Converter NoteElement class to NoteElementDto class.
 *
 * @author Gorbunov.ia
 */
@Mapper(config = DefaultConfig.class)
public interface NoteElementToNoteElementDtoConverter extends Converter<NoteElement, NoteElementDto> {

    @Override
    @Mapping(target = "noteId", ignore = true)
    @Mapping(target = "sortElementVm", ignore = true)
    NoteElementDto convert(NoteElement source);
}
