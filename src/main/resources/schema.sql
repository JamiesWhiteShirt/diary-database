CREATE TABLE IF NOT EXISTS completed_workout(
	id					INTEGER		PRIMARY KEY AUTOINCREMENT,
	time				DATETIME	NOT NULL,
	duration			INT			NOT NULL,
	performance_rating	INT,
	notes				TEXT
);

CREATE TABLE IF NOT EXISTS outdoor_completed_workout(
	completed_workout_id	INT		PRIMARY KEY,
	temperature				INT		NOT NULL,
	weather_conditions		TEXT	NOT NULL,
	FOREIGN KEY (completed_workout_id)
		REFERENCES completed_workout(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS indoor_completed_workout(
	completed_workout_id	INT		PRIMARY KEY,
	air						TEXT	NOT NULL,
	spectators				INT		NOT NULL,
	FOREIGN KEY (completed_workout_id)
		REFERENCES completed_workout(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exercise_replaceable_by(
	exercise_name_a		VARCHAR(50)		NOT NULL,
	exercise_name_b		VARCHAR(50)		NOT NULL,
	PRIMARY KEY (exercise_name_a, exercise_name_b),
	FOREIGN KEY (exercise_name_a)
		REFERENCES exercise(name)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	FOREIGN KEY (exercise_name_b)
		REFERENCES exercise(name)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exercise(
	name			VARCHAR(50)		PRIMARY KEY,
	description		TEXT			NOT NULL,
	type			VARCHAR(50)		NOT NULL
);

CREATE TABLE IF NOT EXISTS completed_exercise(
	id						INT				NOT NULL,
	completed_workout_id	INT 			NOT NULL,
	exercise_name			VARCHAR(50)		NOT NULL,
	PRIMARY KEY (id, completed_workout_id),
	FOREIGN KEY (completed_workout_id)
		REFERENCES completed_workout(id)
		ON UPDATE CASCADE,
	FOREIGN KEY (exercise_name)
		REFERENCES exercise(name)
		ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS repetition_exercise(
	completed_exercise_id	INT		NOT NULL,
	completed_workout_id	INT		NOT NULL,
	load					INT		NOT NULL,
	reps					INT		NOT NULL,
	sets					INT		NOT NULL,
	PRIMARY KEY (completed_exercise_id, completed_workout_id),
	FOREIGN KEY (completed_exercise_id, completed_workout_id)
		REFERENCES completed_exercise(id, completed_workout_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurance_completed_exercise(
	completed_exercise_id	INT		NOT NULL,
	completed_workout_id	INT		NOT NULL,
	length					INT,
	duration				INT,
	PRIMARY KEY (completed_exercise_id, completed_workout_id),
	FOREIGN KEY (completed_exercise_id, completed_workout_id)
		REFERENCES completed_exercise(id, completed_workout_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS major_group(
	name				VARCHAR(50)		PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS exercise_major_group(
	exercise_name		VARCHAR(50)		NOT NULL,
	major_group_name	VARCHAR(50)		NOT NULL,
	PRIMARY KEY (exercise_name, major_group_name),
	FOREIGN KEY (exercise_name)
		REFERENCES exercise(name)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	FOREIGN KEY (major_group_name)
		REFERENCES major_group(name)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS gps_info(
	id						INT		NOT NULL,
	completed_workout_id	INT		NOT NULL,
	pulse					INT		NOT NULL,
	longitude				DOUBLE	NOT NULL,
	latitude				DOUBLE	NOT NULL,
	altitude				DOUBLE	NOT NULL,
	PRIMARY KEY (id, completed_workout_id),
	FOREIGN KEY (completed_workout_id)
		REFERENCES completed_workout(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS goal_period(
    id              INTEGER         PRIMARY KEY AUTOINCREMENT,
    workout_name    VARCHAR(50)     NOT NULL,
    from_time       DATETIME        NOT NULL,
    to_time         DATETIME        NOT NULL,
    description     TEXT            NOT NULL,
    FOREIGN KEY (workout_name)
        REFERENCES workout(name)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
