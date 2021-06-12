package com.adityashri.bit.model;

import java.util.ArrayList;
import java.util.Objects;

public class Project implements Comparable<Project> {
    private int id;
    private String pname;
    private String about;
    private String employeeids;
    private String status;

    public Project(int id, String pname, String about, String empid, String status) {
        this.id = id;
        this.pname = pname;
        this.about = about;
        this.employeeids = empid.equalsIgnoreCase("")?"":empid;
        this.status = status;
    }

    public Project(int id, String pname, String about, int empid, String status) {
        this(id,pname,about,String.valueOf(empid),status);
    }

    public Project(int id, String pname, String about,String status) {
        this(id,pname,about,"",status);
    }

    public Project(int id, String pname, String about, Employee emp,String status) {
        this(id,pname,about,String.valueOf(emp.getId()),status);
    }

    public Project(Project p) {
        if (p != null) {
            this.id = p.getId();
            this.pname = p.getPname();
            this.about = p.getAbout();
            this.employeeids = p.getEmployeeidsfordao();
            this.status = p.getStatus();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmployeeidsfordao() {
        return employeeids;
    }

    public String getEmployeeidsInStr() {
        ArrayList<Integer> emps = getEmployeeids();
        String temp = "";
        if(!emps.isEmpty()){
            temp += String.valueOf(emps.get(0));
            for(int i=1; i<emps.size(); i++){
                temp+= ", " + String.valueOf(emps.get(i));
            }
        }
        return temp;
    }

    public ArrayList<Integer> getEmployeeids() {
        ArrayList<Integer> emps = new ArrayList<>();
        if(this.employeeids != null){
            String[] emp = this.employeeids.split(" ");
            for(String e:emp){
                emps.add(Integer.parseInt(e));
            }
        }
        return emps;
    }

    public void setEmployeeids(int empid) {
        this.employeeids += " " + empid;
    }

    public void setEmployeeids(Employee emp) {
        this.employeeids += " " + emp.getId();
    }

    public void removeEmployeeid(int id) {
        ArrayList<Integer> ids = getEmployeeids();
        ids.remove(new Integer(id));
        String temp = "";
        if(!ids.isEmpty()){
            temp += ids.get(0);
            for(int i=1; i<ids.size(); i++){
                temp += " " + ids.get(i);
            }
        }
        this.employeeids = temp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name=" + pname +
                ", description='" + about + '\'' +
                ", employees=" + employeeids +
                ", status= " + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Project o) {
        if (id == o.id) {
            return 0;
        } else if (id < o.id) {
            return -1;
        } else {
            return 1;
        }
    }

}
