package com.src.cityfind.CityLink.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CityLinkController {
    @GetMapping(value = "/connected")
    public String matchCities(@RequestParam String origin, @RequestParam String destination) throws IOException {
        String pathToCsv = "src/main/resources/cityLink.csv";
        String row;
        String[] data = null;
        Map<String, String> cityLinks = new HashMap<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            cityLinks.put(data[0], data[1]);
        }
        csvReader.close();
        if (findLink(cityLinks, origin, destination)) {
            return "yes";
        } else {
            return "no";
        }
    }

    private boolean findLink(Map<String, String> cityLinks, String origin, String destination) {
        return cityLinks.entrySet().stream().filter(p -> p.getKey() != null).anyMatch(city -> (city.getKey().equalsIgnoreCase(origin) && city.getValue().equalsIgnoreCase(destination))||(city.getKey().equalsIgnoreCase(destination) && city.getValue().equalsIgnoreCase(origin)));

    }


}
