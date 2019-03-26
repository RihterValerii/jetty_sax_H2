package entity;

import java.util.Date;

public class Job {
    private String type;
    private String user;
    private String device;
    private int amount;
    private  int id;
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Job{" +
                "type='" + type + '\'' +
                ", user='" + user + '\'' +
                ", device='" + device + '\'' +
                ", amount=" + amount +
                ", id=" + id +
                ", date: " + new Date(date) +
                '}';
    }

    public Job() {
    }

    public Job(String type, String user, String device, int amount, int id, long date) {
        this.type = type;
        this.user = user;
        this.device = device;
        this.amount = amount;
        this.id = id;
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Job(String type, String user, String device, int amount) {
        this.type = type;
        this.user = user;
        this.device = device;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getDevice() {
        return device;
    }

    public int getAmount() {
        return amount;
    }
}
