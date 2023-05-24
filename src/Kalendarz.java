import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Predicate;


public class Kalendarz {
    private ArrayList<ArrayList<Object>> week;

    public Kalendarz(int numOfDays) {
        week = new ArrayList<>();
        for(int i = 0; i < numOfDays; i++) {
            week.add(new ArrayList<>());
        }
    }

    public Kalendarz() {
        this(7);
    }

    public void add(Meeting meet) {
        LocalDateTime timeNow = LocalDateTime.now();
        int addition = 0;
        if(timeNow.getMonthValue() < meet.getStartTime().getMonthValue()){
            addition = calculateAddition(meet.getStartTime(), timeNow);
        }
        week.get(meet.getStartTime().getDayOfMonth() - timeNow.getDayOfMonth()-1+addition).add(meet);
    }

    public void add(Task task) {
        LocalDateTime timeNow = LocalDateTime.now();
        int addition = 0;
        if(timeNow.getMonthValue() < task.getStartTime().getMonthValue()){
            addition = calculateAddition(task.getStartTime(), timeNow);
        }
        week.get(task.getStartTime().getDayOfMonth() - timeNow.getDayOfMonth()-1+addition).add(task);
    }

    public void delete(int num, LocalDate day) {
        LocalDateTime timeNow = LocalDateTime.now();
        int addition = 0;
        if(timeNow.getMonthValue() < day.getMonthValue()){
            addition = calculateAddition(day.atStartOfDay(), timeNow);
        }
        week.get(day.getDayOfMonth() - timeNow.getDayOfMonth() - 1 + addition).remove(num);
    }

    public ArrayList<Meeting> showMeetings(LocalDate day) {
        LocalDateTime timeNow = LocalDateTime.now();
        int addition = 0;
        if(timeNow.getMonthValue() < day.getMonthValue()){
            addition = calculateAddition(day.atStartOfDay(), timeNow);
        }
        ArrayList<Meeting> meetingsList = new ArrayList<>();

        for (Object element : week.get(day.getDayOfMonth() - timeNow.getDayOfMonth() - 1 + addition)) {
            if (element instanceof Meeting) {
                meetingsList.add((Meeting) element);
            }
        }
        return meetingsList;
    }

    public ArrayList<Task> showTasks(LocalDate day) {
        LocalDateTime timeNow = LocalDateTime.now();
        int addition = 0;
        if(timeNow.getMonthValue() < day.getMonthValue()){
            addition = calculateAddition(day.atStartOfDay(), timeNow);
        }
        ArrayList<Task> meetingsList = new ArrayList<>();

        for (Object element : week.get(day.getDayOfMonth() - timeNow.getDayOfMonth() - 1 + addition)) {
            if (element instanceof Task) {
                meetingsList.add((Task) element);
            }
        }
        return meetingsList;
    }

    public ArrayList<Meeting> showFilter(LocalDate date, Predicate<Meeting> warunek) {
        ArrayList<Meeting> meetingsInDay = showMeetings(date);
        ArrayList<Meeting> filtredMeetings = new ArrayList<>();
        for (Meeting meet : meetingsInDay) {
            if (warunek.test(meet)) {
                filtredMeetings.add(meet);
            }
        }
        return filtredMeetings;
    }

    public ArrayList<Task> showFilterTasks(LocalDate date, Predicate<Task> warunek) {
        ArrayList<Task> meetingsInDay = showTasks(date);
        ArrayList<Task> filtredMeetings = new ArrayList<>();
        for (Task meet : meetingsInDay) {
            if (warunek.test(meet)) {
                filtredMeetings.add(meet);
            }
        }
        return filtredMeetings;
    }

    public int calculateAddition(LocalDateTime startTime, LocalDateTime timeNow){
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int addition = 0;
        if(timeNow.getMonthValue() < startTime.getMonthValue()){
            for(int i = timeNow.getMonthValue(); i < startTime.getMonthValue(); i++){
                addition += daysInMonth[i];
            }
        }
        return addition;
    }
}

