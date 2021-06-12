package com.adityashri.bit.dao;

import com.adityashri.bit.connection.ConnectionUtil;
import com.adityashri.bit.model.Bugs;
import com.adityashri.bit.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BugsDAO {
    static Connection conn;

    public static Bugs getBug(int bugid){
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        Bugs bg = null;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from bugs where id=?");
            ps.setInt(1,bugid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                bg = new Bugs(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getString(7));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bg;
    }

    public static ArrayList<Bugs> getAllBugs(int projectid){
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        ArrayList<Bugs> bugs = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from bugs where projectid=? order by id");
            ps.setInt(1,projectid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Bugs temp = new Bugs(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getString(7));
                bugs.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bugs;
    }

    public static ArrayList<Bugs> getBugsForAllProjects(ArrayList<Project> projects){
        ArrayList<Bugs> bugs = new ArrayList<>();
        if(!projects.isEmpty()) {
            for (Project p : projects) {
                bugs.addAll(getAllBugs(p.getId()));
            }
        }
        return bugs;
    }

    public static boolean modifyBug(Bugs bug) {
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("update bugs set " +
                    "bname=?, " +
                    "about=?, " +
                    "severity=?, " +
                    "projectid=?, " +
                    "empid=?, " +
                    "status=? " +
                    "where id=?");
            ps.setString(1,bug.getBname());
            ps.setString(2,bug.getAbout());
            ps.setString(3,bug.getSeverity());
            ps.setInt(4,bug.getProjectid());
            ps.setInt(5,bug.getEmpid());
            ps.setString(6,bug.getStatus());
            ps.setInt(7,bug.getId());
            int result = ps.executeUpdate();
            if(result>0){
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static int getBugsCount(){
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        int count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("select count(1) from bugs");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                 count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count+1;
    }

    public static boolean addBug(Bugs bug) {
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("insert into bugs (bname,about,severity,projectid,empid,status) values(?,?,?,?,?,?);");
            ps.setString(1,bug.getBname());
            ps.setString(2,bug.getAbout());
            ps.setString(3,bug.getSeverity());
            ps.setInt(4,bug.getProjectid());
            ps.setInt(5,bug.getEmpid());
            ps.setString(6,bug.getStatus());
            int result = ps.executeUpdate();
            if(result>0){
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static void updateAfterProjectStatusChange(int projectid, String status) {
        System.out.println("In BugsDao : Projectid -> " + projectid + ", Status -> " + status);
        ArrayList<Bugs> bugs = getAllBugs(projectid);
        if(!bugs.isEmpty()){
            for(Bugs b: bugs){
                b.setStatus(status);
                modifyBug(b);
            }
        }
    }

    public static void deleteAfterProjectdeletion(Project project) {
        ArrayList<Bugs> bugs = getAllBugs(project.getId());
        if(!bugs.isEmpty()){
            for(Bugs b: bugs){
                deleteBug(b);
            }
        }
    }

    public static boolean deleteBug(Bugs bug) {
        if(conn == null){
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("delete from bugs where id=?");
            ps.setInt(1,bug.getId());
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
