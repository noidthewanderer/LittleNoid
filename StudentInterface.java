import java.util.ArrayList;
import java.util.Scanner;

public interface StudentInterface {
    //Course Management
    public void viewAllCourses(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User
    public void viewNotFull(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void register(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void withdraw(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void viewCourseOfStu(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User
    public void exit(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User

    //The following methods allow user to perform multiple tasks within one login.
    public void menuSelection(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User
    public void returnToMenu(ArrayList<Course> allCourse, ArrayList<Student> allStudents);

    //The following methods are just for cool visual effects.
    public void printSer();
    public void printLogout();
    public void printStuWelcome();
    public void printStuMenu();

    //getters and setters
    public ArrayList<Course> getCourseOfEachStu();
    public void setCourseOfEachStu(ArrayList<Course> eachStuCourse) ;
}