package my.awesome.morsi.b_UserAndGroup;

/**
 * Created by wenhaowu on 17/01/16.
 */
public class Object_User {
    private String userName;
    private String userAvatar;

    public Object_User() {
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

    public Object_User(String userName, String userAvatar) {
        this.userName = userName;
        this.userAvatar = userAvatar;
    }
}
