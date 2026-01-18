import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    // Используем относительный путь.
    // Если файл в корне проекта, Java его найдет.
    private static final String URL = "jdbc:sqlite:fitness.db";

    public List<Workout> getWorkoutsFromDB() {
        List<Workout> list = new ArrayList<>();
        String sql = "SELECT * FROM workouts";

        try {
            // 1. ПРИНУДИТЕЛЬНО ГРУЗИМ ДРАЙВЕР
            // Эта строка связывает твою галочку в настройках с кодом
            Class.forName("org.sqlite.JDBC");

            // 2. ОТКРЫВАЕМ СОЕДИНЕНИЕ
            try (Connection conn = DriverManager.getConnection(URL);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    String title = rs.getString("title");
                    int duration = rs.getInt("duration_min");
                    double multiplier = rs.getDouble("difficulty_multiplier");
                    String type = rs.getString("workout_type");

                    if ("CARDIO".equals(type)) {
                        list.add(new Cardio(title, duration, multiplier));
                    } else {
                        list.add(new Strength(title, duration, multiplier));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка: Драйвер SQLite не найден! Проверь Libraries.");
        } catch (SQLException e) {
            System.out.println("Ошибка БД: " + e.getMessage());
        }
        return list;
    }
}