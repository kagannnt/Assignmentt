import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // 1. Инициализация БД
        DatabaseManager.initDatabase();
        Scanner sc = new Scanner(System.in);

        // 2. CREATE
        List<Workout> initialCheck = DatabaseManager.getAllWorkouts();
        if (initialCheck.isEmpty()) {
            System.out.println("[System] База пуста. Добавляем начальные тренировки...");
            DatabaseManager.addWorkout("Интенсивный бег", 45, "Cardio", 8.0);
            DatabaseManager.addWorkout("Легкая ходьба", 30, "Cardio", 3.5);
            DatabaseManager.addWorkout("Full Body Power", 60, "Strength", 7.0);
            DatabaseManager.addWorkout("Жим лежа", 40, "Strength", 5.5);
        }

        System.out.println("=== Smart Fitness App (SQLite Edition) ===");
        System.out.print("Введите ваш вес (кг): ");
        double weight = sc.nextDouble();

        // 3. READ
        List<Workout> dataPool = DatabaseManager.getAllWorkouts();

        System.out.println("\nВыберите цель:");
        System.out.println("1 - Cardio (Жиросжигание)");
        System.out.println("2 - Strength (Сила)");
        int choice = sc.nextInt();

        Class<?> targetClass = (choice == 1) ? Cardio.class : Strength.class;


        List<Workout> myPlan = dataPool.stream()
                .filter(targetClass::isInstance)
                .sorted(Comparator.comparingInt(Workout::getDurationMin))
                .collect(Collectors.toList());

        System.out.println("\nВаш план тренировок:");
        for (int i = 0; i < myPlan.size(); i++) {
            System.out.println((i + 1) + ". " + myPlan.get(i));
        }

        System.out.print("\nВыберите номер тренировки для выполнения: ");
        int idx = sc.nextInt() - 1;

        if (idx >= 0 && idx < myPlan.size()) {
            Workout selected = myPlan.get(idx);


            System.out.println("\n[ИНФО] Тренировка '" + selected.getTitle() + "' началась...");
            double burned = selected.calculateCalories(weight);
            System.out.printf("Готово! Вы сожгли %.2f ккал.%n", burned);


            System.out.println("\n[DB Action] Обновляем длительность в базе (+5 мин)...");
            DatabaseManager.updateDuration(selected.getTitle(), selected.getDurationMin() + 5);


            System.out.print("\nХотите удалить эту тренировку из базы? (1 - да / 0 - нет): ");
            int deleteChoice = sc.nextInt();
            if (deleteChoice == 1) {
                DatabaseManager.deleteWorkout(selected.getTitle());
                System.out.println("Запись удалена из БД.");
            }

        } else {
            System.out.println("Ошибка: неверный номер тренировки.");
        }

        System.out.println("\n=== Программа завершена успешно ===");
    }
}
