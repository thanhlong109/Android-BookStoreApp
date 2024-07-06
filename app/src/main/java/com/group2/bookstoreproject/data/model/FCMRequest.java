package com.group2.bookstoreproject.data.model;


public class FCMRequest {
    private Message message;

    public FCMRequest(Message message) {
        this.message = message;
    }

    // Getter and setter methods
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class Message {
        private String topic;
        private Notification notification;

        public Message(String topic, Notification notification) {
            this.topic = topic;
            this.notification = notification;
        }

        // Getter and setter methods
        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Notification getNotification() {
            return notification;
        }

        public void setNotification(Notification notification) {
            this.notification = notification;
        }
    }

    public static class Notification {
        private String title;
        private String body;

        public Notification(String title, String body) {
            this.title = title;
            this.body = body;
        }

        // Getter and setter methods
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
