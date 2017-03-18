package diary.tables;

import diary.lib.database.Nullability;
import diary.lib.database.Table;
import diary.lib.database.DataType;

import java.sql.Timestamp;

public class WorkoutTable extends Table<WorkoutTable> {
    public final Column<Integer> id = new Column<>("id", DataType.INTEGER, Nullability.NOT_NULL);
    public final Column<Timestamp> time = new Column<>("time", DataType.TIMESTAMP, Nullability.NOT_NULL);
    public final Column<Integer> duration = new Column<>("duration", DataType.INTEGER, Nullability.NOT_NULL);
    public final Column<Integer> performanceRating = new Column<>("performance_rating", DataType.INTEGER, Nullability.NULLABLE);
    public final Column<String> notes = new Column<>("notes", DataType.STRING, Nullability.NULLABLE);

    public WorkoutTable() {
        super("completed_workout");
        addColumn(id);
        addColumn(time);
        addColumn(duration);
        addColumn(performanceRating);
        addColumn(notes);
    }
}
