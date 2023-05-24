import java.time.LocalDateTime;
import java.time.LocalTime;

final class Meeting extends Point {
    private String priority;

    public Meeting(String description, LocalDateTime startTime, LocalDateTime endTime, String priority){
        super(description, startTime, endTime);
        this.priority = priority;
    }

    public String getPriority(){
        return priority;
    }

    @Override
    public String toString() {
        return "Meeting: \ndescription: " + description + "\npriority: " + priority + "\nstart time: " + startTime + "\nend time" + endTime;
    }
}
