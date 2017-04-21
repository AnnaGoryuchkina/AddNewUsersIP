package com.company.tests;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ExcelWriter {

    private static final String FILE_NAME = "C:\\some_name.xlsx";

    public static void writeFile(List<FullUserProfile> users) throws NoSuchFieldException, IllegalAccessException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Пользователи");

        System.out.println("Creating excel");
        int rowNum = 0;
        for (FullUserProfile user : users) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;

            List<Object> fields = getFieldValues(user);

            for (Object field : fields) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                cell.setCellValue((Integer) field);
            }
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }


    public static List<Object> getFieldValues(FullUserProfile user) throws NoSuchFieldException, IllegalAccessException {
        Class aClass = FullUserProfile.class;
        List<Object> fieldValues = new ArrayList<>();
        /*
        Get FullUserProfile fields
         */
        Field name = aClass.getDeclaredField("name");
        Field email = aClass.getDeclaredField("email");
        Field ufps = aClass.getDeclaredField("ufps");
        Field postamt = aClass.getDeclaredField("postamt");
        Field department = aClass.getDeclaredField("department");
        Field ufps_operator = aClass.getDeclaredField("ufps_operator");
        Field ufps_controler = aClass.getDeclaredField("ufps_controler");
        Field ufps_analyst = aClass.getDeclaredField("ufps_analyst");
        Field postamt_operator = aClass.getDeclaredField("postamt_operator");
        Field postamt_controler = aClass.getDeclaredField("postamt_controler");
        Field postamt_analyst = aClass.getDeclaredField("postamt_analyst");
        Field ops_resp = aClass.getDeclaredField("ops_resp");
        Field requset_resp = aClass.getDeclaredField("requset_resp");
        //Field indexs_OPS = aClass.getDeclaredField("indexs_OPS");
        Field dropped = aClass.getDeclaredField("dropped");
        Field ID = aClass.getDeclaredField("ID");
        Field guid = aClass.getDeclaredField("guid");
        Field pwd = aClass.getDeclaredField("pwd");
        Field pwdHash = aClass.getDeclaredField("pwdHash");
        Field login = aClass.getDeclaredField("login");
        Field added = aClass.getDeclaredField("added");
        Field operator = aClass.getDeclaredField("operator");

        List<Field> fields = new ArrayList<>();
        fields.add(ID);
        fields.add(dropped);
        //fields.add(ufps); // не забыть убрать УФПС если отмечен Почтамт
        //fields.add(postamt);
        fields.add(department);
        fields.add(name);
        fields.add(guid);
        fields.add(login);
        fields.add(pwdHash);
        //fields.add(ufps_operator);
        //fields.add(postamt_operator);
        fields.add(operator);
        fields.add(postamt_controler);
        fields.add(ufps_controler);
        fields.add(postamt_analyst);
        fields.add(ufps_analyst);
        fields.add(ops_resp);
        fields.add(requset_resp);
        fields.add(email);
        fields.add(added);
        fields.add(pwd);
        //fields.add(indexs_OPS);


        /*
        Make fields available even if they are private
         */
        for (Field field : fields){
            field.setAccessible(true);
            Object fieldValue = field.get(user);
            //System.out.println(fieldValue);
            fieldValues.add(fieldValue);
        }
        return fieldValues;
    }
}
