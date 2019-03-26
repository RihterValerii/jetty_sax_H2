package servlets;

import dbService.DBService;
import org.h2.jdbc.JdbcSQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DropServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DBService dbService = new DBService();
        try{
            dbService.cleanUp();
            resp.getWriter().write("table is cleared");
        }
        catch (Exception e){
            resp.getWriter().write("table is already cleared");
        }

    }

}
