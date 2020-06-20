package dbDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.out;

public class Department extends JFrame implements ActionListener {
    private JTable jt = null;
    private JScrollPane jsp = null;
    private JButton select, insert, delete, updata1, updata2, updata3;
    private JPanel north, south, all;
    private JTextField nameText;
    private JLabel tip;
    private JTextArea infos;
    String name, sql, num;
    DepartModel myModel = new DepartModel();

    public  JPanel Department() {

        north = new JPanel();
        south = new JPanel();
        all = new JPanel();

        infos = new JTextArea(25, 45);

        insert = new JButton("添加");
        insert.setActionCommand("添加");
        insert.addActionListener(this);
        delete = new JButton("删除");
        delete.setActionCommand("删除");
        delete.addActionListener(this);

        updata1 = new JButton("科室一");
        updata1.setActionCommand("科室一");
        updata1.addActionListener(this);
        updata2 = new JButton("科室二");
        updata2.setActionCommand("科室二");
        updata2.addActionListener(this);
        updata3 = new JButton("科室三");
        updata3.setActionCommand("科室三");
        updata3.addActionListener(this);

        north.add(updata1);
        north.add(updata2);
        north.add(updata3);

        south.add(insert);
        south.add(delete);

        myModel = new DepartModel();
        jsp = new JScrollPane(infos);
        all.add(north, BorderLayout.NORTH);
        all.add(jsp);
        all.add(south, BorderLayout.SOUTH);
        return all;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "查询":
                name = nameText.getText().trim();
                sql = "select * from department where number = '" + name + "'";
                myModel = new DepartModel(sql);
                jt.setModel(myModel);
                break;
            case "添加":
                JOptionPane.showMessageDialog(this, "无此权限！");
                break;
            case "删除":
                JOptionPane.showMessageDialog(this, "无此权限！");
                break;
            case "科室一":
                num = "D01";
                myModel = new DepartModel();
                infos.setText(myModel.UpdateDepart("update department set room_info = ? where number = ?", num));
                infos.setFont(new Font("宋体", Font.PLAIN, 17));
                break;
            case "科室二":
                num = "D02";
                myModel = new DepartModel();
                infos.setText(myModel.UpdateDepart("update department set room_info = ? where number = ?", num));
                infos.setFont(new Font("宋体", Font.PLAIN, 17));
                break;
            case "科室三":
                num = "D03";
                myModel = new DepartModel();
                infos.setText(myModel.UpdateDepart("update department set room_info = ? where number = ?", num));
                infos.setFont(new Font("宋体", Font.PLAIN, 17));
                break;
            default:
                break;
        }
    }
}
