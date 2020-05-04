package ru.gorbunov.diaries.converters;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import ru.gorbunov.diaries.controller.dto.UserDto;
import ru.gorbunov.diaries.domain.User;

/**
 * Converter User class to UserDto class.
 *
 * @author Gorbunov.ia
 */
@Mapper(config = DefaultConfig.class)
public interface UserToUserDtoConverter extends Converter<User, UserDto> {

    @Override
    UserDto convert(User source);

}
