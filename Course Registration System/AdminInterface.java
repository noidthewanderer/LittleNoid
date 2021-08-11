import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public interface AdminInterface {
    //Course Management
    public void createCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void deleteCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void editCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void displayInfo(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void registerStu(ArrayList<Course> allCourse, ArrayList<Student> allStudents);

    //Reports
    public void viewAllCourses(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User
    public void viewFull(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public ArrayList<String> makeFullList(ArrayList<Course> allCourse);
    public void writeFullToFile(ArrayList<Course> allCourse, ArrayList<Student> allStudents) throws IOException;
    public void viewStuInCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void viewCourseOfStu(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User
    public void sortCourses(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    public void exit(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User
    
    //The following methods allow user to perform multiple tasks within one login.
    public void menuSelection(ArrayList<Course> allCourse, ArrayList<Student> allStudents);//override User
    public void returnToMenu(ArrayList<Course> allCourse, ArrayList<Student> allStudents);

    //The following methods are just for cool visual effects.
    public void printSer();
    public void printLogout();
    public void printAdminWelcome();
    public void printCourseManagement();
    public void printReports();
}