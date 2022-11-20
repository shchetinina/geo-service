package ru.netology.sender;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageSenderTest {
    private static GeoService geoService;
    private static LocalizationService localizationService;

    @BeforeAll
    static void setup(){
        geoService = Mockito.mock(GeoService.class);
        localizationService = Mockito.mock(LocalizationService.class);
    }

    @ParameterizedTest
    @MethodSource("getHeadersWithUSA")
    void sendUSA(Map<String, String> headers, String expectedResult){
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(new Location("city", Country.USA, "street", 1));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        String actualResult = messageSender.send(headers);

        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("getHeadersWithRussia")
    void sendRussia(Map<String, String> headers, String expectedResult){
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(new Location("city", Country.RUSSIA, "street", 1));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        String actualResult = messageSender.send(headers);

        assertEquals(expectedResult, actualResult);
    }

    static Stream<Arguments> getHeadersWithUSA(){
        return Stream.of(
                Arguments.of(Map.of("x-real-ip", ""), "Welcome"),
                Arguments.of(Map.of(), "Welcome"),
                Arguments.of(Map.of("x-real-ip", ""), "Welcome"),
                Arguments.of(Map.of("x-real-ip", "96.44.183.149"), "Welcome"),
                Arguments.of(Map.of("x-real-ip", "96.11.182.147"), "Welcome")
        );
    }

    static Stream<Arguments> getHeadersWithRussia(){
        return Stream.of(
                Arguments.of(Map.of("x-real-ip", "127.0.0.1"), "Добро пожаловать"),
                Arguments.of(Map.of("x-real-ip", "172.0.32.11"), "Добро пожаловать"),
                Arguments.of(Map.of("x-real-ip", "172.0.11.11"), "Добро пожаловать")
        );
    }
}