import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    User(String username, String password, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    User(){}

    public abstract void viewAllCourses(ArrayList<Course> allCourse, ArrayList<Student> allStudents);

    public abstract void exit(ArrayList<Course> allCourse, ArrayList<Student> allStudents);

    public abstract void menuSelection(ArrayList<Course> allCourse, ArrayList<Student> allStudents);

    public abstract void viewCourseOfStu(ArrayList<Course> allCourse, ArrayList<Student> allStudents);
    
    public void serializeCourse(ArrayList<Course> allCourse){
        try {
            FileOutputStream fosCourses = new FileOutputStream("Courses.ser");
            ObjectOutputStream oosCourses = new ObjectOutputStream(fosCourses);
            oosCourses.writeObject(allCourse);
            fosCourses.close();
            oosCourses.close();
        } catch (FileNotFoundException e1) {
            System.out.println("Error: FileNotFoundException");
            e1.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: IOE");
            e.printStackTrace();
        }
    }

    public void serializeStu(ArrayList<Student> allStudents){
        try {
            FileOutputStream fosStu = new FileOutputStream("Students.ser");
            ObjectOutputStream oosStu = new ObjectOutputStream(fosStu);
            oosStu.writeObject(allStudents);
            fosStu.close();
            oosStu.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}