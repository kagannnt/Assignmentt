import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DatabaseManager dbManager = new DatabaseManager();

        System.out.println("=== Регистрация в Smart Fitness (DB Mode) ===");
        System.out.print("Введите ваш рост (см): ");
        int height = sc.nextInt();
        System.out.print("Введите ваш вес (кг): ");
        double weight = sc.nextDouble();


        List<Workout> dataPool = dbManager.getWorkoutsFromDB();


        if (dataPool.isEmpty()) {
            System.out.println("\n[ОШИБКА] База данных пуста или не подключена!");
            return;
        }

        System.out.println("\nВыберите цель тренировки:");
        System.out.println("1 - Жиросжигание (Cardio)");
        System.out.println("2 - Набор массы (Strength)");
        int choice = sc.nextInt();

        Class<?> targetClass = (choice == 1) ? Cardio.class : Strength.class;


        List<Workout> myPlan = dataPool.stream()
                .filter(targetClass::isInstance)
                .sorted(Comparator.comparingInt(Workout::getDurationMin))
                .collect(Collectors.toList());

        System.out.println("\nВаш персональный план из базы данных (от коротких к длинным):");
        for (int i = 0; i < myPlan.size(); i++) {
            System.out.println((i + 1) + ". " + myPlan.get(i));
        }

        if (myPlan.isEmpty()) {
            System.out.println("Тренировок такого типа не найдено.");
            return;
        }

        System.out.print("\nВыберите номер тренировки, чтобы начать: ");
        int workoutIdx = sc.nextInt() - 1;

        if (workoutIdx >= 0 && workoutIdx < myPlan.size()) {
            Workout selected = myPlan.get(workoutIdx);
            System.out.println("\n[ИНФО] Тренировка '" + selected.getTitle() + "' началась...");
            System.out.println("...прошло " + selected.getDurationMin() + " минут...");

            double burned = selected.calculateCalories(weight);
            System.out.printf("Готово! Вы сожгли %.2f ккал.%n", burned);

            if (burned > 400) System.out.println("Отличный результат! Вы работали на пределе.");
        } else {
            System.out.println("Ошибка выбора.");
        }
    }
}