package diary.commandcontext;

import diary.property.DateProperty;
import diary.property.IntegerProperty;
import diary.property.StringProperty;

public class AddWorkoutContext extends Context {
    private final DateProperty date = new DateProperty("time", null);
    private final IntegerProperty duration = new IntegerProperty("duration", null);
    private final IntegerProperty performanceRating = new IntegerProperty("performanceRating", null);
    private final StringProperty notes = new StringProperty("notes", null);

    public AddWorkoutContext() {
        super("addWorkout");
        addProperty(date);
        addProperty(duration);
        addProperty(performanceRating);
        addProperty(notes);
    }
}
