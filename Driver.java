package ga.cs;

//import android.os.Build;

//import androidx.annotation.RequiresApi;
import ga.cs.domain.Class;
import ga.cs.domain.Course;
import ga.cs.domain.MeetingTime;
import ga.cs.domain.Room;
import java.util.ArrayList;

public class Driver {
    public static final int POPULATION_SIZE=9;
    public static final double MUTATION_RATE=0.1;
    public static final double CROSSOVER_RATE=0.9;
    public static final int TOURNAMENT_SELECTION_SIZE=3;
    public static final int NUMB_OF_ELITE_SCHEDULES=1;
    private int scheduleNumber=0;
    private int classNumber=1;

    private Data data;
    //@RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args){
        Driver driver=new Driver();
        driver.data=new Data();
        int generationNumber=0;
        driver.printAvailableData();
        System.out.println("> Generation # "+generationNumber);
        System.out.println("  Schedule # |                                     ");
        System.out.println("Classes [dept,class,room,instructor,meeting-time]   ");
        System.out.println("                                    | Fitness | Conflicts");
        System.out.println("-----------------------------------------------------------------------");
        GeneticAlgorithm geneticAlgo=new GeneticAlgorithm(driver.data);
        Population popln=new Population(Driver.POPULATION_SIZE,driver.data).sortByFitness();
        popln.getSchedules().forEach(schedule -> System.out.println("       "+driver.scheduleNumber++ +"    | "+ schedule+" | "+String.format("%.5f",schedule.getFitness())+" | "+schedule.getNumberOfConflicts()));
        driver.printScheduleAsTable(popln.getSchedules().get(0), generationNumber);
        while(popln.getSchedules().get(0).getFitness()!=1.0)
        {
        	System.out.println("> Generation # "+generationNumber);
            System.out.println("  Schedule # |                                     ");
            System.out.println("Classes [dept,class,room,instructor,meeting-time]   ");
            System.out.println("                                    | Fitness | Conflicts");
            System.out.println("-----------------------------------------------------------------------");
            popln=geneticAlgo.evolve(popln).sortByFitness();
            driver.scheduleNumber=0;
            popln.getSchedules().forEach(schedule->System.out.println("       "+driver.scheduleNumber++ +"    "
            		+ "          | "+ schedule+" | "+String.format("%.5f",schedule.getFitness())+" | "+schedule.getNumberOfConflicts()));
            driver.printScheduleAsTable(popln.getSchedules().get(0), generationNumber);
            driver.classNumber=1;
        }
    }

    private void  printScheduleAsTable(Schedule schedule, int generation) {
        ArrayList<Class> classes=schedule.getClasses();
        System.out.println("\nClass # | Dept | Course (number, max # of students) | Room (capacity) | instructor (Id) | Meeting time (Id)");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        classes.forEach(x->{
            int majorIndex=data.getDepts().indexOf(x.getDept());
            int courseIndex=data.getCourses().indexOf(x.getCourse());
            int roomIndex=data.getRooms().indexOf(x.getRoom());
            int instrucIndex=data.getInstructors().indexOf(x.getInstructor());
            int meetingIndex=data.getMeetingTimes().indexOf(x.getMeetingTime());
                    System.out.println("                         ");
                    System.out.println(String.format(" %1$02d ",classNumber)+ " | ");
                    System.out.println(String.format(" %1$4s", data.getDepts().get(majorIndex).getName()+ " | "));
                    System.out.println(String.format(" %1$21s", data.getCourses().get(courseIndex).getName()+ " ( "+data.getCourses().get(courseIndex).getNumber()+", "+x.getCourse().getMaxNumbOfStudents())+ "           | ");
                    System.out.println(String.format(" %1$10s", data.getRooms().get(roomIndex).getNumber()+ " ( "+x.getRoom().getSeatingCapacity())+ "   )     | ");
                    System.out.println(String.format(" %1$15s", data.getInstructors().get(instrucIndex).getName()+ " ( "+x.getInstructor().getName()+", "+x.getInstructor().getClass())+ "     )      | ");
                    System.out.println(data.getMeetingTimes().get(meetingIndex).getTime()+ " ( "+data.getMeetingTimes().get(meetingIndex).getId()+"   ) ");
                    classNumber++;
                }
                );
                    if(schedule.getFitness()==1) System.out.println("> Solution Found in "+ (generation+1)+" generations");
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    private void printAvailableData(){
        System.out.println("Avaialable Departments -> ");
        data.getDepts().forEach(x->System.out.println("name: "+x.getName()+", courses: "+x.getCourses()));
        System.out.println("\nAvailable Courses ->");
        data.getCourses().forEach(x->System.out.println("course #: "+x.getNumber()+", name: "+x.getName()+", max # of students: "+x.getMaxNumbOfStudents()+", instructors: "+x.getInstructors()));
        System.out.println("\nAvailable Rooms ->");
        data.getRooms().forEach(x->System.out.println("room #: "+x.getNumber()+", max seating capacity: "+x.getSeatingCapacity()));
        System.out.println("\nAvaialable Instructors ->  ");
        data.getInstructors().forEach(x->System.out.println("id: "+x.getId()+", name: "+x.getName()));
        System.out.println("\nAvaialable Meeting Times ->  ");
        data.getMeetingTimes().forEach(x->System.out.println("id: "+x.getId()+", meeting Time: "+x.getTime()));
        System.out.println("-------------------------------------------------------------------------------");
    }

}
