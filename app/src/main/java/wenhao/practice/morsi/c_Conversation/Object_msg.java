package wenhao.practice.morsi.c_Conversation;

/**
 * Created by wenhaowu on 25/01/16.
 */
public class Object_msg implements Comparable<Object_msg>{

    private int direction;
    private Long timeStamp;
    private String message;

    public Object_msg(Long timeStamp, String message, int direction) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.direction = direction;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public int compareTo(Object_msg another) {
        return this.timeStamp.compareTo(another.timeStamp);
    }


}
