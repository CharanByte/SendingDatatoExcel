import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetData {

    public static void main(String[] args) {

        String filePath="D:\\Book4.xlsx";
        String url="jdbc:mysql://localhost:3306/book";
        String userName="root";
        String password="charan";

        Connection  connection=null;
        try{
            connection= DriverManager.getConnection(url,userName,password);
            PreparedStatement statement=connection.prepareStatement("insert into book_tabe values(?,?,?)");
            FileInputStream  fileInputStream=new FileInputStream(new File(filePath));
            Workbook workbook=new XSSFWorkbook(fileInputStream);
            Sheet sheet=workbook.getSheetAt(0);
            for (Row row:sheet){
                if(row.getRowNum()==0){
                    continue;
                }
                int id= (int)row.getCell(0).getNumericCellValue();
                String name=row.getCell(1).getStringCellValue();
                int age=(int) row.getCell(2).getNumericCellValue();
                statement.setInt(1,id);
                statement.setString(2,name);
                statement.setInt(3,age);
                int value= statement.executeUpdate();
                System.out.println(value);
            }


        }
        catch (SQLException| IOException e){
            e.printStackTrace();
        }
    }
}
