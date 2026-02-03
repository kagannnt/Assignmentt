import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WorkoutRestController {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void showJsonResponse() {
        var workouts = DatabaseManager.getAllWorkouts();
        String json = gson.toJson(workouts);

        System.out.println("\n--- MILESTONE 2: REST API JSON RESPONSE ---");
        System.out.println(json);
    }
}
