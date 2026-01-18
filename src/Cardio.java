public class Cardio extends Workout {
    public Cardio(String title, int durationMin, double difficultyMultiplier) {
        super(title, durationMin, difficultyMultiplier);
    }

    @Override
    public double calculateCalories(double userWeight) {
        return getMultiplier() * userWeight * (getDurationMin() / 60.0);
    }
}