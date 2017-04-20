package diary.entityclasses;

import diary.lib.StringMapper;
import diary.lib.entityclass.EntityClass;
import diary.tables.WorkoutTable;

import java.sql.Timestamp;

public class WorkoutEntityClass extends EntityClass<Integer> {
    public final class WorkoutTableBinding extends PrimaryTableBinding {
        public final Attribute<Integer> id;
        public final Attribute<Timestamp> time;
        public final Attribute<Integer> duration;
        public final Attribute<Integer> performanceRating;
        public final Attribute<String> notes;

        private WorkoutTableBinding(WorkoutTable table) {
            super(table);
            addAttribute(id = new Attribute<>(table.id, StringMapper.INTEGER, true));
            addAttribute(time = new Attribute<>(table.time, StringMapper.TIMESTAMP, false));
            addAttribute(duration = new Attribute<>(table.duration, StringMapper.INTEGER, false));
            addAttribute(performanceRating = new Attribute<>(table.performanceRating, StringMapper.INTEGER, false));
            addAttribute(notes = new Attribute<>(table.notes, StringMapper.STRING, false));
        }

        @Override
        public Attribute<Integer> getKey() {
            return id;
        }
    }

    private final WorkoutTableBinding primaryTableBinding;

    public WorkoutEntityClass(WorkoutTable table) {
        super("Workout");
        primaryTableBinding = new WorkoutTableBinding(table);
    }

    @Override
    public WorkoutTableBinding getPrimaryTableBinding() {
        return primaryTableBinding;
    }
}
