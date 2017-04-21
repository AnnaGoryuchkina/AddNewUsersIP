package com.company.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfileCheck {
    UserProfile user = new UserProfile();
    FullUserProfile fullUser = new FullUserProfile();
    static List<FullUserProfile> fullUsers = new ArrayList<>();
    List<String> departments = GetDepartmentInfo.departments;
    List<String> indexes = GetDepartmentInfo.indexes;

    UserProfileCheck(UserProfile user){
        this.user = user;
    }

    public void check() throws Exception {

        /*
        Check name
         */
        Pattern p_name = Pattern.compile(Constants.USER_NAME);
        //Pattern p_name = Pattern.compile("^([А-я]+\\s+){2}[А-я]+\\s*$");
        Matcher m_name = p_name.matcher(user.getName());
        if (m_name.matches()){
            fullUser.setName(user.getName());
        } else {
            System.out.println("Problem with name.");
            throw new Exception();
        }

        /*
        Check email
         */
        Pattern p_mail = Pattern.compile(Constants.EMAIL_P);
        Matcher m_mail = p_mail.matcher(user.getEmail());
        if (m_mail.matches()){ fullUser.setEmail(user.getEmail());}
        else {
            System.out.println("Problem with email.");
            throw new Exception();
        }

        /*
        Check ufps and postamt.
         */
        boolean ufpsIsSet=false;
        boolean postamtIsSet = false;

        Pattern p_ufps = Pattern.compile(Constants.UFPS_P);
        Matcher m_ufps = p_ufps.matcher(user.getUfps());
        if (m_ufps.matches()) {
            /*
            Проверяем напрямую имя департамента полностью
             */
            for (String one : departments) {
                if (one.equals(user.getUfps())) {
                    fullUser.setUfps(user.getUfps());
                    ufpsIsSet = true;
                    break;
                }
            }

            /*
            Проверяем только по индексам.
             */
            if (ufpsIsSet == false) {
                for (String index : indexes) {
                    if (GetDepartmentInfo.getIndexFromGroupName(user.getUfps()).equals(index)) {
                        fullUser.setUfps(GetDepartmentInfo.returnGroupName(index));
                        ufpsIsSet = true;
                        break;
                    }
                }
            }
            if (ufpsIsSet == false) {
                System.out.println("No UFPS department matches with the system for user: " + user.getName());
            }
        }


                if (user.getPostamt() != null) {
                    Pattern p_postamt = Pattern.compile(Constants.POSTAMT_P);
                    Matcher m_postamt = p_postamt.matcher(user.getPostamt());
                    if (m_postamt.matches()) {
                        /*
                        Check department names
                         */
                        for (String one : departments) {
                            if (one.equals(user.getPostamt())) {
                                fullUser.setPostamt(user.getPostamt());
                                postamtIsSet = true;
                                break;
                            }
                        }

                        /*
                        Check indexes if no name matches
                         */

                        if (postamtIsSet == false) {
                            for (String index : indexes) {
                                if (GetDepartmentInfo.getIndexFromGroupName(user.getPostamt()).equals(index)) {
                                    fullUser.setPostamt(GetDepartmentInfo.returnGroupName(index));
                                    postamtIsSet = true;
                                    break;
                                }
                            }
                        }
                        if (postamtIsSet == false) {
                            System.out.println("No Postamt department matches with the system for user: " + user.getName());
                        }

                    }
                }
                /*
                Cетаем поле Department который в результате пойдет в группу.
                 */
                if (fullUser.getPostamt() != null){
                    fullUser.setDepartment(fullUser.getPostamt());
                } else {fullUser.setDepartment(fullUser.getUfps());}

                /*
                Check roles
                 */

                List<Boolean> roles = new ArrayList<>(); //есть смысл подумать над map
                roles.add(user.isUfps_operator());
                roles.add(user.isUfps_controler());
                roles.add(user.isUfps_analyst());
                roles.add(user.isPostamt_operator());
                roles.add(user.isPostamt_controler());
                roles.add(user.isPostamt_analyst());
                roles.add(user.isOps_resp());
                roles.add(user.isRequset_resp());


                boolean con1 = false;
                boolean con2 = false;
                boolean con3 = false;

                //роли УФПС и Почтамта не могут пересекаться
                if ((roles.get(0) == true || roles.get(1)== true || roles.get(2)== true)
                        && (roles.get(3)== true || roles.get(4)== true || roles.get(5)== true)) {
                    System.out.println("Roles for ufps and for postamt intersect - wrong!!!  for user: " + user.getName());
                } else {
                    con1 = true;
                }
                //если указаны роли Почтамта, то должен быть указан Почтамт
                if (user.getPostamt() == null && (roles.get(3)== true || roles.get(4)== true || roles.get(5)== true)) {
                    System.out.println("User with postamt role and without postamt - wrong!!!  for user: " + user.getName());
                } else {
                    con2 = true;
                }

                if (roles.get(6) == true && user.getIndexs_OPS() == null) {
                    System.out.println("User has OPS responsible role, " +
                            "but no OPS indexes(or indexes without OPS resp role)  for user: " + user.getName());
                } else {
                    con3 = true;
                }

                if (con1 == true && con2 == true && con3 == true) {
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
                }

                if (user.isUfps_operator() || user.isPostamt_operator()){
                    fullUser.setOperator(1);
                } else fullUser.setOperator(0);

                fullUser.setLogin(LoginGenerate.generateLogin(user.getName()));
                    /*
                    TO-Do indexs_OPS parsing for OPS-responsible for the second excel document of another program
                     */
                    fullUser.setIndexs_OPS(user.getIndexs_OPS());
        fullUsers.add(fullUser);
    }

}
