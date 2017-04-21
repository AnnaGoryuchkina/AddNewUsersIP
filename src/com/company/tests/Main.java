package com.company.tests;

import java.io.File;
import java.util.List;

import static com.company.tests.ExcelReader.readExcel;

public class Main {

    public static void main(String[] args) throws Exception {

        GetDepartmentInfo.getDepartment(); //заполняем листы департаментов и их индексов

        File folder = new File("C:\\Users\\AGoryuchkina\\Desktop\\fileFolder1");
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            System.out.println(entry.getName());
            List<FullUserProfile> users = readExcel(folder + "\\" + entry.getName());//get data from excel and set it to FullUserProfile object
            for (FullUserProfile user : users) {
                UserProfileCheck checkUser = new UserProfileCheck(user);
                checkUser.check();
            }
            List<FullUserProfile> fullUsers = UserProfileCheck.fullUsers;
            ExcelWriter.writeFile(fullUsers, folder + "\\" + entry.getName() + " - result.xlsx");
            for (FullUserProfile fullUser : fullUsers) {
                ExcelWriter.writeSQLQueryForOPSResp(fullUser);
            }
            users.clear();
            fullUsers.clear();
            FullUserProfile.setID(0);
        }
    }
}
