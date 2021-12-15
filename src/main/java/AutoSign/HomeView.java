package AutoSign;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomeView {

    private String url;
    private String embed;

    public String getURL() {
        return url;
    }

    public String getEmbed(){
        String link = getURL();
        String[] videoID = link.split("v=");
        embed = "https://www.youtube.com/embed/" + videoID[1];
        return embed;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void sendURL(String url) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("url", url);

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://python-microservice.herokuapp.com/download");
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
        }
    }

    public void downloadVideo(String url) throws IOException {
        DownloadObject.downloadObject("autosign-334513", "data_bucket_video_swag", "finals.mp4", "/static/images/file.mp4");
    }


    public String printDBURL(){

        // RETRIEVE INFORMATION FROM DATABASE
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "Arius135";
        String str = "<div class=\"ex8\">" + "<br>" + "<li class=\"ex5\">" + "URL" + "</li>" + "<li class=\"ex6\">" + "TITLE" + "</li>" + "<li class=\"ex7\">" + "DURATION" + "</li>"  + "</br>" + "</div>";;

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement("SELECT * FROM videos");
             ResultSet rs = pst.executeQuery()) {

            int i=0;
            while (rs.next()) {

                //System.out.println(rs.getString(1));
                str += "<div class=\"ex2\">" + "<br>" + "<li class=\"ex3\">" + rs.getString(1) + "</li>" + "<li class=\"ex4\">" + rs.getString(2) + "</li>" + "<li class=\"ex1\">" + rs.getString(3) + "</li>"  + "</br>" + "</div>";
                i++;
            }

            //String.innerHTML = str;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HomeView.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        System.out.println(str);

        return str;
    }
}
