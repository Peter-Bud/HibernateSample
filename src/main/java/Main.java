import entity.City;
import entity.Country;
import helpers.CityHelper;
import helpers.CountryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(CityHelper.class);

    public static void main(String[] args) {
        CountryHelper countryHelper = new CountryHelper();
        Country existingCountry = countryHelper.getCountry("USA");
        if (existingCountry != null) {
        }else {
            existingCountry = new Country(
                    "USA",
                    "United States",
                    "North America",
                    "Western",
                    new BigDecimal("9833517.85"),
                    (short) 1776,
                    331002651,
                    new BigDecimal("78.8"),
                    new BigDecimal("21.43"),
                    new BigDecimal("20.81"),
                    "United States of America",
                    "Federal Republic",
                    "Joe Biden",
                    1,
                    "US");
        }
        CityHelper cityWorker = new CityHelper();
        cityWorker.addCity(new City(
                "hello",
                existingCountry,
                "test",
                1234567894));

        for (City city : cityWorker.getCityList()) {
            logger.info("city: " + city.getName() + "  Population:" + city.getPopulation());
        }
        cityWorker.deleteCityByName("hello");

        // Now retrieve the city list again using the same instance
        List<City> citiesAfterDeletion = cityWorker.getCityList();
        for (City city : citiesAfterDeletion) {
            logger.info("city: " + city.getName() + "  Population:" + city.getPopulation());
        }
    }
}
