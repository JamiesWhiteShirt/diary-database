package diary.lib.entityclass;

import diary.lib.StringMapper;
import diary.lib.database.TableDatabaseEngine;
import diary.lib.database.DatabaseException;
import diary.lib.database.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EntityClass<KeyType> {
    public abstract class TableBinding {
        public final class Attribute<Type> {
            public final class Value {
                private final Table.Column<Type>.Value columnValue;

                private Value(Type value) {
                    columnValue = getColumn().value(value);
                }

                public Attribute getAttribute() {
                    return Attribute.this;
                }

                public Type getValue() {
                    return columnValue.getValue();
                }

                public void setValue(Type value) {
                    columnValue.setValue(value);
                }

                private Table.Column<Type>.Value getColumnValue() {
                    return columnValue;
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

            private final Table.Column<Type> column;
            private final StringMapper<Type> stringMapper;
            private final boolean isGenerated;

            public Attribute(Table.Column<Type> column, StringMapper<Type> stringMapper, boolean isGenerated) {
                this.column = column;
                this.stringMapper = stringMapper;
                this.isGenerated = isGenerated;
            }

            public Table.Column<Type> getColumn() {
                return column;
            }

            public StringMapper<Type> getStringMapper() {
                return stringMapper;
            }

            public boolean isGenerated() {
                return isGenerated;
            }

            public String getName() {
                return getColumn().getName();
            }

            public Class<Type> getType() {
                return getColumn().getDataType().getType();
            }

            public Value value(Type value) {
                return new Value(value);
            }

            public Value value() {
                return value(null);
            }
        }

        private final Table table;
        private List<Attribute<?>> attributes = new ArrayList<>();

        private TableBinding(Table table) {
            this.table = table;
        }

        public final Table getTable() {
            return table;
        }

        public List<Attribute<?>> getAttributes() {
            return attributes;
        }

        protected void addAttribute(Attribute attribute) {
            attributes.add(attribute);
        }
    }

    public abstract class PrimaryTableBinding extends TableBinding {
        public PrimaryTableBinding(Table table) {
            super(table);
        }

        public abstract Attribute<KeyType> getKey();
    }

    public abstract class JoinTableBinding extends TableBinding {
        public JoinTableBinding(Table table) {
            super(table);
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

    private final String name;

    public EntityClass(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    protected abstract PrimaryTableBinding getPrimaryTableBinding();

    private Table.Column<?>.Value getAttributeValueColumnValue(TableBinding.Attribute<?>.Value attributeValue) {
        return attributeValue.getColumnValue();
    }

    public final TableBinding.Attribute<KeyType> getKeyAttribute() {
        return getPrimaryTableBinding().getKey();
    }

    public final List<TableBinding.Attribute<?>> getAttributes() {
        return getPrimaryTableBinding().getAttributes();
    }

    public final List<TableBinding.Attribute<?>.Value> getAttributeValues() {
        return getAttributes().stream().map(EntityClass.TableBinding.Attribute::value).collect(Collectors.toList());
    }

    public final List<TableBinding.Attribute<?>> getNonGeneratedAttributes() {
        return getAttributes().stream().filter(attribute -> !attribute.isGenerated()).collect(Collectors.toList());
    }

    public final List<TableBinding.Attribute<?>.Value> getNonGeneratedAttributeValues() {
        return getNonGeneratedAttributes().stream().map(EntityClass.TableBinding.Attribute::value).collect(Collectors.toList());
    }

    public final ResultSet select(TableDatabaseEngine engine, List<TableBinding.Attribute<?>.Value> get) throws EntityException {
        try {
            PrimaryTableBinding primaryTableBinding = getPrimaryTableBinding();
            List<Table.Column<?>.Value> getColumns = get.stream().map(this::getAttributeValueColumnValue).collect(Collectors.toList());
            TableDatabaseEngine.ResultSet resultSet = engine.select(primaryTableBinding.getTable(), getColumns, Collections.emptyList());
            return new ResultSet(resultSet);
        } catch (DatabaseException e) {
            throw new EntityException(e.getMessage());
        }
    }

    public final ResultSet select(TableDatabaseEngine engine, List<TableBinding.Attribute<?>.Value> get, KeyType key) throws EntityException {
        try {
            PrimaryTableBinding primaryTableBinding = getPrimaryTableBinding();
            List<Table.Column<?>.Value> getColumns = get.stream().map(this::getAttributeValueColumnValue).collect(Collectors.toList());
            Table.Column<?>.Value whereColumn = primaryTableBinding.getKey().value(key).getColumnValue();
            TableDatabaseEngine.ResultSet resultSet = engine.select(primaryTableBinding.getTable(), getColumns, Collections.singletonList(whereColumn));
            return new ResultSet(resultSet);
        } catch (DatabaseException e) {
            throw new EntityException(e.getMessage());
        }
    }

    public final ResultSet insert(TableDatabaseEngine engine, List<TableBinding.Attribute<?>.Value> values, List<TableBinding.Attribute<?>.Value> generated) throws EntityException {
        try {
            List<Table.Column<?>.Value> valueColumnValues = values.stream().map(this::getAttributeValueColumnValue).collect(Collectors.toList());
            List<Table.Column<?>.Value> generatedColumnValues = generated.stream().map(this::getAttributeValueColumnValue).collect(Collectors.toList());
            TableDatabaseEngine.ResultSet resultSet = engine.insert(getPrimaryTableBinding().getTable(), valueColumnValues, generatedColumnValues);
            return new ResultSet(resultSet);
        } catch (DatabaseException e) {
            throw new EntityException(e.getMessage());
        }
    }

    public final void update(TableDatabaseEngine engine, List<TableBinding.Attribute<?>.Value> values, KeyType key) throws EntityException {
        try {
            PrimaryTableBinding primaryTableBinding = getPrimaryTableBinding();
            List<Table.Column<?>.Value> valueColumnValues = values.stream().map(this::getAttributeValueColumnValue).collect(Collectors.toList());
            Table.Column<?>.Value whereColumn = primaryTableBinding.getKey().value(key).getColumnValue();
            engine.update(primaryTableBinding.getTable(), valueColumnValues, Collections.singletonList(whereColumn));
        } catch (DatabaseException e) {
            throw new EntityException(e.getMessage());
        }
    }
}
