package com.adityashri.bit.constant;

import java.util.ArrayList;

public class Constants {
    private static int currentBug;

    private static String adminMenu;
    private static String developerMenu;
    private static String testerMenu;

    private static ArrayList<String> adminUris;
    private static ArrayList<String> developerUris;
    private static ArrayList<String> testerUris;

    static void initUris(){
        adminUris = new ArrayList<>();
        adminUris.add("/bugs/add");
        adminUris.add("/bugs/view");
        adminUris.add("/bugs/modify");
        adminUris.add("/bugs/delete");
        adminUris.add("/projects/create");
        adminUris.add("/projects/view");
        adminUris.add("/projects/modify");
        adminUris.add("/projects/delete");
        adminUris.add("/user/add");
        adminUris.add("/user/view");
        adminUris.add("/user/modify");
        adminUris.add("/user/delete");

        developerUris = new ArrayList<>();
        developerUris.add("/bugs/view");
        developerUris.add("/bugs/modify");
        developerUris.add("/projects/view");

        testerUris = new ArrayList<>();
        testerUris.add("/bugs/add");
        testerUris.add("/bugs/view");
        testerUris.add("/bugs/modify");
        testerUris.add("/bugs/delete");
        testerUris.add("/projects/view");
    }

    public static boolean checkUri(String uri, String position){
        initUris();
        switch (position){
            case "admin":{
                if(adminUris.contains(uri)){
                    return true;
                }else{
                    return false;
                }
            }
            case "developer":{
                if(developerUris.contains(uri)){
                    return true;
                }else{
                    return false;
                }
            }
            default:{
                if(testerUris.contains(uri)){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }

    static void initMenu(){
        if(adminMenu == null) {
            adminMenu = "<a class=\"dropdown-btn\">Project</a>\n" +
                    "    <div class=\"dropdown-container\">\n" +
                    "        <a class=\"item\" href=\"/projects/create\">Create</a>\n" +
                    "        <a class=\"item\" href=\"/projects/view\">View</a>\n" +
                    "        <a class=\"item\" href=\"/projects/modify\">Modify</a>\n" +
                    "        <a class=\"item\" href=\"/projects/delete\">Delete</a>\n" +
                    "    </div>\n" +
                    "    <a class=\"dropdown-btn\">Bugs</a>\n" +
                    "    <div class=\"dropdown-container\">\n" +
                    "        <a class=\"item\" href=\"/bugs/add\">Add</a>\n" +
                    "        <a class=\"item\" href=\"/bugs/view\">View</a>\n" +
                    "        <a class=\"item\" href=\"/bugs/modify\">Modify</a>\n" +
                    "        <a class=\"item\" href=\"/bugs/delete\">Delete</a>\n" +
                    "    </div>\n" +
                    "    <a class=\"dropdown-btn\">User</a>\n" +
                    "    <div class=\"dropdown-container\">\n" +
                    "        <a class=\"item\" href=\"/user/add\">Add</a>\n" +
                    "        <a class=\"item\" href=\"/user/view\">View</a>\n" +
                    "        <a class=\"item\" href=\"/user/modify\">Modify</a>\n" +
                    "        <a class=\"item\" href=\"/user/delete\">Delete</a>\n" +
                    "    </div>";

            developerMenu = "<a href=\"/projects/view\">Projects</a>\n" +
                    "    <a class=\"dropdown-btn\">Bugs</a>\n" +
                    "    <div class=\"dropdown-container\">\n" +
                    "        <a class=\"item\" href=\"/bugs/view\">View</a>\n" +
                    "        <a class=\"item\" href=\"/bugs/modify\">Modify</a>\n" +
                    "    </div>\n";

            testerMenu = "<a href=\"/projects/view\">Project</a>\n" +
                    "    <a class=\"dropdown-btn\">Bugs</a>\n" +
                    "    <div class=\"dropdown-container\">\n" +
                    "        <a class=\"item\" href=\"/bugs/add\">Add</a>\n" +
                    "        <a class=\"item\" href=\"/bugs/view\">View</a>\n" +
                    "        <a class=\"item\" href=\"/bugs/modify\">Modify</a>\n" +
                    "        <a class=\"item\" href=\"/bugs/delete\">Delete</a>\n" +
                    "    </div>";
        }
    }

    public static String getMenuItems(String position){
        System.out.println(position);
        initMenu();
        switch (position){
            case "admin":
                return adminMenu;
            case "developer":
                return developerMenu;
            default:
                return testerMenu;
        }
    }

    public static int getCurrentBug() {
        return currentBug;
    }

    public static void setCurrentBug(int currentBug) {
        Constants.currentBug = currentBug;
    }

    public static ArrayList<String> getBugsSeverity() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Low");
        temp.add("Medium");
        temp.add("High");
        temp.add("Deadly");

        return temp;
    }

    public static ArrayList<String> getEmpoyeePosition() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Admin");
        temp.add("Developer");
        temp.add("Tester");

        return temp;
    }

    public static String AppName(){
        return "Debug It!";
    }
}
