package diary.tables;

import diary.lib.database.Nullability;
import diary.lib.database.Table;
import diary.lib.database.DataType;

public class ExerciseTable extends Table {
    public final Column<String> name = new Column<>("name", DataType.STRING, Nullability.NOT_NULL);
    public final Column<String> description = new Column<>("description", DataType.STRING, Nullability.NOT_NULL);
    public final Column<String> type = new Column<>("type", DataType.STRING, Nullability.NOT_NULL);

    public ExerciseTable() {
        super("exercise");
        addColumn(name);
        addColumn(description);
        addColumn(type);
    }
}
