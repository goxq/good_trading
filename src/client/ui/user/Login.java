package client.ui.user;

import client.ui.MainPage;
import client.ui.component.GPasswordField;
import client.ui.component.GTextField;
import client.ui.util.FontConfig;
import common.entity.User;

import client.CScontrol;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;


public class Login extends JFrame implements ActionListener {
    GTextField userText;
    GPasswordField passText;
    JButton loginButton;
    JButton registerButton;


    User user1;

    public Login() throws Exception {
        super("校园杂货铺");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        add(panel);
        JLabel userLabel = new JLabel("用户名");
        userLabel.setFont(FontConfig.font3);
        userText = new GTextField(20,40);
        userText.setFont(new Font("微软雅黑", Font.PLAIN,13));
        JLabel passLabel = new JLabel("密码");
        passLabel.setFont(FontConfig.font3);
        passText = new GPasswordField(20,40);
        loginButton = new JButton("登录");
        loginButton.setFont(FontConfig.font3);
        loginButton.addActionListener(this);
        registerButton = new JButton("注册");
        registerButton.setFont(FontConfig.font3);
        registerButton.addActionListener(this);
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
        passText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Login.this.login(userText.getText(), String.valueOf(passText.getPassword()));
                }
            }
        });
        panel.add(passText);


        loginButton.setBounds(25, 100, 80, 25);
        panel.add(loginButton);
        registerButton.setBounds(190, 100, 80, 25);
        panel.add(registerButton);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            this.login(userText.getText(), String.valueOf(passText.getPassword()));

        } else if (e.getSource() == registerButton) {
            this.dispose();
            try {
                new Register();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    private void login(String username, String password){
        try {
            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(this, "用户名或密码不能为空！");
            } else {
                boolean isSuccess = CScontrol.loginToServer(username, password);
                if (isSuccess) {
                    user1 = new User(username, password);
                    JOptionPane.showMessageDialog(this, "登录成功！");
                    setVisible(false);
                    new MainPage(user1).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码不正确！");
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Login();
    }
}


