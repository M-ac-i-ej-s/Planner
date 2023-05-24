import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        System.out.println("This program lets you organize meetings");
        Kalendarz meetingsTable = new Kalendarz(7);
        while (run) {
            menu();
            int program = sc.nextInt();
            switch (program) {
                case 0 -> run = false;
                case 1 -> addingMeeting(meetingsTable, sc);
                case 2 -> addingTask(meetingsTable, sc);
                case 3 -> deleting(meetingsTable, sc);
                case 4 -> showingMeetings(meetingsTable, sc);
                case 5 -> showingTasks(meetingsTable, sc);
                case 6 -> showingMeetingsWithPriority(meetingsTable, sc);
                case 7 -> showingMeetingsFrom(meetingsTable, sc);
                case 8 -> showingMeetingsFromWithPriority(meetingsTable, sc);
                case 9 -> showingMeetingsFromAndTo(meetingsTable, sc);
                case 10 -> showingTasksWithStatus(meetingsTable, sc);
                case 11 -> showingTasksToWithStatus(meetingsTable, sc);
                default -> System.out.println("error");
            }
        }
    }

    public static void menu() {
        System.out.println("""
                0 - exit\s
                1 - add new meeting
                2 - add new task
                3 - delete task / meeting
                4 - Show meetings from one day
                5 - Show tasks from one day
                6 - Show meetings with specified priority
                7 - show meetings from a day from specific time
                8 - show meetings from a day from specific time with specific priority
                9 - show meetings from a day from specific time to later time the same day
                10 - show Tasks with a specific status
                11 - show tasks with a specific status and end time in the future"""

        );
    }

    public static void addingMeeting(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("set description");
        String description = sc.next();
        System.out.println("set start date in format: yyyy-MM-dd");
        String dateTime = sc.next();
        System.out.println("set time in format: HH:mm");
        String startTime = sc.next();
        LocalDateTime startTimeDate = LocalDateTime.parse(dateTime + " " + startTime, formatter);

        if(startTimeDate.getHour() < Point.EARLIEST.getHour()){
            throw new IllegalArgumentException("Time is too early !");
        }

        System.out.println("set end date in format: yyyy-MM-dd");
        String endDateTime = sc.next();
        System.out.println("set end time in format: HH:mm");
        String endTime = sc.next();
        LocalDateTime endTimeDate = LocalDateTime.parse(endDateTime + " " + endTime, formatter);

        System.out.println("set priority ( low, medium, high ): ");
        String priority = sc.next();
        Meeting meet = new Meeting(description, startTimeDate, endTimeDate, priority);
        meetingsTable.add(meet);
    }

    public static void addingTask(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("set description");
        String description = sc.next();
        System.out.println("set start date in format: yyyy-MM-dd");
        String dateTime = sc.next();
        System.out.println("set time in format: HH:mm");
        String startTime = sc.next();
        LocalDateTime startTimeDate = LocalDateTime.parse(dateTime + " " + startTime, formatter);

        if(startTimeDate.getHour() < Point.EARLIEST.getHour()){
            throw new IllegalArgumentException("Time is too early !");
        }

        System.out.println("set end date in format: yyyy-MM-dd");
        String endDateTime = sc.next();
        System.out.println("set end time in format: HH:mm");
        String endTime = sc.next();
        LocalDateTime endTimeDate = LocalDateTime.parse(endDateTime + " " + endTime, formatter);

        System.out.println("set status ( new, in progress, finished ): ");
        String status = sc.next();
        Task task = new Task(description, startTimeDate, endTimeDate, status);
        meetingsTable.add(task);
    }

    public static void deleting(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("From which day you need to delete a meeting, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("These are the meetings in that day: ");
        ArrayList<Meeting> aMeet = meetingsTable.showMeetings(dayDate);
        for (Meeting some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
        System.out.println("Choose a meeting, set a number: ");
        int num = sc.nextInt();
        meetingsTable.delete(num, dayDate);
    }

    public static void showingMeetings(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("From which day you need to see all meetings, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("These are the meetings in that day: ");
        ArrayList<Meeting> aMeet = meetingsTable.showMeetings(dayDate);
        for (Meeting some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }

    public static void showingTasks(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("From which day you need to see all tasks, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("These are the tasks in that day: ");
        ArrayList<Task> aMeet = meetingsTable.showTasks(dayDate);
        for (Task some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }

    public static void showingMeetingsWithPriority(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("From which day you need to see meetings, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("What priority would you like to find: ");
        String priority = sc.next();
        Predicate<Meeting> filter = s -> Objects.equals(s.getPriority(), priority);
        ArrayList<Meeting> aMeet = meetingsTable.showFilter(dayDate, filter);
        for (Meeting some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }

    public static void showingTasksWithStatus(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("From which day you need to see tasks, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("What status would you like to find: ");
        String status = sc.next();
        Predicate<Task> filter = s -> Objects.equals(s.getStatus(), status);
        ArrayList<Task> aMeet = meetingsTable.showFilterTasks(dayDate, filter);
        for (Task some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }

    public static void showingMeetingsFrom(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("From which day you need to see meetings, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("From which time you need to see meetings: ");
        String time = sc.next();
        LocalTime timeDate = LocalTime.parse(time, timeFormatter);
        Predicate<Meeting> filter = s -> s.getStartTime().isAfter(timeDate.atDate(dayDate));
        ArrayList<Meeting> aMeet = meetingsTable.showFilter(dayDate, filter);
        for (Meeting some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }

    public static void showingMeetingsFromWithPriority(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("From which day you need to see meetings, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("From which time you need to see meetings: ");
        String time = sc.next();
        LocalTime timeDate = LocalTime.parse(time, timeFormatter);
        System.out.println("What priority would you like to find: ");
        String priority = sc.next();
        Predicate<Meeting> filter = s -> s.getStartTime().isAfter(timeDate.atDate(dayDate)) && s.getPriority().equals(priority);
        ArrayList<Meeting> aMeet = meetingsTable.showFilter(dayDate, filter);
        for (Meeting some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }

    public static void showingTasksToWithStatus(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("From which day you need to see tasks, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("From which time you need to see tasks: ");
        String time = sc.next();
        LocalTime timeDate = LocalTime.parse(time, timeFormatter);
        System.out.println("What priority would you like to find: ");
        String status = sc.next();
        Predicate<Task> filter = s -> s.getStartTime().isAfter(timeDate.atDate(dayDate)) && s.getStatus().equals(status);
        ArrayList<Task> aMeet = meetingsTable.showFilterTasks(dayDate, filter);
        for (Task some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }

    public static void showingMeetingsFromAndTo(Kalendarz meetingsTable, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("From which day you need to see meetings, set it in yyyy-MM-dd format: ");
        String day = sc.next();
        LocalDate dayDate = LocalDate.parse(day, formatter);
        System.out.println("From which time you need to see meetings: ");
        String time = sc.next();
        LocalTime timeDateFrom = LocalTime.parse(time, timeFormatter);
        System.out.println("To which time you need to see meetings: ");
        String timeTo = sc.next();
        LocalTime timeDateTo = LocalTime.parse(timeTo, timeFormatter);
        Predicate<Meeting> filter = s -> s.getStartTime().isAfter(timeDateFrom.atDate(dayDate)) && s.getEndTime().isBefore(timeDateTo.atDate(dayDate));
        ArrayList<Meeting> aMeet = meetingsTable.showFilter(dayDate, filter);
        for (Meeting some : aMeet) {
            System.out.println(some.toString());
            System.out.println("---------------------");
        }
    }
}
