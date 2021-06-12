package com.adityashri.bit.dao;

import com.adityashri.bit.connection.ConnectionUtil;
import com.adityashri.bit.model.Employee;
import com.adityashri.bit.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeesDAO {
    static Connection conn;

    public static boolean checkEmp(String ename, String pass) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        try {
            PreparedStatement ps = conn.prepareStatement("Select * from Employees where ename=? and pass=?");
            ps.setString(1, ename);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addEmp(Employee emp) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("insert into employees (ename,position,projectid,pass) values(?,?,?,?);");
            ps.setString(1, emp.getEname());
            ps.setString(2, emp.getPosition());
            ps.setString(3, emp.getProjectidsfordao());
            ps.setString(4, emp.getPass());
            int result = ps.executeUpdate();
            if (result > 0) {
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static boolean updatePassword(String userName, String password, String newPassword) {
        boolean res = checkEmp(userName, password);
        if (res) {
            try {
                PreparedStatement ps = conn.prepareStatement("Update Employees set pass=? where ename=?");
                ps.setString(1, newPassword);
                ps.setString(2, userName);
                int result = ps.executeUpdate();
                if (result > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return res;
        }
    }

    public static Employee getEmp(String ename) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        Employee emp = null;
        try {
            PreparedStatement ps = conn.prepareStatement("Select * from Employees where ename=?");
            ps.setString(1, ename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5));
            }
            return emp;
        } catch (Exception e) {
            e.printStackTrace();
            return emp;
        }
    }

    public static Employee getPass(String ename) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        Employee emp = null;
        try {
            PreparedStatement ps = conn.prepareStatement("Select * from Employees where ename=?");
            ps.setString(1, ename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5));
            }
            return emp;
        } catch (Exception e) {
            e.printStackTrace();
            return emp;
        }
    }

    public static Employee getEmp(int id) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        Employee emp = null;
        try {
            PreparedStatement ps = conn.prepareStatement("Select * from Employees where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5));
            }
            return emp;
        } catch (Exception e) {
            e.printStackTrace();
            return emp;
        }
    }

    public static ArrayList<Employee> getAllEmployees() {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("Select * from Employees");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5));
                emps.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emps;
    }

    public static ArrayList<Employee> getAllEmployees(ArrayList<Integer> employeeids) {
        ArrayList<Employee> emps = new ArrayList<>();
        if (employeeids != null) {
            for (int p : employeeids) {
                Employee temp = new Employee(getEmp(p));
                if (temp.getEname() != null) {
                    emps.add(temp);
                }
            }
        }
        return emps;
    }

    public static boolean modifyEmployee(Employee emp) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("update employees set " +
                    "ename=?, " +
                    "position=?, " +
                    "projectid=?, " +
                    "pass=? " +
                    "where id=?");
            ps.setString(1, emp.getEname());
            ps.setString(2, emp.getPosition());
            ps.setString(3, emp.getProjectidsfordao());
            ps.setString(4, emp.getPass());
            ps.setInt(5, emp.getId());
            int result = ps.executeUpdate();
            if (result > 0) {
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static void updateEmployees(int projectid, String employeeids, ArrayList<Employee> employees) {
        ArrayList<Integer> empids = new ArrayList<>();
        String[] temp = employeeids.split(" ");
        for (String t : temp) {
            try {
                empids.add(Integer.parseInt(t));
            } catch (Exception e) {
            }
        }
        if (!empids.isEmpty()) {
            for (Employee e : employees) {
                if (empids.contains(e.getId())) {
                    e.setProjectid(projectid);
                    modifyEmployee(e);
                }
            }
        }
    }

    public static void updateEmployees(int projectid, ArrayList<Integer> oldempids, String newempids, ArrayList<Employee> employees) {
        ArrayList<Integer> empids = new ArrayList<>();
        String[] temp = newempids.split(" ");
        for (String t : temp) {
            try {
                int id = Integer.parseInt(t);
                if (oldempids.contains(new Integer(id))) {
                    oldempids.remove(new Integer(id));
                } else {
                    empids.add(id);
                }
            } catch (Exception e) {
            }
        }


        for (Employee e : employees) {
            if (!empids.isEmpty()) {
                if (empids.contains(new Integer(e.getId()))) {
                    e.setProjectid(projectid);
                    modifyEmployee(e);
                }
            }
            if (!oldempids.isEmpty()) {
                if (oldempids.contains(new Integer(e.getId()))) {
                    e.removeProjectid(projectid);
                    modifyEmployee(e);
                }
            }
        }
    }

    public static int getEmployeeId(String ename, String password) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        int id = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("Select * from Employees where ename=? and pass=?");
            ps.setString(1, ename);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5));
                id = emp.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static boolean deleteEmployee(Employee emp) {
        if (conn == null) {
            conn = ConnectionUtil.getConnection();
        }
        boolean res = false;
        try {
            PreparedStatement ps = conn.prepareStatement("delete from employees where id=?");
            ps.setInt(1, emp.getId());
            int result = ps.executeUpdate();
            if (result > 0) {
                res = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static void updateEmployeesAfterProjectDelete(Project project) {
        ArrayList<Integer> empids = project.getEmployeeids();
        ArrayList<Employee> employees = getAllEmployees(empids);
        if (!employees.isEmpty()) {
            for(Employee e : employees) {
                e.removeProjectid(project.getId());
                modifyEmployee(e);
            }
        }
    }
}
