public class Strength extends Workout {
    public Strength(String title, int durationMin, double difficultyMultiplier) {
        super(title, durationMin, difficultyMultiplier);
    }

    @Override
    public double calculateCalories(double userWeight) {

        return (getMultiplier() * 0.8) * userWeight * (getDurationMin() / 60.0);
    }
}