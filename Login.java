import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

//everything static, class is just for initialization, deserialization and login
public class Login {
    static boolean isAdmin; // t = admin f = student
    static boolean adminLoginSuccess;
    static boolean stuLoginSuccess;
    static boolean csvCalled;
    static int stuIndex;
    static boolean adminRegistered;
    static Admin admin;

    public static void serializeAdmin(){
        try {
            FileOutputStream fos = new FileOutputStream("Admin.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(admin);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserializeAdmin(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        try {
            FileInputStream fis = new FileInputStream("Admin.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            admin = (Admin)ois.readObject();
            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            adminRegister(allCourse, allStudents);
            login(allCourse, allStudents);
        } catch (IOException e) {
            System.out.println("Error IO");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readCSV(ArrayList<Course> allCourse) throws FileNotFoundException { // initialize allCourses //
                                                                                           // // ArrayList
        String fileName = "MyUniversityCourses.csv";
        Scanner s;
        s = new Scanner(new File(fileName));
        String courseInfo = "";
        courseInfo = s.nextLine();// this skips first line of titles ("Course Name", etc)
        while (s.hasNextLine()) {
            courseInfo = s.nextLine(); // available to construct courses
            String[] cell = courseInfo.split(",");
            // constructor: name, id, max, current, instructor, section, location
            Course c = new Course(cell[0], cell[1], Integer.parseInt(cell[2]), cell[5], Integer.parseInt(cell[6]), cell[7]);
            allCourse.add(c);
        }
    }

    public static void deserializeCourse(ArrayList<Course> allCourse) {
        ArrayList<Course> courseHolder = new ArrayList<Course>();
        try {
            FileInputStream fisCourses = new FileInputStream("Courses.ser");
            ObjectInputStream oisCourses = new ObjectInputStream(fisCourses);
            courseHolder = (ArrayList) oisCourses.readObject();
            fisCourses.close();
            oisCourses.close();
            allCourse.clear();
            for (int i = 0; i < courseHolder.size(); i++) {
                allCourse.add(courseHolder.get(i));
            }
        } catch (FileNotFoundException e) {
            try {
                readCSV(allCourse);
            } catch (FileNotFoundException e1) {
                System.out.println("File \"MyUniversityCourses.csv\" not found");
            }
        } catch (IOException e) {
            System.out.println("Error IO");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static void deserializeStudent(ArrayList<Student> allStudents) {
        ArrayList<Student> studentHolder = new ArrayList<Student>();
        try {
            FileInputStream fisStu = new FileInputStream("Students.ser");
            ObjectInputStream oisStu = new ObjectInputStream(fisStu);
            studentHolder = (ArrayList) oisStu.readObject();
            oisStu.close();
        } catch (FileNotFoundException e) {
            // do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        allStudents.clear();
        for (int i = 0; i < studentHolder.size(); i++) {
            allStudents.add(studentHolder.get(i));
        }
    }

    public static void enterSystem(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        if(isAdmin && adminLoginSuccess){
            adminLoginSuccess = false;
            admin.menuSelection(allCourse, allStudents);
        }
        else if(isAdmin == false && stuLoginSuccess){
            stuLoginSuccess = false;
            allStudents.get(stuIndex).menuSelection(allCourse, allStudents);
        }
    }

    public static void adminRegister(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        System.out.println("\nWelcome to the Course Registration System.");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("System detects that there is no admin registered.");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Please register as admin first.");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Scanner s = new Scanner(System.in);
        System.out.println("\nEnter your first name:");
        String firstName = s.nextLine();
        System.out.println("Enter your last name:");
        String lastName = s.nextLine();
        admin = new Admin("Admin", "Admin001", firstName, lastName);
        System.out.println("Admin registered.");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nPlease note your account information:" + "\nusername: Admin" + "\npassword: Admin001");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("You may now log in.");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void login(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        Scanner s = new Scanner(System.in);
        String userType;
        String userName;
        String pw;
        System.out.println("\nWelcome to CRS. Do you want to log in as Admin or Student?");
        userType = s.nextLine();
        if (userType.equals("Admin") || userType.equals("admin")) {
            isAdmin = true;
            System.out.println("Hello Admin. Please enter your username: ");
            userName = s.nextLine();
            System.out.println("Please enter your password:");
            pw = s.nextLine();
            if (userName.equals("Admin") && pw.equals("Admin001")) {
                logInEffects();
                adminLoginSuccess = true;
            }
            else{
                System.out.println("Wrong password. Try again.");
                adminLoginSuccess = false;
            }
        } else if (userType.equals("Student") || userType.equals("student")) {
            isAdmin = false; 
            System.out.println("Hello Student. Please Enter your username: ");
            userName = s.nextLine();
            System.out.println("Please enter your password:");
            pw = s.nextLine();
            for (int i = 0; i < allStudents.size(); i++) {
                if (userName.equals(allStudents.get(i).getUsername()) && pw.equals(allStudents.get(i).getPassword())) {
                    logInEffects();
                    stuLoginSuccess = true;
                    stuIndex = i;
                }
            }
            if(stuLoginSuccess == false){
                System.out.println("Wrong username or password. Pleasr try again.");
            }
        }
        else{
            System.out.println("Invalid input. Try again.");
        }
        enterSystem(allCourse, allStudents);
    }

    public static void logInEffects(){
        printLogIn();
        printSuccess();
        printLoading();
    }

    public static void printSuccess(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nLogin Successful");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printLogIn(){
        String[] out = {"L","o","g","g","i","n","g"," ","i","n",".",".","."};
        System.out.println("\n");
        for(String letter : out){  
            System.out.print(letter);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }  
    }

    public static void printLoading(){
        String[] out = {"L","o","a","d","i","n","g"," ","m","e","n","u",".",".","."};
        for(String letter : out){  
            System.out.print(letter);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }  
        System.out.println("\n");
    }

    public static void main(String[] args) {
        ArrayList<Course> allCourse = new ArrayList<Course>(); // serialized in exit()
        ArrayList<Student> allStudents = new ArrayList<Student>(); // serialized in exit()

        deserializeCourse(allCourse);
        deserializeStudent(allStudents);
        deserializeAdmin(allCourse, allStudents);//for additional features

        login(allCourse, allStudents);
    }
}