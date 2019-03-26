package servlets;

import dbService.DBService;
import entity.Job;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutputServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String user = req.getParameter("user");
        String type = req.getParameter("type");
        String device = req.getParameter("device");
        String timeFrom = req.getParameter("timeFrom");
        String timeTo = req.getParameter("timeTo");

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        long longTimeFrom = 0;
        long longTimeTo = 0;
        try {
            if (timeFrom != null) {
                longTimeFrom = format.parse(timeFrom).getTime();
                System.out.println(longTimeFrom);
            }
            if (timeTo != null) {
                longTimeTo = format.parse(timeTo).getTime();
                System.out.println(longTimeTo);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();

        builder.append("select * from jobs");
        if (user != null || type != null || device != null) {
            builder.append(" where");
            boolean addUser = false;
            boolean addType = false;
            if (user != null) {
                builder.append(" user = '").append(user).append("'");
                addUser = true;
            }
            if (type != null && !addUser) {
                builder.append(" type = '").append(type).append("'");
                addType = true;
            }
            if (type != null && addUser) {
                builder.append(" and type = '").append(type).append("'");
                addType = true;
            }
            if (device != null && !addUser && !addType) {
                builder.append(" device = '").append(device).append("'");
            }
            if (device != null && (addUser || addType)) {
                builder.append(" and device = '").append(device).append("'");
            }
        }
        builder.append(" group by id");
        boolean addTimeFrom = false;
        if (timeFrom != null) {
            builder.append(" having date >= ").append(longTimeFrom);
            addTimeFrom = true;
        }
        if (timeTo != null && !addTimeFrom) {
            builder.append(" having date <= ").append(longTimeTo);
        }
        if (timeTo != null && addTimeFrom) {
            builder.append(" and date <= ").append(longTimeTo);
        }
        builder.append(" order by date");

        DBService service = new DBService();
        String query = builder.toString();

        List<Job> jobs = new ArrayList<>();
        try {
            System.out.println(query);
            jobs = service.getJobsForQuery(query);
            System.out.println(jobs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray pages = new JSONArray();
        for (Job job : jobs) {
            JSONObject ret = new JSONObject();
            ret.put("jobId", job.getId());
            ret.put("device", job.getDevice());
            ret.put("user", job.getUser());
            ret.put("type", job.getType());
            ret.put("amount", job.getAmount());
            ret.put("time", new Date(job.getDate()));
            pages.put(ret);
        }
        resp.getWriter().write(pages.toString());
    }
}
