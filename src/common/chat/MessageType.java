package common.chat;/*
定义包的种类
 */


public interface MessageType {
    String message_succeed="1";
    String message_login_fail="2";
    String message_comm_mes="3";//普通信息包
    String message_get_onLineFriend="4";
    String message_ret_onLineFriend="5";//返回在线好友的包
}
