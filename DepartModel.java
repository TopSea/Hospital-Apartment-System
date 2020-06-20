package dbDesign;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static java.lang.System.out;

public class DepartModel extends AbstractTableModel {
    
    //rowData存放行数据，columnNames存放列名
    Vector rowData, columnNames;
    //定义操作数据库需要的东西
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet result = null;
    
    public void UpdateDepart(String sql, String datas) {
        boolean b = true;
        String info = null;
        String room = "select * from room where department = ?";
        try{
            con = DBDesign.GetConnection();
            pstmt = con.prepareStatement(room);
            pstmt.setString(1, datas);
            result = pstmt.executeQuery();
            while (result.next()) {
                String result1 = result.getString("room_number");
                String result2 = result.getString("bed_info") + ";";
                info = result1.concat(result2);
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, info);
                pstmt.setString(2, datas);
            }
            pstmt.executeUpdate();
            out.println(info);
        }catch (Exception e){

            e.printStackTrace();
        }finally {
            DBDesign.CloseDB(result, pstmt, con);
        }
    }
    public void SelectDepart(String sql) {

        columnNames = new Vector();
        //住院表编号 住院号 病人名称 病人身份信息 科室编号 房间号 床号 总花费 住院时间 出院时间 已出院？ 押金
        columnNames.add("科室编号");
        columnNames.add("科室名称");
        columnNames.add("病房信息");

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

                //加入到rowData
                rowData.add(rows);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBDesign.CloseDB(result, pstmt, con);
        }
    }

    //构造函数，初始化数据模型
    public DepartModel() {
        this.SelectDepart("select * from department");
    }
    public DepartModel(String sql) {
        this.SelectDepart(sql);
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
