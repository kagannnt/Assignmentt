import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initDatabase();
        Scanner sc = new Scanner(System.in);


        List<Workout> check = DatabaseManager.getAllWorkouts();
        if (check.isEmpty()) {
            DatabaseManager.addWorkout("Интенсивный бег", 45, "Cardio", 8.0);
            DatabaseManager.addWorkout("Full Body Power", 60, "Strength", 7.0);
        }

        System.out.println("=== Smart Fitness App (SQLite Edition) ===");
        System.out.print("Your weight (кг): ");
        double weight = sc.nextDouble();

        // 3. READ
        List<Workout> dataPool;
        try {
            dataPool = DatabaseManager.getAllWorkouts();
        } catch (Exception e) {
            dataPool = new ArrayList<>();
        }

        // Если база пустая (ошибка драйвера), берем из репозитория
        if (dataPool == null || dataPool.isEmpty()) {
            System.out.println("\n[System] Database connection failed. Loading local data...");
            dataPool = DataRepository.getInitialWorkouts();
        }



        System.out.println("\nВыберите цель:");
        System.out.println("1 - Cardio (Жиросжигание)");
        System.out.println("2 - Strength (Сила)");
        int choice = sc.nextInt();

        Class<?> targetClass = (choice == 1) ? Cardio.class : Strength.class;


        List<Workout> myPlan = dataPool.stream()
                .filter(targetClass::isInstance)
                .sorted(Comparator.comparingInt(Workout::getDurationMin))
                .collect(Collectors.toList());

        if (myPlan.isEmpty()) {
            System.out.println("К сожалению, тренировок этого типа не найдено.");
        } else {
            System.out.println("\nYour training plan:");
            for (int i = 0; i < myPlan.size(); i++) {
                System.out.println((i + 1) + ". " + myPlan.get(i));
            }

            System.out.print("\nChoose number of the execise: ");
            int idx = sc.nextInt() - 1;

            if (idx >= 0 && idx < myPlan.size()) {
                Workout selected = myPlan.get(idx);
                System.out.println("\n[ИНФО] Тренировка '" + selected.getTitle() + "' началась...");
                double burned = selected.calculateCalories(weight);
                System.out.printf("Готово! Вы сожгли %.2f ккал.%n", burned);
            } else {
                System.out.println("Ошибка: неверный номер тренировки.");
            }
        }

        System.out.println("\n=== Программа завершена успешно ===");


        System.out.println("\n---REST API JSON RESPONSE ---");
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(dataPool));
    }
}