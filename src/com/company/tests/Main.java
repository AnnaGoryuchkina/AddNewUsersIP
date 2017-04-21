package com.company.tests;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.company.tests.ExcelReader.readExcel;

public class Main {

    public static void main(String[] args) throws Exception {
        GetDepartmentInfo.getDepartment(); //заполняем листы департаментов и их индексов
        List<UserProfile> users = readExcel();//get data from excel and set it to UserProfile object
        for (UserProfile user : users) {
            UserProfileCheck checkUser = new UserProfileCheck(user);
            checkUser.check();
        }
        List<FullUserProfile> fullUsers = UserProfileCheck.fullUsers;
        ExcelWriter.writeFile(fullUsers);
    }
}
