package diary.commandcontext;

import diary.ExerciseType;
import diary.property.AbstractProperty;
import diary.property.database.EnumDatabaseProperty;
import diary.property.database.StringDatabaseProperty;
import diary.property.wrapper.EnumWrapperProperty;

public class ViewExerciseContext extends Context {
    //private final AbstractProperty<String> name;
    private final AbstractProperty<String> description;
    private final AbstractProperty<ExerciseType> type;

    public ViewExerciseContext(String name) {
        super("view " + name);
        String tableName = "exercise";
        String where = "name = \"" + name + "\"";
        //name = new StringDatabaseProperty("name", tableName, "name", where);
        description = new StringDatabaseProperty("description", tableName, "description", where);
        type = new EnumDatabaseProperty<>(ExerciseType.class, "type", tableName, "type", where);
        //addProperty(name);
        addProperty(description);
        addProperty(new EnumWrapperProperty<>(type));
    }
}
