package miguelsilva.a029187.pokemoncarddatabase.JavaClasses;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Comunicar {
    public static String contactar(String host, int port, String path) throws IOException {
        URL url = new URL("http", host, port, path);
        URLConnection conn = url.openConnection();
        // Isto faz um http GET, para fazer um POST usar: conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setAllowUserInteraction(true); // useless but harmless
        conn.connect();
        // Para fazer um http POST escrevia-se para: conn.getOutputStream());
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    public static String contactar2(String host, String path, int port) {
        String resultado = "<unknown>";
        try {
            resultado = contactar(host, port, path);
        } catch (IOException e) {
            resultado = "<failure>";
            resultado += e.getMessage();
        }
        return resultado;
    }

    public static String contactar(String host, int port, String path, String username, String password) throws IOException {
        URL url = new URL("http", host, port, path);
        URLConnection conn = url.openConnection();
        String credentials = username + ":" + password;
        String base64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);

        // Isto faz um http GET, para fazer um POST usar: conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setAllowUserInteraction(true); // useless but harmless
        conn.setRequestProperty("Authorization", "Basic " + base64);
        conn.connect();
        // Para fazer um http POST escrevia-se para: conn.getOutputStream());
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    public static String contactar2(String host, String path, int port, String username, String password) {
        String resultado = "<unknown>";
        String credentials = username + ":" + password;
        String base64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
        try {
            resultado = contactar(host, port, path);
        } catch (IOException e) {
            resultado = "<failure>";
        }
        return resultado;
    }
}