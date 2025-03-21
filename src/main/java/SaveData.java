import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class SaveData {

    public static void main(String[] args) {

        String file="D:\\Book2.xlsx";
        String url="jdbc:mysql://localhost:3306/book";
        String userName="root";
        String password="charan";

        Connection connection=null;
        try {
            connection= DriverManager.getConnection(url,userName,password);
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select id,name,age from book_tabel");

            FileOutputStream fileOutputStream =new FileOutputStream(file);
            Workbook workbook=new XSSFWorkbook();

            Sheet sheet=workbook.createSheet("data1");
            Row headerRow=sheet.createRow(0);
           ResultSetMetaData resultSetMetaData= resultSet.getMetaData();
           int columnCount=resultSetMetaData.getColumnCount();
            System.out.println(columnCount);
           for (int i=1;i<=columnCount;i++){
               Cell cell=headerRow.createCell(i-1);
               cell.setCellValue(resultSetMetaData.getColumnName(i));
           }
           int row=1;
           while (resultSet.next()){
               Row row1=sheet.createRow(row++);
               for (int i=1;i<=columnCount;i++){
                   row1.createCell(i-1).setCellValue(resultSet.getString(i));
                   System.out.println("hello");
               }
           }
           workbook.write(fileOutputStream);
        }
        catch (SQLException|IOException e){
            e.printStackTrace();
        }
    }


}
