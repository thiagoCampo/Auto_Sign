package com.example.auto_sign;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("home", new HomeView());

        /*String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        Connection conn= null;
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }

        try {
            Statement s=conn.createStatement();
            String sqlStr = "insert into videos (url,title, duration) values('https://www.youtube.com/watch?v=1-t_PSFyzXs','Peppa pig','8.59')”;
            s.execute(sqlStr);
            s.close();
            conn.close();
        }
        catch (Exception e){
        }*/

        // -------------
        /*String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "Arius135";*/

        // TEST CONNECTION WITH DATABASE
       /* try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HomeController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        // FILL IN THE TABLE
        String link = "https://www.youtube.com/hello_2";
        String title = "Peppa pig video";
        String duration = "8 min";


        String query = "INSERT INTO videos(url, title, duration) VALUES(?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, link);
            pst.setString(2, title);
            pst.setString(3, duration);
            pst.executeUpdate();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HomeController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }*/
        

        return "home";
    }

    @PostMapping("/")
    public String URLsubmit(@ModelAttribute HomeView home, Model model) throws IOException {
        model.addAttribute("home", home);
        System.out.println(home.getURL());

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "Arius135";

        // TEST CONNECTION WITH DATABASE
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HomeController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        // ---------------------------
        // RETRIEVE INFORMATION FROM YOUTUBE VIDEO
        String link = home.getURL();
        String[] videoID = link.split("v=");

        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("video-test").build();

        final String videoId = videoID[1];
        YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
        videoRequest.setId(videoId);
        videoRequest.setKey("AIzaSyClhWbvW7KER61FWEw5hz4AdDg8opLQQ1M");
        VideoListResponse listResponse = videoRequest.execute();
        List<Video> videoList = listResponse.getItems();

        Video targetVideo = videoList.iterator().next();

        String title = targetVideo.getSnippet().getTitle();
        String duration = targetVideo.getContentDetails().getDuration();

        String[] duration_1 = duration.split("PT");
        String duration_check = duration_1[1];
        String hours = "00";
        String minutes = "00";
        String seconds = "00";

        if (duration_1[1].indexOf("H") != -1)
        {
            hours = (duration_1[1].split("H"))[0];
            duration_check = (duration_1[1].split("H"))[1];
        }
        if (duration_1[1].indexOf("M") != -1)
        {
            minutes = duration_check.split("M")[0];
            duration_check = duration_check.split("M")[1];
        }
        if (duration_1[1].indexOf("S") != -1)
        {
            seconds = duration_check.split("S")[0];
        }

        System.out.println(hours + ":" + minutes + ":" + seconds);



        // ----------------------
        // FILL IN THE TABLE
        String query = "INSERT INTO videos(url, title, duration) VALUES(?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, link);
            pst.setString(2, title);
            pst.setString(3, hours + ":" + minutes + ":" + seconds);
            pst.executeUpdate();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HomeController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return "result";
    }


}




