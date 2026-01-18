import java.util.Objects;

public abstract class Workout {
    private String title;
    private int durationMin;
    private double difficultyMultiplier; // На этот коэффициент умножаем калории

    public Workout(String title, int durationMin, double difficultyMultiplier) {
        this.title = title;
        this.durationMin = durationMin;
        this.difficultyMultiplier = difficultyMultiplier;
    }

    public String getTitle() { return title; }
    public int getDurationMin() { return durationMin; }
    public double getMultiplier() { return difficultyMultiplier; }

    public abstract double calculateCalories(double userWeight);

    @Override
    public String toString() {
        return String.format("[%s] Длительность: %d мин", title, durationMin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workout)) return false;
        Workout workout = (Workout) o;
        return durationMin == workout.durationMin && Objects.equals(title, workout.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, durationMin);
    }
}