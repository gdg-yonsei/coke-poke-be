package com.gdscys.cokepoke.api.geocoding;

import org.geotools.referencing.GeodeticCalculator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GeocodingAPIService {
    @Value("${gcp.geocoding.api-key}")
    private String apiKey;

    private final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    public double[] getCoordinates(String address) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            HttpGet getRequest = new HttpGet(GEOCODING_API_URL + "?address=" + encodedAddress + "&key=" + apiKey);

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));

                StringBuilder responseStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseStringBuilder.append(line);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseStringBuilder.toString());

                double latitude = jsonNode.path("results").path(0).path("geometry").path("location").path("lat").asDouble();
                double longitude = jsonNode.path("results").path(0).path("geometry").path("location").path("lng").asDouble();

                return new double[]{latitude, longitude};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        GeodeticCalculator calculator = new GeodeticCalculator();
        Coordinate coord1 = new Coordinate(lon1, lat1);
        Coordinate coord2 = new Coordinate(lon2, lat2);

        calculator.setStartingGeographicPoint(coord1.x, coord1.y);
        calculator.setDestinationGeographicPoint(coord2.x, coord2.y);

        return calculator.getOrthodromicDistance() / 1000; // Convert to kilometers
    }

}
