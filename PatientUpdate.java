package dbDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.out;
import static java.text.DateFormat.MEDIUM;

public class PatientUpdate extends JDialog implements ActionListener {

    private JPanel all, center, north, south, gen;
    private JTextField login_numberText, phoneText, costText;
    private final JFormattedTextField ageText, loginText, nameText;
    private JLabel login_numberLabel, nameLabel, genderLabel, ageLabel, phoneLabel,
            departmentLabel, roomLabel, bedLabel, loginLabel,
            costLabel,sureLabel;
    private JButton insertButton;
    private JSpinner department, room, bed;
    private ButtonGroup genders;
    private JRadioButton male, female;
    private String gender;
    private PatientModel my = new PatientModel();
    private String[] departs = {"D01", "D02", "D03"};
    private String[] rooms = {"01", "02", "03"};
    private String[] beds = {"01", "02", "03", "04", "05"};
    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    DateFormat tableDate = new SimpleDateFormat("MM-dd-KKmmss");
    public static int i = 0;

    public static void main(String[] args) {
        i++;
        String row  = "354";
        DateFormat tableDate = new SimpleDateFormat("MM-dd-KKmmss");
        String str = String.format(tableDate.format(new Date()));
        out.println(str);
    }

    public PatientUpdate(Frame owner, String title, boolean modal, PatientModel my, String row, String button) {
        super(owner, title, modal); //调用父类构造方法，达到模式对话框
        all = new JPanel();
        center = new JPanel();
        north = new JPanel();
        gen = new JPanel();
        south = new JPanel();

        male = new JRadioButton("男");
        female = new JRadioButton("女");

        department = new JSpinner(new SpinnerListModel(departs));
        room = new JSpinner(new SpinnerListModel(rooms));
        bed = new JSpinner(new SpinnerListModel(beds));

        login_numberText = new JTextField(16);
        login_numberText.setText(tableDate.format(new Date())/* + "-" + String.format("%03d", i)*/);
        login_numberText.setFont(new Font("宋体", Font.PLAIN, 17));
        login_numberText.setEditable(false);
        nameText = new JFormattedTextField();
        ageText = new JFormattedTextField(NumberFormat.getIntegerInstance());
        phoneText = new JTextField("15697820", 10);
        costText = new JTextField("0", 10);
        loginText = new JFormattedTextField(dateFormat);
        loginText.setValue(new Date());
        loginText.setEditable(false);

        if(button.equals("修改数据")) {
            if((my.UpdateSelect(row, "gender")).equals("男")) {
                male = new JRadioButton("男", true);
            }
            if((my.UpdateSelect(row, "gender")).equals("女")) {
                female = new JRadioButton("女", true);
            }
            login_numberText.setText(my.UpdateSelect(row, "login_number"));
            login_numberText.setEditable(false);
            nameText.setText(my.UpdateSelect(row, "patient_name"));
            ageText.setText(my.UpdateSelect(row, "age"));
            department.setValue(my.UpdateSelect(row, "department"));
            room.setValue(my.UpdateSelect(row, "room_number").substring(3, 5));
            bed.setValue(my.UpdateSelect(row, "bed_number").substring(5, 7));
            loginText.setText(my.UpdateSelect(row, "login_date"));
            costText.setText(my.UpdateSelect(row, "cost"));
        }

        login_numberLabel = new JLabel("住院表编号：");
        login_numberLabel.setFont(new Font("宋体", Font.PLAIN, 17));

        nameLabel = CreateLabel("病人姓名：");

        genderLabel = CreateLabel("病人性别：");

        ageLabel = CreateLabel("病人年龄：");

        phoneLabel = CreateLabel("病人联系方式：");

        departmentLabel = CreateLabel("科室编号：");

        roomLabel = CreateLabel("病房编号：");

        bedLabel = CreateLabel("病床编号：");

        loginLabel = CreateLabel("住院日期：");

        costLabel = CreateLabel("总消费：");

        sureLabel = CreateLabel("请确认信息后插入");

        insertButton = new JButton(button);
        insertButton.addActionListener(this);
        insertButton.setActionCommand(button);

        genders = new ButtonGroup();
        genders.add(male);
        genders.add(female);
        gen.add(male);
        gen.add(female);
        
        north.add(login_numberLabel);
        north.add(login_numberText);
//        jl4.setFont(new Font("宋体", Font.PLAIN, 16));
//        jl4.setForeground(Color.BLUE);

        center.add(nameLabel);
        center.add(nameText);
        center.add(genderLabel);
        center.add(gen);

        center.add(ageLabel);
        center.add(ageText);
        center.add(phoneLabel);
        center.add(phoneText);

        center.add(departmentLabel);
        center.add(department);
        center.add(roomLabel);
        center.add(room);

        center.add(bedLabel);
        center.add(bed);
        center.add(loginLabel);
        center.add(loginText);

        center.add(costLabel);
        center.add(costText);

        center.add(sureLabel);
        center.add(insertButton);

        south.add(sureLabel);
        south.add(insertButton);

        center.setLayout(new GridLayout(10, 2, 5, 5));
        all.add(north, BorderLayout.SOUTH);
        all.add(center, BorderLayout.CENTER);
        all.add(south, BorderLayout.SOUTH);
        this.add(all);
        this.setSize(350, 520);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        pack();

    }

    //设置JLabel的字体和大小
    public JLabel CreateLabel(String label) {
        JLabel creat = new JLabel(label);
        creat.setFont(new Font("宋体", Font.PLAIN, 15));
        return creat;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("修改数据")) {
            if(male.isSelected()){
                gender = "男";
            }
            if(female.isSelected()){
                gender = "女";
            }
            String []datas = {nameText.getText(),
                    gender,ageText.getText(),phoneText.getText(),
                    (String) department.getValue(),(String) department.getValue() + room.getValue(),
                    (String) department.getValue() + room.getValue() + bed.getValue(),
                    loginText.getText(),costText.getText(),
                    login_numberText.getText()};
            String sql = "update patient set patient_name = ?, gender = ? ,age = ?, phone = ? , " +
                    "department = ? , room_number = ? , bed_number = ? , login_date = ? , " +
                    "cost = ? where login_number = ?";
            my = new PatientModel();
            if(my.PatientOpera(sql, datas)) {
                this.dispose();
                JOptionPane.showMessageDialog(this, "修改成功！");
                return;
            }
        }else if(e.getActionCommand().equals("添加数据")) {
            if(male.isSelected()){
                gender = "男";
            }
            if(female.isSelected()){
                gender = "女";
            }
            String []datas = {login_numberText.getText(),nameText.getText(),
                    gender,ageText.getText(),phoneText.getText(),
                    (String) department.getValue(),(String) department.getValue() + room.getValue(),
                    (String) department.getValue() + room.getValue() + bed.getValue(),
                    loginText.getText(),costText.getText()};
            String sql = "insert into patient values(?,?,?,?,?,?,?,?,?,?)";
            i++;
            my = new PatientModel();
            if(my.PatientOpera(sql, datas)) {
                this.dispose();
                JOptionPane.showMessageDialog(this, "添加成功！");
                return;
            }else {
                JOptionPane.showMessageDialog(this, "输入有错误！");
                return;
            }
        }
    }
}
