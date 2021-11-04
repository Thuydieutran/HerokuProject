package wcsdata.xmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wcsdata.xmen.entity.CerebookUser;
import wcsdata.xmen.entity.Post;
import wcsdata.xmen.repository.CerebookUserRepository;
import wcsdata.xmen.repository.PostDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SampleController {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CerebookUserRepository cerebookUserDao;

    @Autowired
    private PostDAO postDao;

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/cerebookUsers")
    @ResponseBody
    List<CerebookUser> getAllCerebookUser() {
        List<CerebookUser> cerebookUsers = new ArrayList<>();
        cerebookUserDao.findAll().forEach(cerebookUsers::add);

        return cerebookUsers;
    }

    @GetMapping("/cerebookPosts")
    @ResponseBody
    List<Post> getAllCerebookPosts() {
        List<Post> cerebookPost = new ArrayList<>();
        postDao.findAll().forEach(cerebookPost::add);

        return cerebookPost;
    }

    @RequestMapping("/db")
    String db(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

            ArrayList<String> output = new ArrayList<String>();
            while (rs.next()) {
                output.add("Read from DB: " + rs.getTimestamp("tick"));
            }

            model.put("records", output);
            return "db";
        } catch (Exception e) {
            model.put("message", e.getMessage());
            return "error";
        }
    }
}
