package com.company.tests;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDepartmentInfo {

    /*
    Получаем все возможные группы пользователей (и Почтамты и УФПСы)
     */
    static List<String> departments = new ArrayList<>();
    static List<String> indexes = new ArrayList<>();

    public static void getDepartment() throws Exception {
        Properties properties = PropertyHelper.loadPropertiesFromPropertyHome(PropertyHelper.PROPERTY_FILE);
        String url = properties.getProperty(Constants.DB_URL);
        String pwd = properties.getProperty(Constants.DB_PWD);
        String user = properties.getProperty(Constants.DB_USER);

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String queryUFPS = "select distinct name from um_group";

        try {
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("connection is set");
            st = con.createStatement();
            rs = st.executeQuery(queryUFPS);
            while (rs.next()) {
                String department = rs.getString("NAME");
                //System.out.println(department);
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (String dep: departments){
            Pattern p = Pattern.compile("\\.*\\d{6}");
            Matcher m = p.matcher(dep);
            if (m.find()){
                String index = m.group();
                indexes.add(index);
                //System.out.println(index);
            }
        }

    }

    public static List<String> getDepartments(){
        return departments;
    }


    public static List<String> getIndexes(){ return indexes; }

    public static String returnGroupName(String index) {
        for (String dep : departments){
            if (getIndexFromGroupName(dep).equals(getIndexFromGroupName(index)))
                return dep;
            return "No group with given index: " + index;
        }
        return null;
    }

    public static String getIndexFromGroupName(String name){
        String index = null;
        Pattern p = Pattern.compile("\\.*\\d{6}");
        Matcher m = p.matcher(name);
        if (m.find()){
            index = m.group();
        }
        return index;
    }
}
