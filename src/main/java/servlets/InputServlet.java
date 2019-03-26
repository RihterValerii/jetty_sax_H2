package servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import dbService.DBService;
import entity.Job;
import saxService.SaxService;
import sun.misc.IOUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InputServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Date date = new Date();
        InputStream i = request.getInputStream();
        byte[] bytes = IOUtils.readFully(i, -1, false);
        String input = new String(bytes);
        DBService dbService = new DBService();
        List<Job> jobs = new ArrayList<>();
        try {
            jobs = new SaxService(date.getTime()).parseRequest(input);
            for(Job job : jobs){
                dbService.addJob(job);
                System.out.println(job);
            }
            System.out.println(dbService.getAll());
            //dbService.cleanUp();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = createResponse(jobs);
        jobs.clear();
        System.out.println(json);
        response.getWriter().write(json);
        response.setStatus(HttpServletResponse.SC_OK);
    }


    public String createResponse(List<Job> jobs){
        Map<String, Integer> map = new HashMap<>();
        for(Job job : jobs) {
            String user = job.getUser();
            if(map.containsKey(user)){
                Integer amount = map.get(user) + job.getAmount();
                map.put(user, amount);
            }
            else {
                map.put(user, job.getAmount());
            }
        }
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
