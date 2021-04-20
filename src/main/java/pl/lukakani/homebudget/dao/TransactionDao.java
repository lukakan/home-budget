package pl.lukakani.homebudget.dao;

import pl.lukakani.homebudget.model.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDao {
    private final Connection connection;

    public TransactionDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/home_budget", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> findAllTransactionFromGivenType(Transaction.Type transactionType) {
        String sqlQuery = "SELECT * FROM transaction WHERE type = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, transactionType.toString());
            ResultSet resultSet = statement.executeQuery();
            return getTransactions(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> findAllTransaction() {
        String sqlQuery = "SELECT * FROM transaction";
        try (Statement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            return getTransactions(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Transaction> getTransactions(ResultSet resultSet) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            Transaction.Type type = Transaction.Type.valueOf(resultSet.getString("type"));
            String description = resultSet.getString("description");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            LocalDate date = resultSet.getDate("transaction_date").toLocalDate();
            transactions.add(new Transaction(id, type, description, amount, date));
        }
        return transactions;
    }

    public void update(Transaction transaction) {
        String sqlQuery = "UPDATE transaction SET type = ?, description = ?, amount = ?, transaction_date = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, transaction.getType().toString());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setDate(4, Date.valueOf(transaction.getDate().toString()));
            preparedStatement.setLong(5, transaction.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Transaction transaction) {
        String sqlQuery = "INSERT INTO transaction (type, description, amount, transaction_date) VALUES(?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, transaction.getType().toString());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setDate(4, Date.valueOf(transaction.getDate().toString()));
            int update = preparedStatement.executeUpdate();
            return update == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Transaction> findTransactionById(Long searchId) {
        String sqlQuery = "SELECT * FROM transaction WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            Optional<Transaction> transaction;
            preparedStatement.setLong(1, searchId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Transaction.Type type = Transaction.Type.valueOf(resultSet.getString("type"));
                String description = resultSet.getString("description");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                LocalDate date = resultSet.getDate("transaction_date").toLocalDate();
                return Optional.of(new Transaction(id, type, description, amount, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        String sqlQuery = "DELETE FROM transaction WHERE id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
