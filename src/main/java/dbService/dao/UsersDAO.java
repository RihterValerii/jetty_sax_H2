package dbService.dao;

import dbService.executor.Executor;
import entity.Job;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public List<Job> getAll() throws SQLException {
        return executor.execQuery("select * from jobs", result -> {
            List<Job> list = new ArrayList<>();
            while (result.next()){
                list.add(new Job(result.getString(4), result.getString(3), result.getString(5), result.getInt(2), result.getInt(1), result.getLong(6)));
            }
            return list;
        });
    }

    public List<Job> getQuery(String query) throws SQLException {
        return executor.execQuery(query, result -> {
            List<Job> list = new ArrayList<>();
            while (result.next()){
                list.add(new Job(result.getString(4), result.getString(3), result.getString(5), result.getInt(2), result.getInt(1), result.getLong(6)));
            }
            return list;
        });
    }

    public Job getJob(int id) throws SQLException {
        return executor.execQuery("select * from jobs where id=" + id, result -> {
            result.next();

            return new Job(result.getString(4), result.getString(3), result.getString(5), result.getInt(2), result.getInt(1), result.getInt(6));
        });
    }

    public void insertJob(Job job) throws SQLException {

        executor.execUpdate("insert into jobs (id, amount, user, type, device, date) values ("+job.getId()+", "+job.getAmount()+", '" +job.getUser()+ "', '" +job.getType()+ "', '" +job.getDevice()+ "', "+job.getDate()+")");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists jobs (id bigint, amount bigint, user varchar(256), type varchar(256), device varchar(256), date BIGINT, primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table jobs");
    }
}
