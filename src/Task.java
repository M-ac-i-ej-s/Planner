import java.time.LocalDateTime;

final class Task extends Point{
    private String status;

    public Task(String description, LocalDateTime startTime, LocalDateTime endTime, String status){
        super(description, startTime, endTime);
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString() {
        return "Meeting: \ndescription: " + description + "\nstatus: " + status + "\nstart time: " + startTime + "\nend time" + endTime;
    }
}
