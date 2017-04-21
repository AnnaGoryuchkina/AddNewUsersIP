package com.company.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfileCheck {
    //UserProfile user = new UserProfile();
    FullUserProfile fullUser = new FullUserProfile();
    static List<FullUserProfile> fullUsers = new ArrayList<>(); // обработанный пользователь

    List<String> departments = GetDepartmentInfo.departments;
    List<String> indexes = GetDepartmentInfo.indexes;

    UserProfileCheck(FullUserProfile user){
        this.fullUser = user;
    }

    public void check() throws Exception {

        /*
        Check name
         */
        Pattern p_name = Pattern.compile(Constants.USER_NAME);
        //Pattern p_name = Pattern.compile("^([А-я]+\\s+){2}[А-я]+\\s*$");
        Matcher m_name = p_name.matcher(fullUser.getName());
        if (m_name.matches()){}
        else {
            System.out.println("Problem with name: " + fullUser.getName());
        }

        /*
        Check email
         */
        Pattern p_mail = Pattern.compile(Constants.EMAIL_P);
        Matcher m_mail = p_mail.matcher(fullUser.getEmail());
        if (m_mail.matches()){}
        else {
            System.out.println("Problem with email: " + fullUser.getEmail());
        }

        /*
        Check ufps and postamt.
         */
        boolean ufpsIsSet=false;
        boolean postamtIsSet = false;

        Pattern p_ufps = Pattern.compile(Constants.UFPS_P);
        Matcher m_ufps = p_ufps.matcher(fullUser.getUfps());
        if (m_ufps.matches()) {
            /*
            Проверяем напрямую имя департамента полностью
             */
            for (String one : departments) {
                if (one.equals(fullUser.getUfps())) {
                    fullUser.setUfps(fullUser.getUfps());
                    ufpsIsSet = true;
                    break;
                }
            }

            /*
            Проверяем только по индексам.
             */
            if (ufpsIsSet == false) {
                for (String index : indexes) {
                    if (GetDepartmentInfo.getIndexFromGroupName(fullUser.getUfps()).equals(index)) {
                        //System.out.println("Index finding: " + user.getName() + " index: " + index);
                        fullUser.setUfps(GetDepartmentInfo.returnGroupName(index));
                        ufpsIsSet = true;
                        break;
                    }
                }
            }
            if (ufpsIsSet == false) {
                System.out.println("No UFPS department matches with the system for user: " + fullUser.getName());
            }
        }


                if (fullUser.getPostamt() != null) {
                    Pattern p_postamt = Pattern.compile(Constants.POSTAMT_P);
                    Matcher m_postamt = p_postamt.matcher(fullUser.getPostamt());
                    if (m_postamt.matches()) {
                        /*
                        Check department names
                         */
                        for (String one : departments) {
                            if (one.equals(fullUser.getPostamt())) {
                                fullUser.setPostamt(fullUser.getPostamt());
                                postamtIsSet = true;
                                break;
                            }
                        }

                        /*
                        Check indexes if no name matches
                         */

                        if (postamtIsSet == false) {
                            for (String index : indexes) {
                                if (GetDepartmentInfo.getIndexFromGroupName(fullUser.getPostamt()).equals(index)) {
                                    fullUser.setPostamt(GetDepartmentInfo.returnGroupName(index));
                                    postamtIsSet = true;
                                    break;
                                }
                            }
                        }
                        if (postamtIsSet == false) {
                            System.out.println("No Postamt department matches with the system for user: " + fullUser.getName());
                        }

                    }
                }
                /*
                Cетаем поле Department который в результате пойдет в группу.
                 */
                if (fullUser.getPostamt() == null || fullUser.getPostamt().equals("")){
                    fullUser.setDepartment(fullUser.getUfps());
                } else {
                    fullUser.setDepartment(fullUser.getPostamt());
                }

                /*
                Check roles
                 */

                /*List<int> roles = new ArrayList<>(); //есть смысл подумать над map
                roles.add(fullUser.getUfps_operator());
                roles.add(fullUser.getUfps_controler());
                roles.add(fullUser.getUfps_analyst());
                roles.add(fullUser.getPostamt_operator());
                roles.add(fullUser.getPostamt_controler());
                roles.add(fullUser.getPostamt_analyst());
                roles.add(fullUser.getOps_resp());
                roles.add(fullUser.getOps_resp());*/


                /*
                Проверка условий для ролей, индексов ОПС.
                 */
                boolean con1 = false;
                boolean con2 = false;
                boolean con3 = false;

                //роли УФПС и Почтамта не могут пересекаться
                if ((fullUser.getUfps_operator() == 1 || fullUser.getUfps_controler() ==1 || fullUser.getUfps_analyst() ==1)
                        && (fullUser.getPostamt_operator()==1 || fullUser.getPostamt_controler()==1 || fullUser.getPostamt_analyst()==1)) {
                    System.out.println("Roles for ufps and for postamt intersect - wrong!!!  for user: " + fullUser.getName());
                } else {
                    con1 = true;
                }
                //если указаны роли Почтамта, то должен быть указан Почтамт
                if (fullUser.getPostamt() == null &&
                        (fullUser.getPostamt_operator()==1 || fullUser.getPostamt_controler()==1 || fullUser.getPostamt_analyst()==1)) {
                    System.out.println("User with postamt role and without postamt - wrong!!!  for user: " + fullUser.getName());
                } else {
                    con2 = true;
                }

                if (fullUser.getOps_resp() == 1 && fullUser.getIndexs_OPS().equals("")) {
                    System.out.println("User has OPS responsible role, " +
                            "but no OPS indexes(or indexes without OPS resp role)  for user: " + fullUser.getName());
                } else {
                    con3 = true;
                }

                /*if (con1 == true && con2 == true && con3 == true) {
                    for (int i = 0; i < roles.size(); i++) {
                        if (roles.get(i) == true) {
                            switch (i) {
                                case 0:
                                    fullUser.setUfps_operator(1);
                                    break;
                                case 1:
                                    fullUser.setUfps_controler(1);
                                    break;
                                case 2:
                                    fullUser.setUfps_analyst(1);
                                    break;
                                case 3:
                                    fullUser.setPostamt_operator(1);
                                    break;
                                case 4:
                                    fullUser.setPostamt_controler(1);
                                    break;
                                case 5:
                                    fullUser.setPostamt_analyst(1);
                                    break;
                                case 6:
                                    fullUser.setOps_resp(1);
                                    break;
                                case 7:
                                    fullUser.setRequset_resp(1);
                                    break;
                            }
                        }
                    }
                }*/

                /*
        В экселе у нас нет разделения на операторов УФПС или Почтамтов, поэтому объединяем их в общий атрибут - operator
         */
                if (fullUser.getUfps_operator() == 1 || fullUser.getPostamt_operator() ==1){
                    fullUser.setOperator(1);
                } else fullUser.setOperator(0);
        /*
        Разделение на аналитиков разного уровня тоже нет, объединяем в одну роль - DEPARTMENT_ANALYST
         */
        if (fullUser.getPostamt_analyst() ==1 ){
            fullUser.setUfps_analyst(1);
        }
        try {
            fullUser.setLogin(LoginGenerate.generateLogin(fullUser.getName()));
        } catch (Exception e){
            System.out.println(e.getMessage() + "for user with name = " + fullUser.getName());
        }

        fullUsers.add(fullUser);
    }

}
