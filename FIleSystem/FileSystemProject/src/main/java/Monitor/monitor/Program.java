package Monitor.monitor;

import Structure.FileSystemStructure.FileSystem;
import java.util.function.Consumer;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

    static final Monitor monitor = new Monitor(new FileSystem(0, "",
            "", 0, 0, 0));

    public static int intInput(){
        int rez;
        String mistake = "";
        Scanner sc = new Scanner(System.in);
        do {
            try {
                rez = sc.nextInt();
                break;
            } catch (InputMismatchException nfe) {
                mistake = sc.nextLine();
            }
            System.out.print("Некорректный ввод " + mistake +
                    ", ожидается целое число, пожалуйста, повторите: ");
        } while(true);
        return rez;
    }

    private static void doWithQuestionAboutSave(Consumer<Monitor> func){
        Scanner sc = new Scanner(System.in);
        System.out.print("Вы хотите сохранить текущую файловую систему?(д/н): ");
        String yn;
        while (true) {
            yn = sc.next();
            if (yn.equals("y") || yn.equals("д")) {
                monitor.save();
                func.accept(monitor);
                break;
            }

            if (yn.equals("n") || yn.equals("н")) {
                func.accept(monitor);
                break;
            }
            System.out.print("Некорректный ввод, пожалуйста, повторите: ");
        }
    }

    public static void init(){
        System.out.println("Вас приветствует команда С17-501");
        System.out.print("1 - Загрузить файловую систему из файла\n" +
                "2 - Создать новую файловую систему\nВаш выбор: ");
        int choice = 0;
        boolean check = true;
        while (choice < 1 || choice > 2) {
            if (!check) {
                System.out.print("Некорректный ввод " + choice + ", пожалуйста, повторите: ");
            }
            choice = intInput();
            check = false;
        }

        if (choice == 1) {
            monitor.download();
        } else {
            monitor.create();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        boolean needSave = false;
        init();
        while (true) {
            System.out.print(monitor.fs.ownerName + "> ");
            String command = sc.nextLine();

            if (command.trim().equals("exit")) {
                if (needSave) {
                    doWithQuestionAboutSave((monitor) -> {
                    });
                }
                break;
            }

            if (command.trim().equals("create")) {
                if (needSave) {
                    doWithQuestionAboutSave(Monitor::create);
                }else{
                    monitor.create();
                }
                check = true;
                needSave = true;
            }

            if (command.trim().equals("download")) {
                if (needSave) {
                    doWithQuestionAboutSave(Monitor::download);
                }else{
                    monitor.download();
                }
                check = true;
                needSave = false;
            }

            if (command.trim().equals("help")) {
                monitor.help();
                check = true;
            }

            if (command.trim().equals("create file")) {
                monitor.file();
                check = true;
                needSave = true;
            }

            if (command.trim().equals("delete")) {
                monitor.delete();
                check = true;
                needSave = true;
            }

            if (command.trim().equals("save")) {
                monitor.save();
                check = true;
                needSave = false;
            }

            if (command.trim().equals("add")) {
                monitor.add();
                check = true;
                needSave = true;
            }

            if (command.trim().equals("title")) {
                monitor.title();
                check = true;
            }

            if (command.trim().equals("title in order")) {
                monitor.title_in_order();
                check = true;
            }

            if (command.trim().equals("defragmentation")) {
                monitor.defragmentation();
                check = true;
                needSave = true;
            }

            if (command.trim().equals("fragmentation")) {
                monitor.fragmentation();
                check = true;
            }

            if (command.trim().equals("info")){
                System.out.println(monitor.fs);
                check = true;
            }

            if (!check) {
                System.out.println("Не найдена команда " + command + ". Пожалуйста, повторите ввод.\n" +
                        "Вся информация о командах доступна по команде help.");
            }

            check = false;
        }
    }
}