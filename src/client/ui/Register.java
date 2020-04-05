package ui;

import CScontrol;
import server.entity.User;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame implements ActionListener {
    JTextField userText;
    JPasswordField passText;

    JButton registerButton;
    String username,password;
    User userOfRegister;//注册成功的时候new一个User，传给MainPage

    public Register(){
        super("注册");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        add(panel);
        JLabel userLabel = new JLabel("用户名");
        userText = new JTextField();
        JLabel passLabel = new JLabel("密码");
        passText = new JPasswordField(20);
//        loginButton = new JButton("登录");
//        loginButton.addActionListener(this);
        registerButton = new JButton("注册");
        registerButton.addActionListener(this);
        registerButton.setActionCommand("registerButton");
        panel.setLayout(null);
        //userLabel
        userLabel.setBounds(30, 30, 80, 25);
        panel.add(userLabel);
        //passLabel
        passLabel.setBounds(30, 60, 80, 25);
        panel.add(passLabel);
        userText.setBounds(105, 30, 165, 25);
        panel.add(userText);
        passText.setBounds(105, 60, 165, 25);
        panel.add(passText);

//        loginButton.setBounds(25, 100, 80, 25);
//        panel.add(loginButton);
        registerButton.setBounds(110, 100, 80, 25);
        panel.add(registerButton);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("registerButton")){
            username = userText.getText();
            password= passText.getText();
            if(username.equals("")||password.equals("")){
                JOptionPane.showMessageDialog(this, "请输入用户名或密码！");
                return;
            }
            if(isUsernameLetter(username)){
                if(isPasswordRight(password))
                    //连接服务器注册
                    register();
                else
                    JOptionPane.showMessageDialog(this, "请输入6-12位密码，并且要包含数字和字母");
            }
            else {
                JOptionPane.showMessageDialog(this, "请输入1-8位用户名，至少含有一位字母，不能含有特殊字符！");
                return;
            }
        }
    }
    //确保用户名至少含有字母，而且只能包含字母或数字，且1-8位
    public boolean isUsernameLetter(String str) {
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]{1,8}$";

        return isLetter && str.matches(regex);
    }
    //确保密码至少含有字母和数字，且6-12位
    public boolean isPasswordRight(String str){
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^.{6,12}$";
        return isDigit && isLetter && str.matches(regex);
    }

    public void register(){

        try{

            int result = CScontrol.RegisterToServer(username,password);
            if(result==0){
                JOptionPane.showMessageDialog(this, "用户已存在！");
                userText.setText("");
                passText.setText("");
            }else{
                //new一个User传给MainPage
                userOfRegister = new User(username,password);
                JOptionPane.showMessageDialog(this, "注册成功！");
                setVisible(false);
                new MainPage(userOfRegister).setVisible(true);
                dispose();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Register();
    }

}
