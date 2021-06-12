package com.adityashri.bit.dao;

import com.adityashri.bit.connection.ConnectionUtil;
import com.adityashri.bit.model.Employee;
import com.adityashri.bit.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectDAO {
    static Connection conn;

    public static Project getProject(int projectid) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        try {
            PreparedStatement ps = conn.prepareStatement("select * from project where id=?");
            ps.setInt(1, projectid);
            ResultSet rs = ps.executeQuery();
            Project project = null;
            while (rs.next()) {
                project = new Project(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
            return project;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Project> getAllProjects(ArrayList<Integer> projectids) {
        ArrayList<Project> projects = new ArrayList<>();
        if (!projectids.isEmpty()) {
            for (int p : projectids) {
                Project temp = new Project(getProject(p));
                if (temp.getPname() != null) {
                    projects.add(new Project(temp));
                }
            }
        }
        Collections.sort(projects);
        return projects;
    }

    public static ArrayList<Project> getAllProjects() {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        ArrayList<Project> projects = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from project");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project project = new Project(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                projects.add(project);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return projects;
    }

    public static boolean addProject(Project p) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("insert into project (pname,about,employees,status) values(?,?,?,?);");
            ps.setString(1, p.getPname());
            ps.setString(2, p.getAbout());
            ps.setString(3, p.getEmployeeidsfordao());
            ps.setString(4,p.getStatus());
            int result = ps.executeUpdate();
            if (result > 0) {
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static int getProjectid(String pname) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        int temp = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from project where pname=?");
            ps.setString(1, pname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project project = new Project(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                temp = project.getId();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return temp;
    }

    public static boolean modifyProject(Project project) {
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("update project set " +
                    "pname=?, " +
                    "about=?, " +
                    "employees=?, " +
                    "status=? " +
                    "where id=?");
            ps.setString(1,project.getPname());
            ps.setString(2,project.getAbout());
            ps.setString(3,project.getEmployeeidsfordao());
            ps.setString(4,project.getStatus());
            ps.setInt(5,project.getId());
            int result = ps.executeUpdate();
            if(result>0){
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static void updateProjects(int id, Employee emp, ArrayList<Project> projects) {
        ArrayList<Integer> projectids = emp.getProjectids();
        if(!projectids.isEmpty()){
            for(Project p : projects){
                if(projectids.contains(new Integer(p.getId()))){
                    p.setEmployeeids(id);
                    modifyProject(p);
                }
            }
        }
    }

    public static void updateProjects(int id, Employee emp, ArrayList<Project> projects, ArrayList<Integer> oldProjectids) {
        ArrayList<Integer> proids = new ArrayList<>();
        ArrayList<Integer> temp = emp.getProjectids();

        if(!temp.isEmpty()) {
            for (int t:temp) {
                try {
                    if (!oldProjectids.isEmpty()){
                        if(oldProjectids.contains(new Integer(t))) {
                            oldProjectids.remove(new Integer(t));
                        }else{
                            proids.add(t);
                        }
                    } else {
                        proids.add(t);
                    }
                } catch (Exception e) {
                }
            }
        }


        for (Project p : projects) {
            if(!proids.isEmpty()) {
                if (proids.contains(new Integer(p.getId()))) {
                    p.setEmployeeids(id);
                    modifyProject(p);
                }
            }
            if(!oldProjectids.isEmpty()) {
                if(oldProjectids.contains(new Integer(p.getId()))) {
                    p.removeEmployeeid(id);
                    modifyProject(p);
                }
            }
        }
    }

    public static void updateProjectsAfterUserDeletion(Employee emp) {
        ArrayList<Integer> projectids = emp.getProjectids();

        for(int id:projectids){
            Project project = getProject(id);
            if(project!=null){
                project.removeEmployeeid(emp.getId());
                modifyProject(project);
            }
        }
    }

    public static boolean deleteProject(Project project) {
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("delete from project where id=?");
            ps.setInt(1,project.getId());
            int result = ps.executeUpdate();
            if(result>0){
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }
}
