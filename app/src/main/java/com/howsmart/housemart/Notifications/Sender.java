package com.howsmart.housemart.Notifications;

public class Sender {

    private String to;
    private NotificationData data;

    public Sender(String to, NotificationData data) {
        this.to = to;
        this.data = data;
    }

    public Sender() {
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "to='" + to + '\'' +
                ", data=" + data +
                '}';
    }
}
