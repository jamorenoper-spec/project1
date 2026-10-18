package com.proyecto1.labs.lab3.apis;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/** LAB 3 - Consume el API de Google Maps para convertir una dirección en latitud,longitud. */
@Component("lab3Geocoder")
public class Geocoder {

    private static final Logger logger = LoggerFactory.getLogger(Geocoder.class);

    private static final String PLACES_RESOURCE =
            "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?fields=geometry&inputtype=textquery";
    private static final String GEOCODE_RESOURCE =
            "https://maps.googleapis.com/maps/api/geocode/json?";

    // La llave NO va escrita en el código: se lee de application.properties o de la variable de entorno
    @Value("${google.maps.api-key:}")
    private String apiKey;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public boolean tieneApiKey() {
        return apiKey != null && !apiKey.isBlank();
    }

    private String llamar(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(uri))
                .timeout(Duration.ofMillis(5000)).build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    /** Devuelve "latitud,longitud" o null si Google no encontró la dirección. */
    public String getLatLng(String query) throws IOException, InterruptedException {
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // 1) API Places (la del laboratorio)
        JsonNode root = mapper.readTree(llamar(PLACES_RESOURCE + "&key=" + apiKey + "&input=" + encoded));
        JsonNode candidates = root.get("candidates");
        if (candidates != null && candidates.size() > 0) {
            JsonNode loc = candidates.get(0).get("geometry").get("location");
            return loc.get("lat").asText() + "," + loc.get("lng").asText();
        }

        // 2) Respaldo: API Geocoding (por si Places no está habilitada en tu proyecto de Google)
        root = mapper.readTree(llamar(GEOCODE_RESOURCE + "address=" + encoded + "&key=" + apiKey));
        JsonNode results = root.get("results");
        if (results != null && results.size() > 0) {
            JsonNode loc = results.get(0).get("geometry").get("location");
            return loc.get("lat").asText() + "," + loc.get("lng").asText();
        }

        logger.warn("Google no devolvió coordenadas para '{}'. Estado: {} {}", query,
                root.path("status").asText(), root.path("error_message").asText());
        return null;
    }
}
