import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Login extends JFrame implements ActionListener {
    JTextField userText;
    JPasswordField passText;
    JButton loginButton;
    JButton registerButton;
    String username,password;

    Dbstest dbstest;
    Connection ct;
    PreparedStatement ps;
    ResultSet rs;
    User user1;

    public Login() {
        super("校园闲置交易系统");
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



        loginButton.setBounds(25, 100, 80, 25);
        panel.add(loginButton);
        registerButton.setBounds(190, 100, 80, 25);
        panel.add(registerButton);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginButton){
            username = userText.getText();
            password= passText.getText();
            dbstest = new Dbstest();
            ct = dbstest.getConnection();
            try{
               ps = ct.prepareStatement("select userID,password from user");
               rs = ps.executeQuery();
               while (rs.next()){
                   String dbUserID = rs.getString(1);
                   String dbPassword = rs.getString(2);
                   if(dbUserID.equals(username)){
                       if(dbPassword.equals(password)){
                           user1 = new User(username,password);
                           JOptionPane.showMessageDialog(this,"登录成功！");
                           setVisible(false);
                           new MainPage(user1).setVisible(true);
                           dispose();
                       }
                       else
                           JOptionPane.showMessageDialog(this,"用户名或密码不正确！");
                   }
               }
            }
            catch (Exception exc){
                exc.printStackTrace();
            }
            finally {
                try{
                    if(rs!=null)
                        rs.close();
                    if(ps!=null)
                        ps.close();
                    if(ct!=null)
                        ct.close();
                }
                catch (Exception exc){
                    exc.printStackTrace();
                }
            }

        }
        else if (e.getSource()==registerButton){

            new Register();

        }
    }

    public static void main(String[] args) {
        new Login();
    }

}


