import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Простой калькулятор:
 * - Консольный ввод и вывод;
 * - Четыре арифметических действия;
 * - Учитывается порядок действий;
 * - Скобки, буквы, знаки препинания и т.д. (кроме точки) не обрабатываются;
 * - Ошибка ввода в случае неправильно введенных данных;
 * - На вход принимает как целые, так и действительные числа (вводить через точку);
 * - Вычисления производятся в действительных числах;
 * - Случаи деления на ноль обрабатываются стандартно.
 * */

public class Calculator {
    private static final List <Character> actions = List.of('/','*','-','+');

    public static void main(String[] args) {
        System.out.println("Введите выражение:");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        in.close();

        List <String> expression = new ArrayList<>();
        if (parse(input, expression) != 0) {
            return;
        }

        for (Character action:
             actions) {
            for (int i = 1; i < expression.size(); i += 2) {
                if (expression.get(i).equals(action.toString())) {
                    expression.set(i-1, Double.toString(operate(action, Double.parseDouble(expression.get(i-1)), Double.parseDouble(expression.get(i+1)))));
                    expression.remove(i+1);
                    expression.remove(i);
                    i-=2;
                }
            }
        }

        for (String str:
             expression) {
            System.out.println(str);
        }
    }

    private static int parse(String input, List<String> expression){
        input = input.replaceAll("\\s", "");
        int begin = 0;
        int end = 0;
        char currentChar;
        for (int i = 0; i < input.length(); i++) {
            currentChar = input.charAt(i);

            if (Character.isDigit(currentChar) || currentChar == '.' && i > 0 && input.charAt(i-1) != '.' && Character.isDigit(input.charAt(i-1))|| i == 0 && currentChar == '-') {
                end++;
                if (end >= input.length()) {
                    expression.add(input.substring(begin, end));
                }
            } else if (actions.contains(currentChar) && i != input.length() - 1 && i > 0 && Character.isDigit(input.charAt(i-1))) {
                expression.add(input.substring(begin, end));
                expression.add(String.valueOf(currentChar));
                begin = i + 1;
                end = begin;
            } else {
                System.out.println("Ошибка ввода");
                if (Character.isLetter(currentChar)) {
                    System.out.println("Введена буква");
                }
                return -1;
            }
        }
        return 0;
    }

    private static double operate(Character operator, double arg1, double arg2) {
        return switch (operator) {
            case '+' -> arg1 + arg2;
            case '-' -> arg1 - arg2;
            case '*' -> arg1 * arg2;
            case '/' -> arg1 / arg2;
            default -> arg1;
        };
    }

}
