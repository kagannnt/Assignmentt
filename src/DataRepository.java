import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    public static List<Workout> getInitialWorkouts() {
        List<Workout> list = new ArrayList<>();
        list.add(new Cardio("Morning Jogging", 30, 7.0));
        list.add(new Cardio("Fast Running", 45, 8.5));
        list.add(new Strength("Walking", 40, 6.0));
        list.add(new Strength("Biking", 50, 9.0));
        return list;
    }
}