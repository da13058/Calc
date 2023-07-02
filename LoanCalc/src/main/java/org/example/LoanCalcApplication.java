package org.example;

import org.example.model.enums.LoanType;
import org.example.services.LoanService;
import org.example.ui.LoanCalcUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class LoanCalcApplication implements CommandLineRunner {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanCalcUI loanCalcUI;

    public static void main(String[] args) {
        SpringApplication.run(LoanCalcApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            loanCalcUI.showHelp();
        } else {
            switch (args[0]) {
                case "housing":
                    loanCalcUI.launchConsoleApp(loanService, LoanType.HOUSING_LOAN);
                    break;
                case "help":
                default:
                    loanCalcUI.showHelp();
            }
        }
    }
}