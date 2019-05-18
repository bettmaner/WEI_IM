package edu.ncu.zww.app.wei_im.mvp.model.bean;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public final class DialogsFactory {

    static SecureRandom rnd = new SecureRandom();
    static String getRandomId() {
        return Long.toString(UUID.randomUUID().getLeastSignificantBits());
    }

    private DialogsFactory() {
        throw new AssertionError();
    }


    public static ArrayList<Dialog> getDialogs() {
        ArrayList<Dialog> chats = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -(i * i));
            calendar.add(Calendar.MINUTE, -(i * i));

            chats.add(getDialog(i, calendar.getTime()));

        }

        return chats;
    }

    private static Dialog getDialog(int i, Date lastMessageCreatedAt) {
        ArrayList<Contact> users = getUsers(i);
        switch (i) {
            case 0:
                return new Dialog(
                        getRandomId(),
                        "王同学",
                        "http://img.touxiangs.com/full/f8b426697174d9f510c4a8696e3708d4d278fd40.jpg",
                        users,
                        getMessage(lastMessageCreatedAt,i),
                        i < 3 ? 3 - i : 0);
            case 1:
                return new Dialog(
                        getRandomId(),
                        "李同学",
                        "http://img.touxiangs.com/full/f28c5d3b5101efad94bcc094ebaf0f8e94528115.jpg",
                        users,
                        getMessage(lastMessageCreatedAt,i),
                        i < 3 ? 3 - i : 0);
            default:
                return new Dialog(
                        getRandomId(),
                        "群聊1",
                        "http://img.touxiangs.com/full/f28c5d3b5101efad94bcc094ebaf0f8e94528115.jpg",
                        users,
                        getMessage(lastMessageCreatedAt,i),
                        i < 3 ? 3 - i : 0);
        }
    }

    // 返回成员
    private static ArrayList<Contact> getUsers(int size) {
        ArrayList<Contact> users = new ArrayList<>();
        switch (size) {
            case 3:
                users.add(getUser(0));
            case 2:
                users.add(getUser(1));
            case 1:
                users.add(getUser(2));
                break;
            default:
        }
        return users;
    }

    private static Contact getUser(int number) {
        switch (number) {
            case 0:
                return new Contact(123,"温泉3",
                        "http://img.touxiangs.com/full/f8b426697174d9f510c4a8696e3708d4d278fd40.jpg",
                        true);
            case 1:
                return new Contact(1234,"气温4",
                        "http://img.touxiangs.com/full/f28c5d3b5101efad94bcc094ebaf0f8e94528115.jpg",
                        true);
            default:
                return new Contact(1235,"大武当5",
                        "http://img.touxiangs.com/full/951616b0c9eb96f8832adf9903887f48e9ef9303.jpg",
                        true);
        }
    }

    public static Message getMessage(final Date date,int i) {
        Message message =
         new Message(
                getRandomId(),
                getUser(i),
                getUser(i).getId()+"说了个笑话",
                date);
        if (i==1) {
            ImgMsgBody img = new ImgMsgBody();
            img.setThumbUrl("http://pic19.nipic.com/20120323/9248108_173720311160_2.jpg");
            message.setImage(img);
        }
        return message;
    }
}
