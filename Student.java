import java.util.ArrayList;
import java.util.Scanner;

////Most methods correspond with the menu. See interface for details
public class Student extends User implements StudentInterface{
    private static final long serialVersionUID = 1L;
    private ArrayList<Course> courseOfEachStu;

    Student(String username, String password, String firstName, String lastName){
        super(username, password, firstName, lastName);
        this.courseOfEachStu = new ArrayList<Course>();
    }

    @Override
    public void viewAllCourses(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        Object[] title = { "Course Name", "id", "Instructor", "Sec", "Location" };
        System.out.printf("%-50s%-15s%-27s%-5s%-10s%n", title);
        for (int i = 0; i < allCourse.size(); i++) {
            Object[] stuView = {allCourse.get(i).getName(), allCourse.get(i).getId(),
                    allCourse.get(i).getInstructor(),Integer.toString(allCourse.get(i).getSection()), 
                    allCourse.get(i).getLocation()};
            System.out.printf("%-50s%-15s%-27s%-5s%-10s%n", stuView);
        }
            returnToMenu(allCourse, allStudents);
    }

    public void viewNotFull(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        System.out.println("Displaying open courses\n");
        Object[] title = { "Course Name", "id", "Instructor", "Section", "Location" };
        System.out.printf("%-50s%-15s%-27s%-5s%-10s%n", title);
        for(int i = 0; i < allCourse.size(); i++) {
            if (allCourse.get(i).getCurrentStu() < allCourse.get(i).getMaxStu()) {
                Object[] stuView = {allCourse.get(i).getName(), allCourse.get(i).getId(),
                    allCourse.get(i).getInstructor(),Integer.toString(allCourse.get(i).getSection()), 
                    allCourse.get(i).getLocation()};
                System.out.printf("%-50s%-15s%-27s%-5s%-10s%n", stuView);
            }
        }
        returnToMenu(allCourse, allStudents);
    }

