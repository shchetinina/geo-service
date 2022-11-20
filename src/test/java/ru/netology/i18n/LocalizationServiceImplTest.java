package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalizationServiceImplTest {
    private final LocalizationService localizationService = new LocalizationServiceImpl();

    @ParameterizedTest
    @MethodSource("getCountries")
    void checkCountryLocalization(Country country, String expectedResult){
        String actualResult = localizationService.locale(country);

        assertEquals(expectedResult, actualResult);
    }

    static Stream<Arguments> getCountries(){
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}