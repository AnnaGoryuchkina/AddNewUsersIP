package com.company.tests;


import java.util.regex.Pattern;

public class Constants {

    public static final String DB_URL = "db_url";
    public static final String DB_USER = "db_user";
    public static final String DB_PWD = "db_password";

    public static final String EMAIL_P="^\\s*[-A-z0-9!#$%&'*+-/=?^_`{|}~]+(\\.[-A-z0-9!#$%&'*+-/=?^_`{|}~]+)*@" +
            "([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*[a-z]{2,6}\\s*$";

    public static final String USER_NAME = "^(\\s*[А-я]+\\s+){2}\\s*[А-я]+\\s*$"; //если хотим чтобы обязательно были имя, фамилия и отчество
    public static final String UFPS_P = "^\\s*([А-я]+\\s*)*\\d{6}\\s*$";
    public static final String POSTAMT_P = UFPS_P;



}
