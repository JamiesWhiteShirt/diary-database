package diary.contexts;

/*public class ViewExerciseContext extends Context {
    private final AbstractProperty<String> description;
    private final AbstractProperty<ExerciseType> type;

    public ViewExerciseContext(String name) {
        super("view " + name);
        String tableName = "exercise";
        String where = "name = \"" + name + "\"";
        description = new StringDatabaseProperty("description", tableName, "description", where);
        type = new EnumDatabaseProperty<>(ExerciseType.class, "type", tableName, "type", where);
        //addProperty(name);
        addProperty(description);
        addProperty(new EnumWrapperProperty<>(type));
    }
}*/
