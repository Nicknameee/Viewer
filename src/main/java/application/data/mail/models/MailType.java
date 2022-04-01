package application.data.mail.models;

public enum MailType {
    PLAIN_TEXT(1) , HTML(2) , MEDIA(3);

    private Integer key;

    MailType(Integer key) {
        this.key = key;
    }
}
