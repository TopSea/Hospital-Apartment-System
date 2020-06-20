package dbDesign;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import static java.lang.System.out;

public class PatientModel extends AbstractTableModel {


    //rowData存放行数据，columnNames存放列名
    Vector rowData, columnNames;
    //定义操作数据库需要的东西
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet result = null;


    public boolean PatientOpera(String sql, String []datas) {
        boolean b = true;
        try{
            con = DBDesign.GetConnection();
            pstmt = con.prepareStatement(sql);
            for(int i = 0; i < datas.length; i++) {
                //插入
                pstmt.setString(i + 1, datas[i]);
            }
            if(pstmt.executeUpdate() != 1) {
                b = false;
            }
        }catch (Exception e){

            b = false;
            e.printStackTrace();
        }finally {
            DBDesign.CloseDB(result, pstmt, con);
        }
        return b;
    }

    public void SelectPatient(String sql) {

        columnNames = new Vector();
        //住院表编号 住院号 病人名称 病人身份信息 科室编号 房间号 床号 总花费 住院时间 出院时间 已出院？ 押金
        columnNames.add("住院表编号");
        columnNames.add("病人名称");
        columnNames.add("病人性别");
        columnNames.add("病人年龄");
        columnNames.add("所在病床");
        columnNames.add("电话号码");
        columnNames.add("住院日期");

        rowData = new Vector();

        try {
            con = DBDesign.GetConnection();
            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, login);
            result = pstmt.executeQuery();
            while (result.next()) {
                Vector rows = new Vector();
                rows.add(result.getString(1));
                rows.add(result.getString(2));
                rows.add(result.getString(3));
                rows.add(result.getString(4));
                rows.add(result.getString(8));
                rows.add(result.getString(5));
                rows.add(result.getString(9));

                //加入到rowData
                rowData.add(rows);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBDesign.CloseDB(result, pstmt, con);
        }
    }

    public  String UpdateSelect(String row, String column) {
        String index = null;
        try {
            con = DBDesign.GetConnection();
            pstmt = con.prepareStatement("select * from patient where login_number = ?");
            pstmt.setString(1, row);
            result = pstmt.executeQuery();
            while (result.next()) {
                index = result.getString(column);
            }
            return index;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBDesign.CloseDB(result, pstmt, con);
            return index;
        }
    }

    //构造函数，初始化数据模型
    public PatientModel() {
        this.SelectPatient("select * from patient");
    }
    public PatientModel(String sql) {
        this.SelectPatient(sql);
    }

    @Override
    public int getRowCount() {
        return this.rowData.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    //得到某行某列数据
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector)this.rowData.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int arg0) {
        return (String)this.columnNames.get(arg0);
    }
}