    public void register(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        boolean alreadyEnrolled = false;
        boolean courseExists = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter course name: ");
        String courseName = s.nextLine();
        System.out.println("Enter course section: ");
        int section = s.nextInt();
        s.nextLine();

        //getting name is kinda pointless but it's in the requirement
        System.out.println("Enter your full name. If you have middle name, hyphenate after first name (e.g. Chris-William Jackson):");
        String fullName = s.nextLine();
        String[] name = fullName.split(" ");
        String first = name[0];
        String last = name[1];
        setFirstName(first);
        setLastName(last);
        for(int i = 0; i < allCourse.size(); i++){
            if(allCourse.get(i).getName().equals(courseName) && allCourse.get(i).getSection() == section){ //if course exists
                courseExists = true;
                for(int l = 0; l < this.courseOfEachStu.size(); l++){
                    if(allCourse.get(i).equals(this.courseOfEachStu.get(l))){
                        alreadyEnrolled = true;
                    }
                }
                if(alreadyEnrolled == false){
                    this.courseOfEachStu.add(allCourse.get(i)); //add to course of student
                    for(int j = 0; j < allStudents.size(); j++){
                        if(allStudents.get(j).getFirstName().equals(this.getFirstName()) && allStudents.get(j).getLastName().equals(this.getLastName())){
                            allCourse.get(i).getStuInCourse().add(allStudents.get(j));//add to student of course
                            allCourse.get(i).incrementCurrent();//current
                            System.out.println("\nEnrolling in course..."); //just some cool effects
                            try {
                                Thread.sleep(800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Enrolled successfully");
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else{
                    System.out.println("Error: You are already enrolled");
                }
            }
        }
        if(courseExists == false){
            System.out.println("Course not found. Try again.");
        }
        returnToMenu(allCourse, allStudents);
    }

    public void withdraw(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        boolean iHaveCourse = false;
        boolean courseExists = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter course name: ");
        String courseName = s.nextLine();
        System.out.println("Enter course section: ");
        int section = s.nextInt();
        s.nextLine();

        //getting name is kinda pointless but it's in the requirement
        System.out.println("Enter your full name. If you have middle name, hyphenate after first name (e.g. Chris-William Jackson):");
        String fullName = s.nextLine();
        String[] name = fullName.split(" ");
        String first = name[0];
        String last = name[1];
        setFirstName(first);
        setLastName(last);

        for(int i = 0; i < allCourse.size(); i++){
            if(allCourse.get(i).getName().equals(courseName) && allCourse.get(i).getSection() == section){ //if course exists
                courseExists = true;
                for(int k = 0; k < courseOfEachStu.size(); k++){//and if I have course
                    if(courseOfEachStu.get(k).equals(allCourse.get(i))){
                        iHaveCourse = true;
                        courseOfEachStu.remove(k);//remove course from student
                        for(int j = 0; j < allStudents.size(); j++){  //find student
                            if(allStudents.get(j).getFirstName().equals(this.getFirstName()) && allStudents.get(j).getLastName().equals(this.getLastName())){
                                allCourse.get(i).getStuInCourse().remove(allStudents.get(j));//remove student from course
                                allCourse.get(i).decrementCurrent();
                                System.out.println("\nWithdrawing..."); //just some cool effects
                                try {
                                    Thread.sleep(800);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Withdrew sucessfully");
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        if(courseExists == false){
            System.out.println("Course not found. Try again.");
        }
        if(iHaveCourse == false){
            System.out.println("Error: You were not enrolled in the course");
        }
        returnToMenu(allCourse, allStudents);
    }

    @Override
    public void viewCourseOfStu(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        Object[] title = { "Course Name", "Instructor", "Section", "Location" };
        System.out.printf("%-45s%-25s%-20s%-15s%n", title);
        for(int i = 0; i < courseOfEachStu.size(); i++){
            Object[] enrolled = {courseOfEachStu.get(i).getName(), courseOfEachStu.get(i).getInstructor(),
                                Integer.toString(courseOfEachStu.get(i).getSection()), courseOfEachStu.get(i).getLocation() };
            System.out.format("%-45s%-25s%-20s%-15s%n", enrolled);
        }
        returnToMenu(allCourse, allStudents);
    }

    @Override
    public void exit(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        printSer();
        serializeCourse(allCourse);
        serializeStu(allStudents);
        Login.serializeAdmin();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nSerialization successful");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printLogout();
        System.exit(0);

    }

    @Override
    public void menuSelection(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        printStuWelcome();
        printStuMenu();
        Scanner s = new Scanner(System.in);
        System.out.println("Enter number: ");
        int num = s.nextInt();
        s.nextLine();
        if(num == 1){
            viewAllCourses(allCourse, allStudents);
        }
        else if(num == 2){
            viewNotFull(allCourse, allStudents);
        }
        else if(num == 3){
            register(allCourse, allStudents);
        }
        else if(num == 4){
            withdraw(allCourse, allStudents);
        }
        else if(num == 5){
            viewCourseOfStu(allCourse, allStudents);
        }
        else if(num == 6){
            exit(allCourse, allStudents);
        }    
    }
    
    public void returnToMenu(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        System.out.println("\n" + "Returning to menu..." + "\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menuSelection(allCourse, allStudents);
    }

    public void printSer(){
        System.out.println("\n");
        String[] ser = {"S","e","r","i","a","l","i","z","i","n","g"," ","c","h","a","n","g","e","s",".",".","."};
        for(String letter : ser){  
            System.out.print(letter);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }  
    }

    public void printLogout(){
        String[] out = {"L","o","g","g","i","n","g"," ","o","u","t",".",".","."};
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

    public void printStuWelcome(){
        System.out.println("    Welcome, " + this.getFirstName() + "\n" + 
                           "==========================" + "\n");
    }

    public void printStuMenu(){
        System.out.println("   -----------------" + "\n" + 
                           "  |Course Management|" + "\n" + 
                           "   -----------------" + "\n" + 
                           "1. View all courses." + "\n" +
                           "2. View courses not full." + "\n" +
                           "3. Register in a course." + "\n" +
                           "4. Withdraw from a course." + "\n" +
                           "5. View registered courses." + "\n" + 
                           "6. Exit.");
    }

    public ArrayList<Course> getCourseOfEachStu() {
        return this.courseOfEachStu;
    }

    public void setCourseOfEachStu(ArrayList<Course> eachStuCourse) {
        this.courseOfEachStu = eachStuCourse;
    }
    
}