package dbDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.out;

public class Department extends JFrame implements ActionListener {
    private JTable jt = null;
    private JScrollPane jsp = null;
    private JButton select, insert, delete, updata;
    private JPanel north, south, all;
    private JTextField nameText;
    private JLabel tip;
    String name, sql, room;
    int row;
    DepartModel myModel = new DepartModel();
    
    public  JPanel Department() {

        north = new JPanel();
        south = new JPanel();
        all = new JPanel();

        tip = new JLabel("请输入科室编号进行查询");
        nameText = new JTextField(10);
        select = new JButton("查询");
        select.setActionCommand("查询");
        select.addActionListener(this);
        north.add(tip);
        north.add(nameText);
        north.add(select);

        insert = new JButton("添加");
        insert.setActionCommand("添加");
        insert.addActionListener(this);
        delete = new JButton("删除");
        delete.setActionCommand("删除");
        delete.addActionListener(this);
        updata = new JButton("更新");
        updata.setActionCommand("更新");
        updata.addActionListener(this);
        south.add(insert);
        south.add(updata);
        south.add(delete);

        myModel = new DepartModel();
        jt = new JTable(myModel);
        jsp = new JScrollPane(jt);
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
            case "更新":
                row = this.jt.getSelectedRow();
                out.println(row);
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "请选择一行！");
                    return;
                }
                room = (String) myModel.getValueAt(row, 0);
                myModel = new DepartModel();
                myModel.UpdateDepart("update department set room_info = ? where number = ?", room);
                DepartModel my = new DepartModel();
                jt.setModel(my);
                break;
            default:
                break;
        }
    }
}
