package com.howsmart.housemart.Notifications;

public class NotificationData {
    private String senderNickname;
    private String message;
    private String receiverFirebaseId;
    private String senderFirebaseId;

    public NotificationData(String senderNickname, String message, String receiverFirebaseId, String senderFirebaseId) {
        this.senderNickname = senderNickname;
        this.message = message;
        this.receiverFirebaseId = receiverFirebaseId;
        this.senderFirebaseId = senderFirebaseId;
    }

    public NotificationData() {
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
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
                "senderNickname='" + senderNickname + '\'' +
                ", message='" + message + '\'' +
                ", receiverFirebaseId='" + receiverFirebaseId + '\'' +
                ", senderFirebaseId='" + senderFirebaseId + '\'' +
                '}';
    }
}
