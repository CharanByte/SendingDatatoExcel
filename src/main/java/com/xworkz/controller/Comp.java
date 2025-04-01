package com.xworkz.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.sun.deploy.net.HttpResponse;
import com.xworkz.service.SendMailService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import static org.apache.poi.ss.util.SheetUtil.getCell;

@Component
@RequestMapping("/")
public class Comp {

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/sendMail")
    public String onSend(String email){
        System.out.println(email);
        sendMailService.sendmail(email);
        System.out.println("saved");
        return "index.jsp";
    }

    @PostMapping("/excel")
    public String onclick(@RequestParam("file")MultipartFile file){

        try {
            System.out.println(file);
            InputStream inputStream= file.getInputStream();
            Workbook workbook=new XSSFWorkbook(inputStream);
            Sheet sheet=workbook.getSheetAt(0);

            for (Row row:sheet){
                if(row.getRowNum()==0){
                    continue;}
                int id= (int) row.getCell(0).getNumericCellValue();
                String name=row.getCell(1).getStringCellValue();

                System.out.println(id +" "+name);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/downloadPdf")
    public void onPdf(HttpServletResponse response){

        System.out.println(response);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=employees.pdf");

//        String url="jdbc:mysql://localhost:3306/book";
//        String userName="root";
//        String password="charan";
//        Connection connection=null;
        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select id,name,age from book_tabel");

            PdfWriter pdfWriter=new PdfWriter(response.getOutputStream());
            PdfDocument pdfDocument=new PdfDocument(pdfWriter);
            Document document=new Document(pdfDocument);

            document.add(new Paragraph("good morning"));



            document.close();

    }
//        catch (SQLException e){
//        e.printStackTrace();
//    }
    catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
