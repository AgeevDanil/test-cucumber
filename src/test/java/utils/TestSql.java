package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TestSql {
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost:9092/mem:testdb";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";

    public void searchItemByName(String foodname, String typeItem, String checkExot) {
        String query = "SELECT * FROM FOOD WHERE FOOD_NAME = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, foodname); // Установка параметра foodname в запросе
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("FOOD_ID");
                    String name = resultSet.getString("FOOD_NAME");
                    String type = resultSet.getString("FOOD_TYPE");
                    boolean isExotic = resultSet.getBoolean("FOOD_EXOTIC");
                    assertEquals(typeItem, type);
                    assertEquals(checkExot, String.valueOf(isExotic));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUnwantedFoods() {
        String query = "DELETE FROM FOOD WHERE FOOD_NAME NOT IN ('Апельсин', 'Капуста', 'Помидор', 'Яблоко')";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCountOfFood(String foodName, String foodType) {
        String query = "SELECT COUNT(*) FROM FOOD WHERE FOOD_NAME = ? AND FOOD_TYPE = ?";
        int count = 0;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, foodName);
            pstmt.setString(2, foodType);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertNotEquals(count, 0);
    }

}