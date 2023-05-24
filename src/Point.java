import java.time.LocalDateTime;
import java.time.LocalTime;

sealed class Point permits Task, Meeting  {
    protected String description;
    protected LocalDateTime startTime, endTime;
    public static final LocalTime EARLIEST=LocalTime.of(10, 0, 0);
    public Point(String description, LocalDateTime startTime, LocalDateTime endTime){
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDescription() {
        return  description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
