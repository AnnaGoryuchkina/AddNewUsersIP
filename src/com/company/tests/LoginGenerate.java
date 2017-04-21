package com.company.tests;


public class LoginGenerate {

    public static String generateLogin(String name){
        String[] arr = name.split(" ");
        char[] nameChars = arr[1].toCharArray();
        char[] chars = arr[2].toCharArray();
        String rus_login = nameChars[0] + ""+ chars[0]+ "" + arr[0];
        String login = cyr2lat(rus_login.toUpperCase());
        return login;
    }

    public static String cyr2lat(char ch){
        switch (ch){
            case 'А': return "A";
            case 'Б': return "B";
            case 'В': return "V";
            case 'Г': return "G";
            case 'Д': return "D";
            case 'Е': return "E";
            case 'Ё': return "YO";
            case 'Ж': return "ZH";
            case 'З': return "Z";
            case 'И': return "I";
            case 'Й': return "Y";
            case 'К': return "K";
            case 'Л': return "L";
            case 'М': return "M";
            case 'Н': return "N";
            case 'О': return "O";
            case 'П': return "P";
            case 'Р': return "R";
            case 'С': return "S";
            case 'Т': return "T";
            case 'У': return "U";
            case 'Ф': return "F";
            case 'Х': return "KH";
            case 'Ц': return "TS";
            case 'Ч': return "CH";
            case 'Ш': return "SH";
            case 'Щ': return "SCH";
            case 'Ъ': return "";
            case 'Ы': return "I";
            case 'Ь': return "";
            case 'Э': return "YE";
            case 'Ю': return "YU";
            case 'Я': return "YA";
            default: return String.valueOf(ch);
        }
    }

    public static String cyr2lat(String s){
        StringBuilder sb = new StringBuilder(s.length()*2);
        for(char ch: s.toCharArray()){
            sb.append(cyr2lat(ch));
        }
        return sb.toString().toLowerCase();
    }

}
