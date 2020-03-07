import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Login extends JFrame implements ActionListener {
    JTextField userText;
    JPasswordField passText;
    JButton loginButton;
    JButton registerButton;
    String username,password;

    public Login() {
        super("校园闲置物品交易");
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
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);
        registerButton = new JButton("注册");
        registerButton.addActionListener(this);
        panel.setLayout(null);
        //userLabel创建
        userLabel.setBounds(30, 30, 80, 25);
        panel.add(userLabel);
        //passLabel创建
        passLabel.setBounds(30, 60, 80, 25);
        panel.add(passLabel);
        userText.setBounds(105, 30, 165, 25);
        panel.add(userText);
        passText.setBounds(105, 60, 165, 25);
        panel.add(passText);
        //创建登录窗口
        loginButton.setBounds(25, 100, 80, 25);
        panel.add(loginButton);
        registerButton.setBounds(190, 100, 80, 25);
        panel.add(registerButton);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginButton){
            username = userText.getText();
            password= passText.getPassword().toString();
            //TODO:判断用户名和密码
            JOptionPane.showMessageDialog(null,"登录成功！","提示",JOptionPane.NO_OPTION);
            setVisible(false);
            new MainPage(username).setVisible(true);
            dispose();
        }
        else if (e.getSource()==registerButton){
                //
        }
    }

}


