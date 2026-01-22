import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:fitness.db";


    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS workouts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, duration INTEGER, type TEXT, intensity REAL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка БД: " + e.getMessage());
        }
    }

    public static void addWorkout(String title, int duration, String type, double intensity) {
        String sql = "INSERT INTO workouts(title, duration, type, intensity) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, duration);
            pstmt.setString(3, type);
            pstmt.setDouble(4, intensity);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }


    public static List<Workout> getAllWorkouts() {
        List<Workout> list = new ArrayList<>();
        String sql = "SELECT * FROM workouts";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("type");
                if (type.equals("Cardio")) {
                    list.add(new Cardio(rs.getString("title"), rs.getInt("duration"), rs.getDouble("intensity")));
                } else {
                    list.add(new Strength(rs.getString("title"), rs.getInt("duration"), rs.getDouble("intensity")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }


    public static void updateDuration(String title, int newDuration) {
        String sql = "UPDATE workouts SET duration = ? WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newDuration);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            System.out.println("Данные обновлены!");
        } catch (SQLException e) { e.printStackTrace(); }
    }


    public static void deleteWorkout(String title) {
        String sql = "DELETE FROM workouts WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
            System.out.println("Тренировка удалена.");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}