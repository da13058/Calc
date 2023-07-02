package org.example.ui;

import java.util.Scanner;

import org.example.constants.UtilityConstants;
import org.example.exceptions.ValidationException;
import org.example.model.enums.LoanType;
import org.example.model.loan.BaseLoan;
import org.example.model.loan.HousingLoan;
import org.example.model.report.MonthlyReport;
import org.example.services.LoanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoanCalcUI {

    @Value("${loan.type.housing.yearly-interest-rate:}") 
    private double yearlyHousingInterestRate;

    @Value("${loan.payback-plan:}") 
    private String paybackPlan;

    public void launchConsoleApp(LoanService loanService, LoanType loanType) {

        double loanAmount = 0;
        int paybackTime = 0;

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println(UtilityConstants.FRAME_ONE);

            try {
                System.out.print("Loan amount: ");
                if (scanner.hasNext()) {
                    loanAmount = Double.parseDouble(scanner.nextLine());
                    if (loanAmount <= 0) {
                        throw new ValidationException("Loan amount should be a positive value!");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                return;
            } catch (Exception e) {
                System.out.println("Wrong amount format!");
                System.out.println(UtilityConstants.FRAME_ONE);
                return;
            }
            
            try {
                System.out.print("Payback time (years): ");
                if (scanner.hasNext()) {
                    paybackTime = Integer.parseInt(scanner.nextLine());
                    if (paybackTime <= 0) {
                        throw new ValidationException("Payback time should be a positive value!");
                    }
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                return;
            } catch (Exception e) {
                System.out.println("Wrong payback time format!");
                System.out.println(UtilityConstants.FRAME_ONE);
                return;
            }
            
            System.out.println(UtilityConstants.FRAME_TWO);
            System.out.printf("%.2f loan plan for %d years (%d months)%n", 
                loanAmount, paybackTime, paybackTime * UtilityConstants.YEARLY_MONTHS);

            if (loanType.equals(LoanType.HOUSING_LOAN)) {
                HousingLoan loan = new HousingLoan(loanAmount, paybackTime, yearlyHousingInterestRate);
                launchCalculation(loan, loanService);
            } else {
                System.out.println("To be implemented...");
            }

            System.out.println(UtilityConstants.FRAME_ONE);
        }
    }

    public void launchCalculation(BaseLoan loan, LoanService loanService) {
        System.out.printf("Loan type: %s%n", loan.getLoanType());
        System.out.printf("Yearly interest rate (%%): %s%n", Double.toString(loan.getInterestRate()));

        if (paybackPlan.equals("monthly")) {
            loanService.createMonthlyReport(loan.getAmount(), loan.getMonths(), loan.getInterestRate());
        }

        printMonthlyPlan(loanService);
    }

    public void printMonthlyPlan(LoanService loanService) {
        int year = 1;
        System.out.printf("%n===YEAR %d PLAN===%n", year);

        int monthlyReportListSize = loanService.getMonthlyReportList().size();

        for (int i = 1; i <= monthlyReportListSize; i++) {
            MonthlyReport monthlyReport = loanService.getMonthlyReportList().get(i - 1);
            System.out.println("Month " + i);
            System.out.printf("Beginning balance: %.2f || Total paid: %.2f | Interest paid: %.2f | Principal: %.2f || Ending balance: %.2f%n",
                monthlyReport.getBeginningBalance(),
                monthlyReport.getTotalPerMonth(),
                monthlyReport.getInterest(),
                monthlyReport.getPrincipal(),
                monthlyReport.getEndingBalance()
            );

            if ((i != monthlyReportListSize) && (i % UtilityConstants.YEARLY_MONTHS == 0)) {
                year++;
                System.out.printf("%n===YEAR %d PLAN===%n", year);
            }
        }   
    }

    public void showHelp() {
        System.out.println("Usage: <app> <argument>");
        System.out.println("Available arguments:");
        System.out.println("help: shows help");
        System.out.println("housing: starts housing loan calculator");
    }

}
