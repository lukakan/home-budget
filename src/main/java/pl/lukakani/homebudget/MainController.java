package pl.lukakani.homebudget;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/add")
    public String getAddNewTransactionForm() {
        return "add";
    }

    @GetMapping("/list")
    public String getTransactionListPage() {
        return "list";
    }

}
