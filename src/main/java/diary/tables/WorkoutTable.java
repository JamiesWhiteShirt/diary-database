package diary.tables;

import diary.Nullability;
import diary.lib.database.Table;
import diary.lib.database.DataType;

import java.sql.Timestamp;

public class WorkoutTable extends Table {
    private final Column<Integer> id = new Column<>("id", DataType.INTEGER, Nullability.NOT_NULL);
    private final Column<Timestamp> time = new Column<>("time", DataType.TIMESTAMP, Nullability.NOT_NULL);
    private final Column<Integer> duration = new Column<>("duration", DataType.INTEGER, Nullability.NOT_NULL);
    private final Column<Integer> performanceRating = new Column<>("performance_rating", DataType.INTEGER, Nullability.NULLABLE);
    private final Column<String> notes = new Column<>("notes", DataType.STRING, Nullability.NULLABLE);

    public WorkoutTable() {
        super("completed_workout");
        addColumn(id);
        addColumn(time);
        addColumn(duration);
        addColumn(performanceRating);
        addColumn(notes);
    }

    public Column<Integer> getId() {
        return id;
    }

    public Column<Timestamp> getTime() {
        return time;
    }

    public Column<Integer> getDuration() {
        return duration;
    }

    public Column<Integer> getPerformanceRating() {
        return performanceRating;
    }

    public Column<String> getNotes() {
        return notes;
    }
}
