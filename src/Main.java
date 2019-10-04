
public class Main {
    public static void main(String[] args) {
        // аргументы командной строки. Путь к файлам для чтения и записи данных
        String inputFileName;
        String outputFileName;

        try {
            inputFileName = args[0];
            outputFileName = args[1];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Введите название файлов");
            e.printStackTrace();
            return;
        }
        InputOutput inout = new InputOutput();

        // строка с координатами самого большого треугольника
        String coordinates = inout.readFile(inputFileName);

        inout.writeCoordinatesToFile(outputFileName, coordinates);

    }

}
