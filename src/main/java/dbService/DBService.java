package dbService;

import dbService.dao.UsersDAO;
import org.h2.jdbcx.JdbcDataSource;
import entity.Job;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getH2Connection();
    }

    public List<Job> getAll() throws Exception{
        UsersDAO dao = new UsersDAO(connection);
        return dao.getAll();
    }
    public List<Job> getJobsForQuery(String query) throws Exception{
        UsersDAO dao = new UsersDAO(connection);
        return dao.getQuery(query);
    }

    public boolean addJob(Job job) {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            dao.insertJob(job);
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public void cleanUp() {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "tully";
            String pass = "tully";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
