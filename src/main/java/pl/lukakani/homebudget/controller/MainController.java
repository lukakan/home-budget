package pl.lukakani.homebudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.lukakani.homebudget.model.Transaction;
import pl.lukakani.homebudget.service.TransactionService;

import java.util.List;

@Controller
public class MainController {

    private final TransactionService service;

    @Autowired
    public MainController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/add")
    public String getAddNewTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("action", "add");
        return "add";
    }

    @PostMapping("/add")
    public String addNewTransaction(Transaction transaction, RedirectAttributes redirectAttributes) {
        boolean success = service.saveTransaction(transaction);
        if (success) {
            redirectAttributes.addFlashAttribute("feedback", "success");
        } else {
            redirectAttributes.addFlashAttribute("feedback", "error");
        }
        return "redirect:/add";
    }

    @GetMapping("/list")
    public String getTransactionListPage(@RequestParam(name = "category") Transaction.Type type, Model model) {
        List<Transaction> transactions;
        if (type.equals(Transaction.Type.EXPENSE)) {
            transactions = service.getAllExpenses();
        } else {
            transactions = service.getAllIncome();
        }
        model.addAttribute("transactions", transactions);
        model.addAttribute("sum", service.getSum(transactions));
        model.addAttribute("title", type);
        model.addAttribute("editmode", false);
        return "list";
    }

    @GetMapping("/editablelist")
    public String getTransactionEditableListPage(Model model) {
        List<Transaction> transactions = service.getAll();
        model.addAttribute("transactions", transactions);
        model.addAttribute("sum", service.getBalance(transactions));
        model.addAttribute("title", "ALL");
        model.addAttribute("editmode", true);
        return "list";
    }

    @GetMapping("/update")
    public String getEditForm(@RequestParam(name = "id") Long id, Model model) {
        Transaction transaction = service.findById(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("editmode", true);
        model.addAttribute("action", "edit");
        return "/add";
    }

    @PostMapping("/update")
    public String update(Transaction transaction, RedirectAttributes redirectAttributes) {
        service.updateTransaction(transaction);
        return "redirect:/editablelist";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id) {
        service.deleteTransaction(id);
        return "redirect:/editablelist";
    }

}
