package wenhao.practice.morsi;

/**
 * Created by wenhaowu on 17/01/16.
 */
public class Obj_Usr {
    private String userName;
    private String userAvatar;

    public Obj_Usr() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    @Override
    public String toString() {
        return "Obj_Usr{" +
                "userName='" + userName + '\'' +
                ", UsrAvatar='" + userAvatar + '\'' +
                '}';
    }

    public Obj_Usr(String userName, String userAvatar) {
        this.userName = userName;
        this.userAvatar = userAvatar;
    }
}
