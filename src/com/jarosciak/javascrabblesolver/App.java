package com.jarosciak.javascrabblesolver;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by jaros on 2/14/18.
 */

public class App {
    private JPanel panel1;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTable table1;



    public static void main(String[] args) {

        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:file:src/db/endb/", "admin", "admin");
            stmt = con.createStatement();
            result = stmt.executeQuery("SELECT ID,WORD FROM PUBLIC.WORDS WHERE word LIKE '%sheer%'");

            while(result.next()){
                System.out.println(result.getInt("id") + " | " + result.getString("word"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }


    }

}


