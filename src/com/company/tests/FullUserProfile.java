package com.company.tests;


import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class FullUserProfile {

    private String name = null;
    private String email = null;
    private String ufps = null;
    private String postamt = null;
    private String department = null;
    private int operator;

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    private int ufps_operator;
    private int ufps_controler;
    private int ufps_analyst;
    private int postamt_operator;
    private int postamt_controler;
    private int postamt_analyst;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private int ops_resp;
    private int requset_resp;
    private String indexs_OPS = null;
    private static int ID; // увеличичается с каждым новым экземпляром
    private int id=0;
    private String dropped = "0";
    private String guid = RandomGUID.createGUID();
    private String pwd = "pwd";
    private String added = "0";
    private String pwdHash = "pwdHash";
    private String login = null;

    FullUserProfile(){
        ID++;
        id=ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        FullUserProfile.ID = ID;
    }

    public String getDropped() {
        return dropped;
    }

    public void setDropped(String dropped) {
        this.dropped = dropped;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public int getUfps_operator() {
        return ufps_operator;
    }

    public void setUfps_operator(int ufps_operator) {
        this.ufps_operator = ufps_operator;
    }

    public int getUfps_controler() {
        return ufps_controler;
    }

    public void setUfps_controler(int ufps_controler) {
        this.ufps_controler = ufps_controler;
    }

    public int getUfps_analyst() {
        return ufps_analyst;
    }

    public void setUfps_analyst(int ufps_analyst) {
        this.ufps_analyst = ufps_analyst;
    }

    public int getPostamt_operator() {
        return postamt_operator;
    }

    public void setPostamt_operator(int postamt_operator) {
        this.postamt_operator = postamt_operator;
    }

    public int getPostamt_controler() {
        return postamt_controler;
    }

    public void setPostamt_controler(int postamt_controler) {
        this.postamt_controler = postamt_controler;
    }

    public int getPostamt_analyst() {
        return postamt_analyst;
    }

    public void setPostamt_analyst(int postamt_analyst) {
        this.postamt_analyst = postamt_analyst;
    }

    public int getOps_resp() {
        return ops_resp;
    }

    public void setOps_resp(int ops_resp) {
        this.ops_resp = ops_resp;
    }

    public int getRequset_resp() {
        return requset_resp;
    }

    public void setRequset_resp(int requset_resp) {
        this.requset_resp = requset_resp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUfps() {
        return ufps;
    }

    public void setUfps(String ufps) {
        this.ufps = ufps;
    }

    public String getPostamt() {
        return postamt;
    }

    public void setPostamt(String postamt) {
        this.postamt = postamt;
    }

    public String getIndexs_OPS() {
        return indexs_OPS;
    }

    public void setIndexs_OPS(String indexs_OPS) {
        this.indexs_OPS = indexs_OPS;
    }
}
