package com.jarosciak.javascrabblesolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by jaros on 2/14/18.
 */

public class App {
    private JPanel panel1;
    private JTextField sgdregreTextField;
    private JTable table1;
    private JButton searchButton;


    public App() {
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TEST FILL TABLE
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

                } catch (Exception ee) {
                    ee.printStackTrace(System.out);
                }

            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("");
        frame.setContentPane(new App().panel1);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);




    }

}


