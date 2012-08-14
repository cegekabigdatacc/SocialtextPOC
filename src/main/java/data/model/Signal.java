package data.model;

public class Signal {

    private Long id;
    private Long userId;
    private String body;
    private Long numberReplies;
    private Long[] likers;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getBody() {
        return body;
    }

    public Long getNumberReplies() {
        return numberReplies;
    }

    public Long[] getLikers() {
        return likers;
    }
}
