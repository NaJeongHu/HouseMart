package com.howsmart.housemart.Notifications;

public class NotificationData {
    private String receiverNickname;
    private String message;
    private String receiverFirebaseId;
    private String senderFirebaseId;

    public NotificationData(String receiverNickname, String message, String receiverFirebaseId, String senderFirebaseId) {
        this.receiverNickname = receiverNickname;
        this.message = message;
        this.receiverFirebaseId = receiverFirebaseId;
        this.senderFirebaseId = senderFirebaseId;
    }

    public NotificationData() {
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiverFirebaseId() {
        return receiverFirebaseId;
    }

    public void setReceiverFirebaseId(String receiverFirebaseId) {
        this.receiverFirebaseId = receiverFirebaseId;
    }

    public String getSenderFirebaseId() {
        return senderFirebaseId;
    }

    public void setSenderFirebaseId(String senderFirebaseId) {
        this.senderFirebaseId = senderFirebaseId;
    }

    @Override
    public String toString() {
        return "NotificationData{" +
                "receiverNickname='" + receiverNickname + '\'' +
                ", message='" + message + '\'' +
                ", receiverFirebaseId='" + receiverFirebaseId + '\'' +
                ", senderFirebaseId='" + senderFirebaseId + '\'' +
                '}';
    }
}
