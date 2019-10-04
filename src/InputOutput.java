import java.io.*;

/*Далее в комментариях: in - входные параметры метода;
*                       out - возвращаемое значение*/

public class InputOutput {

    /*readFile() - читает строку из файла и проверяет её на соответствие условиям.
    *              Для корректной строки с координатами определяет площадь треугольника. Записывает
    *              ее в отдельную переменную squareCurrentTriangle, которая сравнивается с максимальной
    *              на данный момент площадью squareMaxTriangle и перезаписывает при необходимости.
    *              Также записывает в отдельную переменную coordinatesMaxTriangle строку с координатами
    *              самого большого треугольника. После завершения чтения файла возвращает эту строку.
    *
    * in - строка, представляет путь к файлу, из которого происходит чтение;
    * out - строка с координатами максимального по площади треугольника*/
    public String readFile(String filename) {

        double squareMaxTriangle = 0;
        String coordinatesMaxTriangle = "Координаты отсутствуют";
        String lineFromFile;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            while ((lineFromFile = reader.readLine()) != null) {
                if (isCorrectAmount(lineFromFile) && isCorrectDigit(lineFromFile)
                        && isCorrectTriangle(parseToDouble(lineFromFile))) {

                    double squareCurrentTriangle = getArea(parseToDouble(lineFromFile));
                    if (squareCurrentTriangle > squareMaxTriangle) {
                        coordinatesMaxTriangle = lineFromFile;
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Файл не найден");
            e.printStackTrace();
        }
        catch (IOException o){
            System.out.println("Не удается прочитать данные из файла");
            o.printStackTrace();
        }
        return coordinatesMaxTriangle;
    }

    /* isCorrectAmount() - проверяет, что в строке находится именно 6 элементов, разделенных пробелом
    *
    * in - отдельная строка, прочитанная из файла;
    * out - логическое значение Да/Нет, отвечает, является ли строка корректной для дальнейшей обработки*/
    public boolean isCorrectAmount(String line) {
        String[] elements = line.split(" ");
        if (elements.length != 6) {
            System.out.println("Строка не соответствует условиям. Необходимо ввести 6 значений");
            return false;
        }
        return true;
    }


    /* isCorrectDigit() - проверяет, что в строке находятся только числа
    *
    * in - отдельная строка, прочитанная из файла;
    * out - логическое значение Да/Нет, отвечает, является ли строка корректной для дальнейшей обработки*/
    public boolean isCorrectDigit(String line) {
        String[] elements = line.split(" ");
        for (String s : elements) {
            try {
                Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Строка не соответствует условиям. Присутствуют нечисловые символы");
                return false;
            }
        }
        return true;
    }

    /* parseToDouble() - преобразует строку в массив отдельных координат
    *
    * in - отдельная строка, прочитанная из файла;
    * out - массив вещественных чисел, представляет собой координаты*/
    public double[] parseToDouble(String line) {
        double[] coordinates = new double[6];

        String[] elements = line.split(" ");
        for (int i = 0; i < 6; i++) {
            coordinates[i] = Double.parseDouble(elements[i]);
        }
        return coordinates;
    }

    /* findSidesOfTriangle() - определяет длину сторон фигуры по координатам
    *
    * in - массив вещественных чисел, представляет собой координаты;
    * out- массив вещественных чисел, представляет собой длины трех сторон фигуры*/
    public double[] findSidesOfTriangle(double[] coordinates){
        double[] sides = new double[3];

        double x1 = coordinates[0];
        double y1 = coordinates[1];
        double x2 = coordinates[2];
        double y2 = coordinates[3];
        double x3 = coordinates[4];
        double y3 = coordinates[5];

        double a = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double b = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
        double c = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));

        sides[0] = a;
        sides[1] = b;
        sides[2] = c;

        return sides;
    }


     /* isCorrectTriangle() - проверяет, является ли фигура равнобедренным треугольником
     *
     * in -  массив вещественных чисел, представляет собой координаты фигуры;
     * out - логическое значение Да/Нет, отвечает, является ли треугольник корректным по условиям задачи*/
    public boolean isCorrectTriangle(double[] coordinates) {

        double[] sides = findSidesOfTriangle(coordinates);
        double a = sides[0];
        double b = sides[1];
        double c = sides[2];

        //условие существования треугольника: любые две стороны больше третьей
        if (!(a + b > c || a + c > b || b + c > a)){
            System.out.println("Представленные координаты не являются координатами треугольника");
            return false;
        }
        //условие вырожденного треугольника: три вершины находятся на одной линии
        if (a == 0 || b == 0 || c == 0){
            System.out.println("Координаты представляют собой вырожденный треугольник");
            return false;
        }
        //условие равнобедренного треугольника: две стороны должны быть равны
        if (!(a == b || a == c || b == c)){
            System.out.println("Треугольник не является равнобедренным");
            return false;
        }

        return true;
    }


    /* getArea() - определяет площадь треугольника через полупериметр p
    *
    * in - массив вещественных чисел, представляет собой координаты треугольника;
    * out - вещественное число, представляет площадь треугольника*/
    public double getArea(double[] coordinates) {
        double square;

        double[] sides = findSidesOfTriangle(coordinates);
        double a = sides[0];
        double b = sides[1];
        double c = sides[2];

        double p = (a + b + c) / 2.0;
        square = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        return square;
    }


    /* writeCoordinatesToFile() - записывает строку с координатами в файл
    *
    * in - filename - адрес файла, в который записывается информация
    *      coordinates - строка с координатами самого большого треугольника*/
    public void writeCoordinatesToFile(String filename, String coordinates) {
        try( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {

            bufferedWriter.write("Координаты самого большого равнобедренного треугольника:\r\n"  + coordinates);
            System.out.println("Поиск завершен");
        }catch (IOException e){
            System.out.println("Не удается записать данные в файл");
            e.printStackTrace();
        }
    }
}
