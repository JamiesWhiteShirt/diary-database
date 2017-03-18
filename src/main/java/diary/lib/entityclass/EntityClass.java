package diary.lib.entityclass;

import diary.lib.StringMapper;
import diary.lib.database.TableDatabaseEngine;
import diary.lib.database.DatabaseException;
import diary.lib.database.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EntityClass<T> {
    public abstract class TableBinding<TableType extends Table> {
        public final class Attribute<Type> {
            public final class Property {
                private final Table<TableType>.Column<Type>.Property columnProperty;

                private Property(Type value) {
                    columnProperty = getColumn().property(value);
                }

                public Attribute getAttribute() {
                    return Attribute.this;
                }

                public Type getValue() {
                    return columnProperty.getValue();
                }

                public void setValue(Type value) {
                    columnProperty.setValue(value);
                }

                private Table<TableType>.Column<Type>.Property getColumnProperty() {
                    return columnProperty;
                }

                public String getStringValue() {
                    Type value = getValue();
                    if (value != null) {
                        return getStringMapper().toString(value);
                    } else {
                        return null;
                    }
                }
            }

            private final Table<TableType>.Column<Type> column;
            private final StringMapper<Type> stringMapper;
            private final boolean isGenerated;

            public Attribute(Table<TableType>.Column<Type> column, StringMapper<Type> stringMapper, boolean isGenerated) {
                this.column = column;
                this.stringMapper = stringMapper;
                this.isGenerated = isGenerated;
            }

            public Table<TableType>.Column<Type> getColumn() {
                return column;
            }

            public StringMapper<Type> getStringMapper() {
                return stringMapper;
            }

            public boolean isGenerated() {
                return isGenerated;
            }

            public Property property(Type value) {
                return new Property(value);
            }

            public Property property() {
                return property(null);
            }
        }

        private final TableType table;
        private List<Attribute> attributes = new ArrayList<>();

        private TableBinding(TableType table) {
            this.table = table;
        }

        public final TableType getTable() {
            return table;
        }

        public List<Attribute> getAttributes() {
            return attributes;
        }

        protected void addAttribute(Attribute attribute) {
            attributes.add(attribute);
        }
    }

    public abstract class PrimaryTableBinding<TableType extends Table, KeyType> extends TableBinding<TableType> {
        public PrimaryTableBinding(TableType table) {
            super(table);
        }

        public abstract Attribute getKey();

        public final ResultSet select(TableDatabaseEngine engine, List<Attribute.Property> get) throws EntityException {
            try {
                TableType table = getTable();
                List<Table.Column.Property> getColumns = get.stream().map(TableBinding.Attribute.Property::getColumnProperty).collect(Collectors.toList());
                List<Table.Column.Property> whereColumns = Collections.emptyList();
                TableDatabaseEngine.ResultSet resultSet = engine.select(table, getColumns, whereColumns);
                return new ResultSet(resultSet);
            } catch (DatabaseException e) {
                throw new EntityException(e.getMessage());
            }
        }
    }

    public final class ResultSet {
        private final TableDatabaseEngine.ResultSet resultSet;

        private ResultSet(TableDatabaseEngine.ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean next() throws EntityException {
            try {
                return resultSet.next();
            } catch (DatabaseException e) {
                throw new EntityException(e.getMessage());
            }
        }
    }

    public abstract PrimaryTableBinding<?, T> getPrimaryTableBinding();
}
