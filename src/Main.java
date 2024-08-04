import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // 1. Поиск количества несовершеннолетних (до 18 лет)
        long minorsCount = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + minorsCount);

        // 2. Получение списка фамилий призывников (мужчины от 18 до 27 лет)
        List<String> conscripts = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Количество призывников: " + conscripts.size());
        // Например, вывод первых 10 призывников:
        conscripts.stream().limit(10).forEach(System.out::println);

        // 3. Получение отсортированного по фамилии списка потенциально работоспособных людей с высшим образованием
        List<Person> workablePeople = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> (person.getSex() == Sex.WOMAN && person.getAge() >= 18 && person.getAge() <= 60) ||
                        (person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() <= 65))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Количество работоспособных людей с высшим образованием: " + workablePeople.size());
        // Например, вывод первых 10 работоспособных людей с высшим образованием:
        workablePeople.stream().limit(10).forEach(System.out::println);
    }
}