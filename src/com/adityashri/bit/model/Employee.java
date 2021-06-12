package com.adityashri.bit.model;

import java.util.ArrayList;
import java.util.Objects;

public class Employee implements Comparable<Employee>{

    private int id;
    private String ename;
    private String pass;
    private String position;
    private String projectid;

    public Employee(int id, String ename, String position, String projectid, String pass) {
        this.id = id;
        this.ename = ename;
        this.pass = pass;
        this.position = position;
        this.projectid = projectid;
    }

    public Employee(int id, String ename, String position, int projectid, String pass) {
        this(id,ename,position,String.valueOf(projectid),pass);
    }

    public Employee(Employee emp) {
        this(emp.getId(),emp.getEname(),emp.getPosition(),emp.getProjectidsfordao(),emp.getPass());
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ArrayList<Integer> getProjectids() {
        String[] temp = this.projectid.split(" ");
        ArrayList<Integer> res = new ArrayList<>();
        for (int i=1; i<temp.length; i++) {
            res.add(Integer.parseInt(temp[i]));
        }
        return res;
    }

    public String getProjectidsfordao(){return this.projectid;}

    public String getProjectidsInStr(){
        ArrayList<Integer> temp = getProjectids();
        String tmp = "";
        if(!temp.isEmpty()) {
            tmp = String.valueOf(temp.get(0));
            for (int i = 1; i < temp.size(); i++) {
                tmp += ", " + String.valueOf(temp.get(i));
            }
        }
        return tmp;
    }

    public void setProjectid(int projectid) {
        this.projectid +=  " " + projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid +=  " " + projectid;
    }

    public void removeProjectid(int projectid){
        ArrayList<Integer> ids = getProjectids();
        ids.remove(new Integer(projectid));
        String temp = "0";
        if(!ids.isEmpty()){
            for(int i: ids){
                temp += " " + i;
            }
        }
        this.projectid = temp;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "name='" + ename + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", projectid=" + projectid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(ename, employee.ename) &&
                Objects.equals(position, employee.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ename, id, position);
    }

    @Override
    public int compareTo(Employee o) {
        if(id==o.id){
            return 0;
        }else if(id < o.id){
            return -1;
        }else{
            return 1;
        }
    }
}
