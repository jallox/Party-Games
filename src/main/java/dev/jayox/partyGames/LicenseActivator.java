package dev.jayox.partyGames;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import org.bukkit.Bukkit;
import org.json.JSONObject; // Make sure to include the org.json library
import java.util.logging.Logger;

public class LicenseActivator {
    private static final Logger logger = Bukkit.getLogger();
    private static final String API_URL = "http://84.247.167.236:25572/licenses/check"; // Replace with your API endpoint

    public int activateLicense(String licenseKey, String productName) {
        HttpClient client = HttpClient.newHttpClient();

        try {
            // Build the GET request with query parameters
            String urlWithParams = String.format("%s?key=%s&pn=%s", API_URL, licenseKey, productName);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(urlWithParams))
                    .GET() // Use GET instead of POST
                    .header("Accept", "application/json") // Set the header to accept JSON responses
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the raw response body and status code
            logger.info("[PartyGames]: [License activator]: Raw response: " + response.body());
            logger.info("[PartyGames]: [License activator]: Response status code: " + response.statusCode());

            // Check for HTTP error status codes
            if (response.statusCode() != 200) {
                logger.warning("[PartyGames]: [License activator]: API call failed with status code: " + response.statusCode());
                return 0; // Default to invalid on error
            }

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.body());
            // Check if the response indicates the license is valid
            if (jsonResponse.getString("message").equalsIgnoreCase("License is valid and product enabled")) {
                return 1; // License activated successfully
            } else {
                logger.warning("[PartyGames]: [License activator]: License activation failed: " + jsonResponse.getString("message"));
                return 0; // License is invalid
            }
        } catch (Exception e) {
            // Handling network or server errors
            logger.warning("[PartyGames]: [License activator]: Error activating license: " + e.getMessage());
            return 0; // Default to invalid on error
        }
    }
}
