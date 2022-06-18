package dao;

import dto.ExpressionFilter;
import entity.Expression;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class ExpressionDao implements Dao<Expression, Long> {

    private static final ExpressionDao INSTANCE = new ExpressionDao();

    private static final String SAVE_SQL = """
            INSERT INTO expression(value, result)
            VALUES (?, ?)
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id,
                   result,
                   value
            FROM expression
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE expression
            SET id = ?,
                value = ?,
                result = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM expression
            WHERE id = ?
            """;

    @Override
    public Expression save(Expression entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getValue());
            statement.setDouble(2, entity.getResult());
            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }
            return entity;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    private Expression buildEntity(ResultSet resultSet) throws SQLException {
        return new Expression(
                resultSet.getLong("id"),
                resultSet.getString("value"),
                resultSet.getDouble("result")
        );
    }

    @Override
    public Optional<Expression> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);

            Expression expression = null;
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                expression = buildEntity(resultSet);
            }
            return Optional.ofNullable(expression);
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public List<Expression> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();

            List<Expression> expressions = new ArrayList<>();
            while (resultSet.next()) {
                expressions.add(buildEntity(resultSet));
            }
            return expressions;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public boolean update(Expression entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getValue());
            statement.setDouble(3, entity.getResult());
            statement.setLong(4, entity.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    public List<Expression> findAllByFilter(ExpressionFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSQL = new ArrayList<>();

        if (filter.aEquals() != null) {
            parameters.add(filter.aEquals());
            whereSQL.add("result = ?");
        }
        if (filter.aLess() != null) {
            parameters.add(filter.aLess());
            whereSQL.add("result <= ?");
        }
        if (filter.aLarge() != null) {
            parameters.add(filter.aLarge());
            whereSQL.add("result >= ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        String where = "";
        if (filter.aEquals() != null || filter.aLess() != null || filter.aLarge() != null) {
            where = whereSQL.stream()
                    .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        } else {
            where = whereSQL.stream()
                    .collect(joining(" AND ", "", " LIMIT ? OFFSET ? "));
        }
        String dynamicSql = FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(dynamicSql)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            System.out.println(statement);

            List<Expression> expressions = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                expressions.add(buildEntity(resultSet));
            }
            return expressions;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    public static ExpressionDao getInstance() {
        return INSTANCE;
    }
}
