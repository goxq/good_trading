import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainPage extends JFrame implements ActionListener {
    JTextField SearchTextField;
    JButton SearchButton;

    public MainPage(String username){
        super("У԰������Ʒ���� �û�: "+username+" �ѵ�¼");
        setSize(800,500);
        setLocationRelativeTo(null);
        setMenus();
        getTable();

        JPanel panel = new JPanel();
        //panel.setBackground(Color.blue);
        panel.setBounds(10,10,12,20);
        SearchTextField = new JTextField("����Ҫ�������Ʒ��������ѯ");
        SearchButton = new JButton("��ѯ");
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
        panel.add(SearchTextField);
        panel.add(SearchButton);
        add(panel,BorderLayout.NORTH);

        setVisible(true);
    }

    JMenuItem item1Jmenu1 = new JMenuItem("�����촰��");
    JMenuItem item1Jmenu2 = new JMenuItem("������Ʒ");
    JMenuItem part1_item2Jmenu2 = new JMenuItem("�������ͳ��");
    JMenuItem part2_item2Jmenu2 = new JMenuItem("�鿴�ѷ�������Ʒ");
    JMenuItem item1Jmenu3 = new JMenuItem("�鿴���ﳵ");
    JMenuItem item2Jmenu3 = new JMenuItem("�鿴�ѹ����б�");
    public void setMenus(){
        JMenuBar bar = new JMenuBar();
        JMenu jMenu1= new JMenu("ͨ��");
        JMenu jMenu2 = new JMenu("��������");
        JMenu item2Jmenu2 = new JMenu("�۳����");
        item2Jmenu2.add(part1_item2Jmenu2);
        item2Jmenu2.add(part2_item2Jmenu2);
        JMenu jMenu3 = new JMenu("�������");
        jMenu1.add(item1Jmenu1);
        jMenu2.add(item1Jmenu2);
        jMenu2.add(item2Jmenu2);
        jMenu3.add(item1Jmenu3);
        jMenu3.add(item2Jmenu3);
        //��Ӳ˵����˵���
        bar.add(jMenu1);
        bar.add(jMenu2);
        bar.add(jMenu3);
        setJMenuBar(bar);
        //�������˵���ӵ���¼�
        item1Jmenu1.addActionListener(this);
        item1Jmenu2.addActionListener(this);
        item1Jmenu3.addActionListener(this);
        item2Jmenu3.addActionListener(this);
        part1_item2Jmenu2.addActionListener(this);
        part2_item2Jmenu2.addActionListener(this);
    }

    JTable table;
    Object tableContent[][];
    Object name[]={"��Ʒ����","�۸�","�Ƿ�Ϊ����"};
    public void getTable(){
        //���ٵ�
        tableContent = new Object[50][3];
        for(int i = 0;i<50;i++){
           tableContent[i][0]="���ܲ�";
           tableContent[i][1]="3";
           tableContent[i][2]="��";
        }
        table = new JTable(tableContent,name);
        table.setRowHeight(30);
        add(new JScrollPane(table),BorderLayout.CENTER);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        if(e.getSource()==SearchButton){
            String searchStr = SearchTextField.getText();
            //TODO:����
        }
        //TODO:д�����˵��ĵ���¼�
    }
}
