package com.company.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private static String fileDirection = "C:\\some_name.xlsx";
    private static String firstCell = "start cell";

    public static List<UserProfile> readExcel() {
        List<UserProfile> users = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(fileDirection));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = datatypeSheet.iterator();

            /*
                Проверяем все ячейки на наличие нужной нам стартовой строки.
            */
            int rowNum=0;
            int startRow=1000;
            int startColumn =0;
            List<String> keys = new ArrayList<>();
            boolean isNeeded = false; //атрибут помогает отслеживать заголовки столбцов

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                int cellNum = 0;

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getStringCellValue().equals(firstCell)) {
                        startRow = rowNum;
                        startColumn = cellNum;
                        isNeeded = true;
                    }
                    if (rowNum > startRow + 1) {
                        isNeeded = false;
                    }
                    if (isNeeded == true && !currentCell.getStringCellValue().isEmpty() && (rowNum == startRow || rowNum == startRow + 1)){
                        keys.add(currentCell.getStringCellValue()); //вытаскиваем ключи из двух строк с наименованиями столбцов (в нашем случае это две строки)
                    }
                    cellNum++;
                }
                rowNum++;
            }

            //TO-DO poi не видит пустые строки / ячейки - не видит их, проходит мимо и не считает.
            //Разобраться, как из увидеть и посчитать, чтобы можно было находить начальную координату таблицы,
            //и от нее начинать "читать" значения таблицы

            users = getInput(startRow, startColumn);
            //System.out.println(keys);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }



    private static List<UserProfile> getInput(int startRow, int startColumn){
        List<UserProfile> users = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(fileDirection));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = datatypeSheet.iterator();
            System.out.println(startRow + ":" + startColumn);

        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if (currentRow.getRowNum() - 1 > startRow ) {
                //System.out.println(currentRow.getRowNum());
                UserProfile user = new UserProfile();

                Cell nameCell = currentRow.getCell(0 + startColumn); //add startColumn if table starts not from the left border
                user.setName(nameCell.getStringCellValue());

                Cell emailCell = currentRow.getCell(1+ startColumn);
                user.setEmail(emailCell.getStringCellValue());

                Cell ufpsCell = currentRow.getCell(2+ startColumn);
                user.setUfps(ufpsCell.getStringCellValue());

                Cell postamtCell = currentRow.getCell(3+ startColumn);
                if (postamtCell == null){
                    user.setPostamt("");
                } else user.setPostamt(postamtCell.getStringCellValue());

                Cell ufpsOperatorCell = currentRow.getCell(4+ startColumn);
                user.setUfps_operator(formatStringToBoolean(ufpsOperatorCell));

                Cell ufpsControlerCell = currentRow.getCell(5+ startColumn);
                user.setUfps_controler(formatStringToBoolean(ufpsControlerCell));

                Cell ufpsAnalystCell = currentRow.getCell(6+ startColumn);
                user.setUfps_analyst(formatStringToBoolean(ufpsAnalystCell));

                Cell postamtOperatorCell = currentRow.getCell(7+ startColumn);
                user.setPostamt_operator(formatStringToBoolean(postamtOperatorCell));

                Cell postamtControlerCell = currentRow.getCell(8+ startColumn);
                user.setPostamt_controler(formatStringToBoolean(postamtControlerCell));

                Cell postamtAnalystCell = currentRow.getCell(9+ startColumn);
                user.setPostamt_analyst(formatStringToBoolean(postamtAnalystCell));

                Cell opsRespCell = currentRow.getCell(10+ startColumn);
                user.setOps_resp(formatStringToBoolean(opsRespCell));

                Cell requestRespCell = currentRow.getCell(11+ startColumn);
                user.setRequset_resp(formatStringToBoolean(requestRespCell));

                Cell indexOPSCell = currentRow.getCell(12+startColumn);
                if(indexOPSCell == null){
                    user.setIndexs_OPS("");
                } else user.setIndexs_OPS(indexOPSCell.getStringCellValue());

                users.add(user);
            }
        }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find or load file with file direction: " + fileDirection);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Some exception appears during parsing file.");
            e.printStackTrace();
        }
        return users;
    }

    public static boolean formatStringToBoolean(Cell cell){
        if (cell == null || cell.toString() == ""){
            return false;
        } else return true;
    }
}
