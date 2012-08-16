package poc.socialtext.model;

import com.google.gson.Gson;

import java.util.Arrays;

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

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "Signal{" +
                "id=" + id +
                ", userId=" + userId +
                ", body='" + body + '\'' +
                ", numberReplies=" + numberReplies +
                ", likers=" + (likers == null ? null : Arrays.asList(likers)) +
                '}';
    }
}
