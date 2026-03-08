public class Event extends Task {
    protected String startToEndDate;

    public Event(String description, String startToEndDate) {
        super(description);
        this.startToEndDate = startToEndDate;
    }

    public Event(String description, String startToEndDate, boolean isDone) {
        super(description, isDone);
        this.startToEndDate = startToEndDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (" + startToEndDate.replace("/from", "from:")
                .replace("/to", "to:") + ")";
    }
}
