package com.jarosciak.javascrabblesolver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {

    // declaring the components
    private JPanel panel1;
    private JTextField searchText;
    private JTable table;
    private JButton searchButton;
    private JScrollPane scroller;
    int scrabbleValue = 0;


    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        frame.setContentPane(new App().panel1);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }


    public App() {

        // Listen to Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbQuery();
            }
        });

        // Listen to Enter Button on the Text Entry Box
        searchText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbQuery();
            }
        });
    }

    public int charScrabbleValue(String chr) {
        /*
        1 point: E ×12, A ×9, I ×9, O ×8, N ×6, R ×6, T ×6, L ×4, S ×4, U ×4
        2 points: D ×4, G ×3
        3 points: B ×2, C ×2, M ×2, P ×2
        4 points: F ×2, H ×2, V ×2, W ×2, Y ×2
        5 points: K ×1
        8 points: J ×1, X ×1
        10 points: Q ×1, Z ×1
        */
        int charValue = 0;

        if ((chr.contains("a"))||(chr.contains("e"))||(chr.contains("i"))||(chr.contains("o"))||(chr.contains("n"))
            ||(chr.contains("r"))||(chr.contains("t"))||(chr.contains("l"))||(chr.contains("s"))|| (chr.contains("u"))
            ) {charValue=1;}

        if ((chr.contains("d"))||(chr.contains("g")) ) {charValue=2;}

        if ((chr.contains("b"))||(chr.contains("c"))||(chr.contains("m"))||(chr.contains("p")) ) {charValue=3;}

        if ((chr.contains("f"))||(chr.contains("h"))||(chr.contains("v"))||(chr.contains("w"))||(chr.contains("y"))
            ) {charValue=4;}

        if ((chr.contains("k"))) {charValue=5;}

        if ((chr.contains("j"))||(chr.contains("x"))) {charValue=8;}

        if ((chr.contains("q"))||(chr.contains("z")) ) {charValue=10;}

        System.out.println("Value is: " + charValue) ;
        return charValue;
    }


    public String createSqlQueryAndTotalScrabbleValue(String enteredWord) {
        // split entered string into letters
        String[] array = enteredWord.split("(?!^)");
        int arrLen = array.length;
        String sqlQuery = "SELECT WORD FROM PUBLIC.WORDS WHERE ";

        for(int i=0; i<arrLen; i++){
            sqlQuery = sqlQuery +  "word LIKE '%" + array[i] + "%' AND ";
            scrabbleValue = scrabbleValue + charScrabbleValue(array[i]);
            System.out.println("Letter is: " + array[i] + " - " + scrabbleValue) ;
        }
        sqlQuery = sqlQuery.replaceFirst(" AND $", "");

        sqlQuery = sqlQuery + " AND LENGTH(word)<="+arrLen;
        return sqlQuery;
    }


    public void dbQuery() {
        // reset scrabble value
        scrabbleValue = 0;
        // TEST FILL TABLE
        try {

            // Connect to Embedded DB
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection con = DriverManager.getConnection("jdbc:hsqldb:file:src/db/endb/", "admin", "admin");
            Statement stmt = con.createStatement();

            // Setup Table for Displaying Results
            DefaultTableModel model = new DefaultTableModel();
            table.setAutoCreateRowSorter(true);
            table.setFillsViewportHeight(true);
            scroller.setVisible(true);
            model.addColumn("#");
            model.addColumn("Word");
            model.addColumn("Scrabble Value");
            table.setModel(model);
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));

            // Display Results
            String searchLetters = searchText.getText().toLowerCase();

            // Build SQL Search Query based on the letters entered by the user
            String sqlQuery = createSqlQueryAndTotalScrabbleValue(searchLetters);
            System.out.println(sqlQuery);

            // Run SQL query
            ResultSet result = stmt.executeQuery(sqlQuery);
            int count = 0;
            while (result.next()) {
                count++;
                String foundWord = result.getString("word");

                // add found row to table
                model.addRow(new Object[]{count, foundWord, scrabbleValue});
            }

            con.close();

        } catch (Exception ee) {
            ee.printStackTrace(System.out);
        }
    }


}


