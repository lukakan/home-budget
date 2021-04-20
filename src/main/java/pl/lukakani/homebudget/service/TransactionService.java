package pl.lukakani.homebudget.service;

import org.springframework.stereotype.Service;
import pl.lukakani.homebudget.dao.TransactionDao;
import pl.lukakani.homebudget.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionDao transactionDao;

    public TransactionService() {
        this.transactionDao = new TransactionDao();
    }

    public List<Transaction> getAllIncome() {
        return transactionDao.findAllTransactionFromGivenType(Transaction.Type.INCOME);
    }

    public List<Transaction> getAllExpenses() {
        return transactionDao.findAllTransactionFromGivenType(Transaction.Type.EXPENSE);
    }

    public List<Transaction> getAll() {
        return transactionDao.findAllTransaction();
    }

    public boolean saveTransaction(Transaction transaction) {
        return transactionDao.save(transaction);
    }

    public void updateTransaction(Transaction transaction) {
        transactionDao.update(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionDao.delete(id);
    }

    public Transaction findById(Long id) {
        return transactionDao.findTransactionById(id).orElseThrow(IllegalArgumentException::new);
    }

    public BigDecimal getSum(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getBalance(List<Transaction> transactions) {
        BigDecimal incomeSum = sumTransactionInType(transactions, Transaction.Type.INCOME);
        BigDecimal expenseSum = sumTransactionInType(transactions, Transaction.Type.EXPENSE);
        return incomeSum.subtract(expenseSum);
    }

    private BigDecimal sumTransactionInType(List<Transaction> transactions, Transaction.Type type) {
        return transactions.stream()
                .filter(transaction -> transaction.getType().equals(type))
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
