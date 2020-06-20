package dbDesign;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Patient extends JFrame implements ActionListener {
    private JTable table = null;
    private JScrollPane jsp = null;
    private JButton select, insert, delete, updata;
    private JPanel north, south, all;
    private JTextField nameText;
    private JLabel tip;
    String name, sql, login;
    TableColumn column1;
    PatientModel myModel = new PatientModel();

    public JPanel Patient() {

        north = new JPanel();
        south = new JPanel();
        all = new JPanel();

        tip = new JLabel("请输入病人姓名进行查询");
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
        updata = new JButton("修改");
        updata.setActionCommand("修改");
        updata.addActionListener(this);
        south.add(insert);
        south.add(updata);
        south.add(delete);

        myModel = new PatientModel();
        table = new JTable(myModel);
        column1 = table.getColumnModel().getColumn(1);
        column1.setPreferredWidth(200);
        table.setAutoCreateRowSorter(true);
        jsp = new JScrollPane(table);
        all.add(north, BorderLayout.NORTH);
        all.add(jsp);
        all.add(south, BorderLayout.SOUTH);
        return all;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "查询" :
                name = nameText.getText().trim();
                sql = "select * from patient where patient_name = '"+name+"'";
                myModel = new PatientModel(sql);

                table.setModel(myModel);
                break;
            case "添加" :
                PatientUpdate insert = new PatientUpdate(this, e.getActionCommand(), true, myModel, null, "添加数据");
                myModel = new PatientModel();
                table.setModel(myModel);
                break;
            case "删除" :
                int row = this.table.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(this, "请选择一行！");
                    return ;
                }
                login = (String)myModel.getValueAt(row, 0);
                sql = "delete from patient where login_number = ?";
                String []datas = {login};
                myModel = new PatientModel();
                myModel.PatientOpera(sql, datas);
                myModel = new PatientModel();
                table.setModel(myModel);
                break;
            case "修改" :
                row = this.table.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(this, "请选择一行！");
                    return ;
                }
                login = (String)myModel.getValueAt(row, 0);
                PatientUpdate update = new PatientUpdate(this, e.getActionCommand(), true, myModel, login, "修改数据");
                myModel = new PatientModel();
                table.setModel(myModel);
                break;
            default:break;
        }
    }
}
