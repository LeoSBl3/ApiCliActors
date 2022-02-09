package com.apiclient;

// Dessa paket använder vi för att läsa information från och skriva information till HTTP-anslutningar

import com.fasterxml.jackson.databind.*;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ApiClient {
    // Adressen till vår server, exempelvis https://127.0.0.1:8080/api/v1 (Important: Without the / at the end)
    private String apiAddress;
    HttpURLConnection connection;

    // Vår konstruktor
    public ApiClient(String apiAddress) {
        this.apiAddress = apiAddress;
    }

    public ArrayList<String> getStringArray(String target) {
        JsonObject countryObj = new JsonObject();

        ArrayList<String> myArrayOfStrings = new ArrayList<>();

        return myArrayOfStrings;
    }


    public boolean addActor(Actor newActor) {
        String target = "/blog/create";

        //System.out.println("Adding movie at " + apiAddress + target);

        boolean success = false;

        try {
            // Skapa ett URL-objekt och säg vilken adress vi vill skicka information till
            URL url = new URL(apiAddress + target);

            // Öppna nätverksanslutningen
            connection = (HttpURLConnection) url.openConnection();

            // Ange metod
            connection.setRequestMethod("POST");

            // Lägg till header (säg att vi vill skicka JSON-data)
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Konvertera vårt Java-objekt (Actor) till JSON med hjälp av .toJSON-metoden i klassen Actor,
            // och skriv den JSON-datan till vår nätverksanslutning med hjälp av en OutputStream
            try (OutputStream os = connection.getOutputStream()) {
                // Skapa en byte-array som innehåller JSON-datan
                byte[] input = newActor.toJson().getBytes(StandardCharsets.UTF_8);

                // Skriv byte-arrayen till nätverksanslutningen (vi måste också ange hur lång strängen är)
                os.write(input, 0, input.length);
            }

            // Vad fick vi för svar? Vad var HTTP-statuskoden vi fick tillbaka?
            int status = connection.getResponseCode();

            // Generellt om HTTP-koden är över 300 har något gått fel
            // Om den är 299 eller lägre har det gått bra
            // (Exempelvis är "200 OK" bra och "404 Not Found" inte bra)
            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }

    public Actor[] getActors() {
        Actor[] actors = {};

        String target = "/blog/list";

        //System.out.println("Getting actors from " + apiAddress + target);

        // Se kommentarer i metoden addMovie()
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        // Se kommentarer i metoden addMovie()
        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            //System.out.println(responseContent.toString());
            String jsonStr = responseContent.toString();

            // Se kommentarer i metoden addMovie()
            ObjectMapper mapper = new ObjectMapper();
            actors = mapper.readValue(jsonStr, Actor[].class);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return actors;
    }

    public Actor getActorById(int id) {
        Actor actorObject = new Actor();

        String target = "/blog/view/"+ id;

        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            //System.out.println(responseContent.toString());
            String jsonStr = responseContent.toString();

            // Se kommentarer i metoden addMovie()
            ObjectMapper mapper = new ObjectMapper();
            actorObject = mapper.readValue(jsonStr, Actor.class);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return actorObject;
    }

    public boolean updateActor(int id, Actor actorChanges) {
        String target = "/blog/update/"+ id;

        //System.out.println("Adding movie at " + apiAddress + target);

        boolean success = false;

        try {
            // Skapa ett URL-objekt och säg vilken adress vi vill skicka information till
            URL url = new URL(apiAddress + target);

            // Öppna nätverksanslutningen
            connection = (HttpURLConnection) url.openConnection();

            // Ange metod
            connection.setRequestMethod("POST");

            // Lägg till header (säg att vi vill skicka JSON-data)
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Konvertera vårt Java-objekt (Actor) till JSON med hjälp av .toJSON-metoden i klassen Actor,
            // och skriv den JSON-datan till vår nätverksanslutning med hjälp av en OutputStream
            try (OutputStream os = connection.getOutputStream()) {
                // Skapa en byte-array som innehåller JSON-datan
                byte[] input = actorChanges.toJson().getBytes(StandardCharsets.UTF_8);

                // Skriv byte-arrayen till nätverksanslutningen (vi måste också ange hur lång strängen är)
                os.write(input, 0, input.length);
            }

            // Vad fick vi för svar? Vad var HTTP-statuskoden vi fick tillbaka?
            int status = connection.getResponseCode();

            // Generellt om HTTP-koden är över 300 har något gått fel
            // Om den är 299 eller lägre har det gått bra
            // (Exempelvis är "200 OK" bra och "404 Not Found" inte bra)
            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }


    public boolean deleteActor(int id) {
        String target = "/blog/delete/"+ id;

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();

            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }
    public boolean deleteAllActors() {
        String target = "/blog/clear";

        //System.out.println("Clearing actor from " + apiAddress + target);

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();

            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }


}
