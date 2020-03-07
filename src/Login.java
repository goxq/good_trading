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
        super("У԰������Ʒ����");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        add(panel);
        JLabel userLabel = new JLabel("�û���");
        userText = new JTextField();
        JLabel passLabel = new JLabel("����");
        passText = new JPasswordField(20);
        loginButton = new JButton("��¼");
        loginButton.addActionListener(this);
        registerButton = new JButton("ע��");
        registerButton.addActionListener(this);
        panel.setLayout(null);
        //userLabel����
        userLabel.setBounds(30, 30, 80, 25);
        panel.add(userLabel);
        //passLabel����
        passLabel.setBounds(30, 60, 80, 25);
        panel.add(passLabel);
        userText.setBounds(105, 30, 165, 25);
        panel.add(userText);
        passText.setBounds(105, 60, 165, 25);
        panel.add(passText);
        //������¼����
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
            //TODO:�ж��û���������
            JOptionPane.showMessageDialog(null,"��¼�ɹ���","��ʾ",JOptionPane.NO_OPTION);
            setVisible(false);
            new MainPage(username).setVisible(true);
            dispose();
        }
        else if (e.getSource()==registerButton){
                //
        }
    }

}


