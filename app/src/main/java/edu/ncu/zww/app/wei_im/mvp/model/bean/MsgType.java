package edu.ncu.zww.app.wei_im.mvp.model.bean;

/**
 * 消息类型
 */
public class MsgType {

    public static String TEXT = "TEXT"; // 文本消息
    public static String AUDIO = "AUDIO"; // 语音消息
    public static String VIDEO = "VIDEO"; // 视频消息
    public static String IMAGE = "IMAGE"; // 图片消息
    public static String FILE = "FILE"; // 文件消息
    public static String LOCATION = "LOCATION"; // 位置消息

    // 根据类型转换text,用于在消息界面显示对应类型
    public static String typeToText(String type) {
        switch (type) {
            case "AUDIO":
                return "[语音]";
            case "VIDEO":
                return "[视频]";
            case "IMAGE":
                return "[图片]";
            case "FILE":
                return "[文件]";
            case "LOCATION":
                return "[位置]";
            default:
                return null;
        }
    }
}