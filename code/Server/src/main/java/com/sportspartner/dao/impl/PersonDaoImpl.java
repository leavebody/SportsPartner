package com.sportspartner.dao.impl;


import com.sportspartner.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sportspartner.util.ConnectionUtil;

public class PersonDaoImpl {
    /**
     *  Get all of the persons in the database
     * @return List of all persons
     */
//    public List<Person> getAllPersons(){
//
//        Connection c = new ConnectionUtil().connectDB();
//
//        List<Person> persons = new ArrayList<Person>();
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            stmt = c.createStatement();
//            rs = stmt.executeQuery("SELECT * FROM \"Person\" WHERE \"Person\".\"userId\"=\"User\".\"userId\";");
//
//            while (rs.next()) {
//                String userId = rs.getString("userId");
//                String userName = rs.getString("userName");
//                String address = rs.getString("address");
//                String gender = rs.getString("gender");
//                int age = rs.getInt("age");
//                double punctuality = rs.getDouble("punctuality");
//                int punctualityCount = rs.getInt("punctualityCount");
//                double participation = rs.getDouble("participation");
//                int participationCount = rs.getInt("participationCount");
//                String iconUUID = rs.getString("iconUUID");
//
//                persons.add(new Person(userId, userName, address, gender, age, punctuality, punctualityCount, participation, participationCount, iconUUID));
//            }
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//
//        } finally {
//            try {
//                rs.close();
//                stmt.close();
//                c.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return persons;
//    }

    /**
     * Get the person by searching userId in the database
     * @param userId Id of the person
     * @return Person Object
     */
    public Person getPerson(String userId) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        Person person = null;

        try {
            stmt = c.prepareStatement("SELECT * FROM \"User\",\"Person\" WHERE \"Person\".\"userId\"=\"User\".\"userId\" AND \"Person\".\"userId\" = ?;");
            stmt.setString(1, userId);
            rs = stmt.executeQuery();
            if(rs.next()) {
                String userName = rs.getString("userName");
                String address = rs.getString("address");
                String gender = rs.getString("gender");
                int age = rs.getInt("age");
                double punctuality = rs.getDouble("punctuality");
                int punctualityCount = rs.getInt("punctualityCount");
                double participation = rs.getDouble("participation");
                int participationCount = rs.getInt("participationCount");
                String iconUUID = rs.getString("iconUUID");

                person = new Person(userId, userName, address, gender, age, punctuality, punctualityCount, participation, participationCount, iconUUID);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                rs.close();
                stmt.close();
                c.close();
        }
        return person;
    }

    /**
     * Create a new Person in the database
     * @param person Person Object
     * @return true if the process succeeds; false if not
     */
    public boolean newPerson(Person person) throws SQLException{

        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String userId = person.getUserId();
        String userName = person.getUserName();
        String address = person.getAddress();
        String gender = person.getGender();
        int age = person.getAge();
        double punctuality = person.getPunctuality();
        int punctualityCount = person.getPunctualityCount();
        double participation = person.getParticipation();
        int participationCount = person.getPunctualityCount();
        String iconUUID = person.getIconUUID();


        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Person\" (\"userId\", \"userName\", \"address\", \"gender\", \"age\", \"punctuality\", \"punctualityCount\", \"participation\", \"participationCount\", \"iconUUID\")"+
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, userId);
            stmt.setString(2, userName);
            stmt.setString(3, address);
            stmt.setString(4, gender);
            stmt.setInt(5, age);
            stmt.setDouble(6, punctuality);
            stmt.setInt(7, punctualityCount);
            stmt.setDouble(8, participation);
            stmt.setInt(9, participationCount);
            stmt.setString(10, iconUUID);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                stmt.close();
                c.close();
        }
        return result;
    }

    /**
     * Update a new Person in the database
     * @param person Person Object
     * @return true if the process succeeds; false if not
     */
    public boolean updatePerson(Person person) throws SQLException{
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String userId = person.getUserId();
        String userName = person.getUserName();
        String address = person.getAddress();
        String gender = person.getGender();
        int age = person.getAge();
        double punctuality = person.getPunctuality();
        int punctualityCount = person.getPunctualityCount();
        double participation = person.getParticipation();
        int participationCount = person.getPunctualityCount();
        String iconUUID = person.getIconUUID();

        boolean result = false;

        try {
            stmt = c.prepareStatement("UPDATE \"Person\" SET \"userName\" = ?, \"address\" = ?, \"gender\" = ?, \"age\" = ?, \"punctuality\" = ?, \"punctualityCount\" = ?, \"participation\" = ?, \"participationCount\" = ?, \"iconUUID\" = ?" +
                    "WHERE \"userId\"=?;");
            stmt.setString(1, userName);
            stmt.setString(2, address);
            stmt.setString(3, gender);
            stmt.setInt(4, age);
            stmt.setDouble(5, punctuality);
            stmt.setInt(6, punctualityCount);
            stmt.setDouble(7, participation);
            stmt.setInt(8, participationCount);
            stmt.setString(9, iconUUID);
            stmt.setString(10, userId);
            rs = stmt.executeUpdate();

            if(rs>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                stmt.close();
                c.close();
        }
        return result;
    }
    /**
     *  Delete a new Person in the database
     * @param userId Id of a person
     * @return true if the process succeeds; false if not
     */
    public boolean deletePerson(String userId) throws SQLException{

        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;

        boolean result = false;

        try {
            stmt = c.prepareStatement("DELETE FROM \"Person\" WHERE \"userId\"=?;");
            stmt.setString(1, userId);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                stmt.close();
                c.close();
        }
        return result;
    }
}

