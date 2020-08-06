package ga.cs;

//import android.os.Build;

//import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Population {
    private ArrayList<Schedule> schedules;

   // @RequiresApi(api = Build.VERSION_CODES.N)
    public Population(int size, Data data) {
        schedules=new ArrayList<Schedule>(size);
        IntStream.range(0,size).forEach(x->schedules.add(new Schedule(data).initialize()));

    }

    public ArrayList<Schedule> getSchedules(){return this.schedules;}
    //@RequiresApi(api = Build.VERSION_CODES.N)
    public Population sortByFitness() {
        schedules.sort((s1, s2) -> {
            int returnValue = 0;
            if (s1.getFitness() > s2.getFitness()) returnValue = -1;
            else if (s1.getFitness() < s2.getFitness()) returnValue = 1;
            return returnValue;

        });
        return this;
    }
}
