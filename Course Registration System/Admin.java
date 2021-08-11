import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

//Most methods correspond with the menu. See interface for details
public class Admin extends User implements AdminInterface{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Admin(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    public void createCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        boolean courseExists = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter course name: ");
        String name = s.nextLine();
        System.out.println("Enter course id: ");
        String id = s.nextLine();
        System.out.println("Enter maximum number of students: ");
        int max = s.nextInt();
        s.nextLine();
        System.out.println("Enter instructor: ");
        String instructor = s.nextLine();
        System.out.println("Enter section number: ");
        int section = s.nextInt();
        s.nextLine();
        System.out.println("Enter location: ");
        String location = s.nextLine();
        Course c = new Course(name, id, max, instructor, section, location);
        for(int i = 0; i < allCourse.size(); i++){ //test for duplicates
            if(allCourse.get(i).equals(c)){
                courseExists = true;
            }
        }
        if(courseExists){
            System.out.println("Error: Course already exists");
        }else{
            allCourse.add(c);
            System.out.println("\nCreating course..."); //just some cool effects
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Course created");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        returnToMenu(allCourse, allStudents);
    }

    public void deleteCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        boolean stuInCourse = false;
        boolean courseExists = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter course name: ");
        String name = s.nextLine();
        System.out.println("Enter course section: ");
        int section = s.nextInt();
        s.nextLine();
        for (int i = 0; i < allCourse.size(); i++) {
            if (name.equals(allCourse.get(i).getName()) && section == (allCourse.get(i).getSection())){//if course exists
                courseExists = true;
                for(int j = 0; j< allStudents.size(); j++){
                    for(int k = 0; k < allStudents.get(j).getCourseOfEachStu().size(); k++){ //for each course of each student
                        if(allStudents.get(j).getCourseOfEachStu().get(k).getName().equals(allCourse.get(i).getName()) 
                            && allStudents.get(j).getCourseOfEachStu().get(k).getSection() == allCourse.get(i).getSection()){//if student has course
                            allStudents.get(j).getCourseOfEachStu().remove(k);
                            stuInCourse = true;
                        }
                    }
                }
                allCourse.remove(allCourse.get(i));//delete
            }
        }
        if(stuInCourse){
            System.out.println("Note: There were students enrolled in course. Course has been removed from their list.");
        }
        if(courseExists == false){
            System.out.println("Error: Course not found");
        }else{
            System.out.println("\nDeleting course...");
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Course deleted");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        returnToMenu(allCourse, allStudents);
    }

    public void editCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        boolean courseExists = false;
        Scanner s = new Scanner(System.in);
        String input;
        int num;
        System.out.println("Enter course name: ");
        String name = s.nextLine();
        System.out.println("Enter course section: ");
        int section = s.nextInt();
        s.nextLine();
        for (int i = 0; i < allCourse.size(); i++) {
            if (name.equals(allCourse.get(i).getName()) && section == (allCourse.get(i).getSection())){
                courseExists = true;
                System.out.println("What would you like to edit? You may change" + "\n"
                        + "1. Maxinum number of students" + "\n" + "2. Number of current students" + "\n"
                        + "3. Enrolled students in the course" + "\n" + "4. Course instructor" + "\n"
                        + "5. Course section" + "\n" + "6. Course location" + "\n" + "Enter number: ");
                num = s.nextInt();
                s.nextLine();
                if (num == 1) {
                    System.out.println("Enter the new maximun: ");
                    num = s.nextInt();
                    s.nextLine();
                    if(num >= allCourse.get(i).getStuInCourse().size()){
                        allCourse.get(i).setMaxStu(num);
                    }else{
                        System.out.println("Error: Action not allowed. Your input is smaller than actual number of students");
                    }
                
                } else if (num == 2) {
                    System.out.println("*Warning*: Change with caution. System may not track the current number of students accurately after it");
                    System.out.println("Enter updated number of current students:");
                    num = s.nextInt();
                    s.nextLine();
                    allCourse.get(i).setCurrentStu(num);
                    if(allCourse.get(i).getStuInCourse().size() < num){
                        allCourse.get(i).setCurrentStu(num);
                        System.out.println("Note: Actual number of students remains smaller than current number");//can still make changes, just a note
                    }
                    else if(allCourse.get(i).getStuInCourse().size() > num){
                        allCourse.get(i).setCurrentStu(num);
                        System.out.println("Note: Actual number of students remains greater than current number");//can still make changes, just a note
                    }
                    if(num == allCourse.get(i).getMaxStu()){
                        allCourse.get(i).setCurrentStu(num);
                        System.out.println("Course is set to full");
                    }
                    else if(num > allCourse.get(i).getMaxStu()){
                        System.out.println("Error: Action not allowed. Current would exceed max");
                    }
                } else if (num == 3) { //this edits ArrayList<Student> of students
                    System.out.println("Do you want to :\n" + "1. Enroll a student\n" + "2. Remove a student\n" + "Enter number:");
                    num = s.nextInt();
                    s.nextLine();
                    if(num == 1){
                        boolean stuExists = false;
                        String firstName;
                        String lastName;
                        System.out.println("Enter student first name:");
                        firstName = s.nextLine();
                        System.out.println("Enter student last name:");
                        lastName = s.nextLine();
                        for(int j = 0; j < allStudents.size(); j++){
                            if(allStudents.get(j).getFirstName().equals(firstName) && allStudents.get(j).getLastName().equals(lastName)){
                                stuExists = true;
                                allCourse.get(i).getStuInCourse().add(allStudents.get(j));//add to student of course
                                allCourse.get(i).incrementCurrent();//current++
                                allStudents.get(j).getCourseOfEachStu().add(allCourse.get(i));//add to course of student
                            }
                        }
                        if(stuExists == false){
                            System.out.println("Error: Student account not registered");
                        }
                    }
                    else if(num == 2){
                        boolean stuExists = false;
                        boolean stuInCourse = false;
                        System.out.println("Enter student first name:");
                        String firstName = s.nextLine();
                        System.out.println("Enter student last name:");
                        String lastName = s.nextLine();
                        for(int j = 0; j < allStudents.size(); j++){
                            if(allStudents.get(j).getFirstName().equals(firstName) && allStudents.get(j).getLastName().equals(lastName)){
                                stuExists = true;
                                for(int k = 0; k < allCourse.get(i).getStuInCourse().size(); k++){
                                    if(allCourse.get(i).getStuInCourse().get(k).equals(allStudents.get(j))){ //if stu given = stu in course
                                        stuInCourse = true;
                                        allCourse.get(i).getStuInCourse().remove(k);//remove student from course
                                        allCourse.get(i).decrementCurrent();//change current
                                        allStudents.get(j).getCourseOfEachStu().remove(allCourse.get(i));//remove course from student
                                    }
                                }
                                if(stuInCourse == false){
                                    System.out.println("Error: Student is not enrolled in this course");
                                }
                            }
                        }
                        if(stuExists == false){
                            System.out.println("Error: Student account not registered");
                        }
                    }  
                    else{
                        System.out.println("Invalid input: Must enter a number");
                    }                
                } else if (num == 4) {
                    System.out.println("Enter the name of the new instructor: ");
                    input = s.nextLine();
                    allCourse.get(i).setInstructor(input);
                } else if (num == 5) {
                    System.out.println("Enter the new section: ");
                    num = s.nextInt();
                    s.nextLine();
                    allCourse.get(i).setSection(num);
                } else if (num == 6) {
                    System.out.println("Enter the new location:");
                    input = s.nextLine();
                    allCourse.get(i).setLocation(input);
                }
                else{
                    System.out.println("Invalid input: Must enter a number");
                }
            }
        }
        if(courseExists == false){
            System.out.println("Error: Course not found");
        }else{
            System.out.println("\nSaving changes...");
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Changes Saved");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        returnToMenu(allCourse, allStudents);
    }

    public void displayInfo(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        //Note: only searches by course id, will show multiple courses under the same id as said in requirement
        //student list not printed here since it can be viewed in another method
        //also it doesn't fit; table has length limit
        boolean idExists = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter course id: ");
        String id = s.nextLine();
        Object[] title = {"Name", "id", "Max", "Current", "Instructor", "Sec", "Location"};
        System.out.printf("%-45s%-15s%-5s%-8s%-25s%-4s%-10s%n", title);
        for (int i = 0; i < allCourse.size(); i++) {
            if (id.equals(allCourse.get(i).getId())){
                idExists = true;
                Object[] display = {allCourse.get(i).getName(), allCourse.get(i).getId(), 
                    Integer.toString(allCourse.get(i).getMaxStu()), Integer.toString(allCourse.get(i).getCurrentStu()),
                    allCourse.get(i).getInstructor(), allCourse.get(i).getSection(), allCourse.get(i).getLocation()};
                System.out.printf("%-45s%-15s%-5s%-8s%-25s%-4s%-10s%n", display);
            }
        }
        if(idExists == false){
            System.out.println("Error: Course id not found");
        }
        returnToMenu(allCourse, allStudents);
    }

    public void registerStu(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        boolean stuExists = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter student username: ");
        String username = s.nextLine();
        System.out.println("Enter student password: ");
        String password = s.nextLine();
        System.out.println("Enter student first name: ");
        String firstName = s.nextLine();
        System.out.println("Enter student last name: ");
        String lastName = s.nextLine();
        Student stu = new Student(username, password, firstName, lastName);
        for(int i = 0; i < allStudents.size(); i++){ //test duplicates
            if(allStudents.get(i).equals(stu)){
                stuExists = true;
            }
        }
        if(stuExists){
            System.out.println("Error: Account already exists in system");
        }else{
            allStudents.add(stu);
            System.out.println("\nRegistering...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Student registered");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        returnToMenu(allCourse, allStudents);
    }

    @Override
    public void viewAllCourses(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        Object[] title = { "Course Name", "Course id", "Current number", "Max number" };
        System.out.printf("%-50s%-25s%-20s%-20s%n", title);
        for (int i = 0; i < allCourse.size(); i++) {
            Object[] adminView = {allCourse.get(i).getName(), allCourse.get(i).getId(),
                    Integer.toString(allCourse.get(i).getCurrentStu()),
                    Integer.toString(allCourse.get(i).getMaxStu()) };
            System.out.format("%-50s%-25s%-20s%-20s%n", adminView);
        }
        returnToMenu(allCourse, allStudents);
    }

    public void viewFull(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        boolean allOpen = true;
        for (int i = 0; i < allCourse.size(); i++) {
            if (allCourse.get(i).getCurrentStu() == allCourse.get(i).getMaxStu()) {
                allOpen = false;
            }
        }
        if(allOpen){
            System.out.println("There are currently no full courses.");
        }else{
            Object[] title = { "Course Name", "Course id", "Current number", "Max number" };
            System.out.printf("%-50s%-25s%-20s%-20s%n", title);
            for (int i = 0; i < allCourse.size(); i++) {
                if (allCourse.get(i).getCurrentStu() >= allCourse.get(i).getMaxStu()) {
                    allOpen = false;
                    Object[] fullView = {allCourse.get(i).getName(), allCourse.get(i).getId(),
                        Integer.toString(allCourse.get(i).getCurrentStu()),
                        Integer.toString(allCourse.get(i).getMaxStu()) };
                        System.out.format("%-50s%-25s%-20s%-20s%n", fullView);
                }
            }
        }
        returnToMenu(allCourse, allStudents);
    }

    public ArrayList<String> makeFullList(ArrayList<Course> allCourse) {
        ArrayList<String> full = new ArrayList<String>();
        for (int i = 0; i < allCourse.size(); i++) {
            if (allCourse.get(i).getCurrentStu() >= allCourse.get(i).getMaxStu()) {
                Object[] fullTemp = {allCourse.get(i).getName(), allCourse.get(i).getId(),
                    Integer.toString(allCourse.get(i).getCurrentStu()),
                    Integer.toString(allCourse.get(i).getMaxStu()) };
                full.add(String.format("%-50s%-25s%-20s%-20s%n", fullTemp));
            }
        }
        return full;
    }

    public void writeFullToFile(ArrayList<Course> allCourse, ArrayList<Student> allStudents) throws IOException {
        ArrayList<String> full = makeFullList(allCourse);
        File fullFile = new File("Full Courses.txt");
        fullFile.createNewFile();
        FileWriter w = new FileWriter("Full Courses.txt");
        Object[] title = { "Course Name", "Course id", "Current number", "Max number" };
        w.write(String.format("%-50s%-25s%-20s%-20s%n", title));
        for (int i = 0; i < full.size(); i++) {
            w.write(full.get(i));
        }
        w.close();
        System.out.println("\nWriting to file..."); //just some cool effects
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Written to file \"Full_Courses.txt\" successfully");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        returnToMenu(allCourse, allStudents);
    }

    public void viewStuInCourse(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        boolean courseExists = false;
        boolean courseHasStudent = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter course name: ");
        String name = s.nextLine();
        System.out.println("Enter course section: ");
        int section = s.nextInt();
        s.nextLine();
        for (int i = 0; i < allCourse.size(); i++) {
            if(name.equals(allCourse.get(i).getName()) && section == (allCourse.get(i).getSection())){
                courseExists = true;
                if(allCourse.get(i).getStuInCourse().size() > 0){
                    courseHasStudent = true;
                    for(int j = 0; j < allCourse.get(i).getStuInCourse().size(); j++){
                        System.out.println(allCourse.get(i).getStuInCourse().get(j).getFirstName() + 
                                    " " + allCourse.get(i).getStuInCourse().get(j).getLastName());
                    }
                }
            }
        }
        if(courseHasStudent == false){
            System.out.println("There is currently not student enrolled");
        }
        if(courseExists == false){
            System.out.println("Error: Course not found");
        }
        returnToMenu(allCourse, allStudents);
    }

    @Override
    public void viewCourseOfStu(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        boolean stuExists = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter student first name: ");
        String firstName = s.nextLine();
        System.out.println("Enter student last name: ");
        String lastName = s.nextLine();
        for (int i = 0; i < allStudents.size(); i++) {
            if (allStudents.get(i).getFirstName().equals(firstName)
                    && allStudents.get(i).getLastName().equals(lastName)) {
                stuExists = true;
                if (allStudents.get(i).getCourseOfEachStu().size() == 0) {
                    System.out.println("Student is currently not registered in any course");
                } else {
                    for (int j = 0; j < allStudents.get(i).getCourseOfEachStu().size(); j++) {
                        System.out.println(allStudents.get(i).getCourseOfEachStu().get(j).getName());
                    }
                }
            }
        }
        if(stuExists == false){
            System.out.println("Student not found");
        }
        returnToMenu(allCourse, allStudents);
    }

    public void sortCourses(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        Collections.sort(allCourse, Course.sortByCurrent);
        viewAllCourses(allCourse, allStudents);
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
    public void menuSelection(ArrayList<Course> allCourse, ArrayList<Student> allStudents) {
        printAdminWelcome();
        System.out.println("Enter 1 for Course Management, 2 for Reports:");
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        s.nextLine();
        if(num == 1){
            printCourseManagement();
            System.out.println("What would you like to do? Enter the number: ");
            num = s.nextInt();
            s.nextLine();
            if(num == 1){
                createCourse(allCourse, allStudents);
            }
            else if(num == 2){
                deleteCourse(allCourse, allStudents);
            }
            else if(num == 3){
                editCourse(allCourse, allStudents);
            }
            else if(num == 4){
                displayInfo(allCourse, allStudents);
            }
            else if(num == 5){
                registerStu(allCourse, allStudents);
            }
            else if(num == 6){
                exit(allCourse, allStudents);
            }
        }   
        else if(num == 2){
            printReports();
            System.out.println("What would you like to do? Enter the number:");
            num = s.nextInt();
            s.nextLine();
            if(num == 1){
                viewAllCourses(allCourse, allStudents);
            }
            else if(num == 2){
                viewFull(allCourse, allStudents);
            }
            else if(num == 3){
                try {
                    writeFullToFile(allCourse, allStudents);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(num == 4){
                viewStuInCourse(allCourse, allStudents);
            }
            else if(num == 5){
                viewCourseOfStu(allCourse, allStudents);
            }
            else if(num == 6){
                sortCourses(allCourse, allStudents);
            }
            else if(num == 7){
                exit(allCourse, allStudents);
            }
        }
    }

    public void returnToMenu(ArrayList<Course> allCourse, ArrayList<Student> allStudents){
        System.out.println("Returning to menu..." + "\n");
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

    public void printAdminWelcome(){
        System.out.println("            Welcome, " + Login.admin.getFirstName() +"\n" +
                           "=======================================" + "\n" +
                           " ------------------            -------" + "\n" +
                           "| Course Management|          |Reports|" + "\n" +
                           " ------------------            -------" + "\n" +
                           "=======================================" + "\n");
    }

    public void printCourseManagement(){
        System.out.println("   -----------------" + "\n" + 
                           "  |Course Management|" + "\n" + 
                           "   -----------------" + "\n" + 
                           "1. Create a new course" + "\n" + 
                           "2. Delete a course" + "\n" +
                           "3. Edit a course" + "\n" +
                           "4. Display course information by id" +"\n" +
                           "5. Register a student" + "\n" + 
                           "6. Exit");
    }
    
    public void printReports(){
        System.out.println("   -------" + "\n" + 
                           "  |Reports|" + "\n" + 
                           "   -------" + "\n" + 
                           "1. View all courses" + "\n" +
                           "2. View full courses" + "\n" +
                           "3. Write full courses to a file" + "\n" +
                           "4. View registered students of a course" + "\n" +
                           "5. View registered courses of a student" + "\n" + 
                           "6. Sort courses" + "\n" +
                           "7. Exit");
    }

}