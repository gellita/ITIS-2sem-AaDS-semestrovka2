package RBTree;



import java.io.*;
import java.util.Scanner;

public class FileTextDeleter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите 1,2,3,4,5 для удаления данных input, outputAdd, outputContains, outputDelete или удаление данных из всех файлов соответственно, Босс");
        int num = sc.nextInt();
        if (num == 1){
            try {
                File file = new File("input.txt");
                FileWriter writer = new FileWriter(file, false);
                writer.close();
                System.out.println("Файл почищен, Босс");
            } catch (IOException e) {
                System.out.println("Такого файла нет, Босс" + e.getMessage());
            }
        }
        if (num == 2){
            try {
                File file = new File("outputAdd.txt");
                FileWriter writer = new FileWriter(file, false);
                writer.close();
                System.out.println("Файл почищен, Босс");
            } catch (IOException e) {
                System.out.println("Такого файла нет, Босс" + e.getMessage());
            }
        }
        if (num == 3){
            try {
                File file = new File("outputContains.txt");
                FileWriter writer = new FileWriter(file, false);
                writer.close();
                System.out.println("Файл почищен, Босс");
            } catch (IOException e) {
                System.out.println("Такого файла нет, Босс" + e.getMessage());
            }
        }
        if (num == 4){
            try {
                File file = new File("outputDelete.txt");
                FileWriter writer = new FileWriter(file, false);
                writer.close();
                System.out.println("Файл почищен, Босс");
            } catch (IOException e) {
                System.out.println("Такого файла нет, Босс" + e.getMessage());
            }
        }
        if (num == 5){
            try {
                File file = new File("input.txt");
                FileWriter writer = new FileWriter(file, false);
                File file1 = new File("outputAdd.txt");
                FileWriter writer1 = new FileWriter(file1, false);
                File file2 = new File("outputContains.txt");
                FileWriter writer2 = new FileWriter(file2, false);
                File file3 = new File("outputDelete.txt");
                FileWriter writer3 = new FileWriter(file3, false);
                writer.close();
                writer2.close();
                writer1.close();
                writer3.close();
                System.out.println("Файлы почищены, Босс");
            } catch (IOException e) {
                System.out.println("Таких файлов нет, Босс" + e.getMessage());
            }
        }
    }
}
