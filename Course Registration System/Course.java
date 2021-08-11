import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Course implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String id;
    private int maxStu;
    private int currentStu;
    private String instructor;
    private int section;
    private String location;
    private ArrayList<Student> stuInCourse;

    Course(String name, String id, int maxStu, String instructor, int section, String location){
        this.name = name;
        this.id = id;
        this.maxStu = maxStu;
        this.currentStu = currentStu;
        this.instructor = instructor;
        this.section = section;
        this.location = location;
        currentStu = 0;
        stuInCourse = new ArrayList<Student>();
    }

	public static Comparator<Course> sortByCurrent = new Comparator<Course>(){
		@Override
		public int compare(Course c1, Course c2){
            if(c1.getCurrentStu() < c2.getCurrentStu()){
                return -1;
            }
            else if(c1.getCurrentStu() == c2.getCurrentStu()){
                return 0;
            }
            else{
                return 1;
            }
		}
    };

    public void incrementCurrent(){
        this.currentStu++;
    }

    public void decrementCurrent(){
        this.currentStu--;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxStu() {
        return this.maxStu;
    }

    public void setMaxStu(int maxStu) {
        this.maxStu = maxStu;
    }

    public int getCurrentStu() {
        return this.currentStu;
    }

    public void setCurrentStu(int currentStu) {
        this.currentStu = currentStu;
    }

    public ArrayList<Student> getStuInCourse(){
        return this.stuInCourse;
    }

    public void setStuInCourse(ArrayList<Student> stuInCourse){
        this.stuInCourse = stuInCourse;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getSection() {
        return this.section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}