package ru.gorbunov.diaries.converters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.gorbunov.diaries.controller.dto.UserDto;
import ru.gorbunov.diaries.domain.User;

import java.util.Random;

/**
 * Test for UserToUserDtoConverter class.
 *
 * @author Gorbunov.ia
 */
class UserToUserDtoConverterTest {

    /**
     * Error message.
     */
    private static final String CONVERT_ERR_MSG = "Data lost when converted User to UserDto";

    /**
     * Converter instance.
     */
    private final UserToUserDtoConverter converter = new UserToUserDtoConverter();

    /**
     * Test convert empty User object.
     */
    @Test
    void testConvertEmptyObject() {
        UserDto userDto = converter.convert(new User());
        Assertions.assertThat(userDto).as("UserDto object is null, but User not null.").isNotNull();
    }

    /**
     * Test convert User object with filled field.
     */
    @Test
    void testConvertFullObject() {
        final Integer userId = new Random().nextInt();
        final User user = getTestUser(userId);
        final UserDto userDto = converter.convert(user);

        Assertions.assertThat(userDto.getId()).as(CONVERT_ERR_MSG).isEqualTo(userId);
        Assertions.assertThat(userDto.getLogin()).as(CONVERT_ERR_MSG).isEqualTo("unitTest");
        Assertions.assertThat(userDto.getEmail()).as(CONVERT_ERR_MSG).isEqualTo("unit@test");
        Assertions.assertThat(userDto.getActive()).as(CONVERT_ERR_MSG).isTrue();
    }

    /**
     * Test convert null User object.
     */
    @Test
    void testConvertNullSource() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> converter.convert(null));
    }

    /**
     * Help method for creating test user object.
     *
     * @param userId user id
     * @return user object
     */
    private User getTestUser(final Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setLogin("unitTest");
        user.setEmail("unit@test");
        user.setIsActive(true);
        return user;
    }
}
