package ru.netology.geo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {
    private final GeoService geoService = new GeoServiceImpl();

    @ParameterizedTest
    @MethodSource("getIps")
    void checkGetByIP(String ip, Location expectedLocation){
        Location actualLocation = geoService.byIp(ip);

        assertEquals(expectedLocation.getCountry(), actualLocation.getCountry());
        assertEquals(expectedLocation.getCity(), actualLocation.getCity());
        assertEquals(expectedLocation.getStreet(), actualLocation.getStreet());
        assertEquals(expectedLocation.getBuiling(), actualLocation.getBuiling());
    }

    @Test
    void checkNullLocation(){
        Location actualLocation = geoService.byIp("123.0.0.0");

        assertNull(actualLocation);
    }

    @Test
    void checkGetByCoordinates(){
        assertThrows(RuntimeException.class, () -> geoService.byCoordinates(new Random().nextDouble(),
                new Random().nextDouble()));
    }

    static Stream<Arguments> getIps(){
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.9.9.9", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.31.31.31", new Location("New York", Country.USA, null,  0))
        );
    }

}