package dbDesign;

import javax.swing.*;
import java.sql.*;

public class DBDesign extends JFrame {
    private  JTabbedPane ui;
    private JPanel patient, room, department;
    private  Patient pa = new Patient();
    private  Room ro = new Room();
    private  Department de = new Department();
    private static final String url = "jdbc:mysql://localhost:3306/DBDesign?&useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "root";

    public static void main(String[] args) {
        DBDesign dbDesign = new DBDesign();
    }

    public DBDesign() {
        ui = new JTabbedPane();

        patient = new JPanel();
        room = new JPanel();
        patient = pa.Patient();
        room = ro.Room();
        department = de.Department();

        ui.addTab("病人", patient);
        ui.addTab("病房", room);
        ui.addTab("科室", department);
//        ui.addTab("插入", insert);


        this.add(ui);
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("住院管理子系统");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static Connection GetConnection(){
        try {
            //加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //通过驱动管理类获取数据库链接
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void CloseDB(ResultSet rs, PreparedStatement pstmt, Connection con) {
        //释放资源
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}

/*
insert into patient(login_number, patient_name, ID_card, gender,
age, phone, accompany_phone, department, room_number, bed_number,login_date,
left_date, already_left, cost, balance) value('L0002', '守法市民李四', '430621199901210124',
'男', '36','156850','65451#4','D01','D0101','D010102','2019-7-06','2019-7-21',true,1523,2000);
 */
