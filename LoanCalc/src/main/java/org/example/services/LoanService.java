package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

import org.example.model.report.MonthlyReport;

@Service
public class LoanService {

    private List<MonthlyReport> monthlyReportList = new ArrayList<>();

    public List<MonthlyReport> getMonthlyReportList() {
        return monthlyReportList;
    }

    public void createMonthlyReport(double amount, int months, double interest) {

        double interestPaid;
        double principalPaid;
        double newBalance;
        double totalPaid;

        double monthlyInterestRate = (interest / 100) / 12;

        double monthlyPayment = calculateMonthlyPayment(amount, months, monthlyInterestRate);

        for (int i = 1; i <= months; i++) {
            interestPaid = amount * monthlyInterestRate;
            principalPaid = monthlyPayment - interestPaid;
            newBalance = amount - principalPaid;
            totalPaid = interestPaid + principalPaid;

            MonthlyReport monthlyReport = new MonthlyReport(
                Precision.round(amount,2), 
                Precision.round(newBalance,2), 
                Precision.round(interestPaid, 2), 
                Precision.round(principalPaid, 2),
                Precision.round(totalPaid, 2)
            );

            amount = newBalance;
            monthlyReportList.add(monthlyReport);

        }
    }

    public double calculateMonthlyPayment(double amount, int months, double monthlyInterestRate) {
        return (amount * monthlyInterestRate) /
            (1 - 1 / Math.pow(1 + monthlyInterestRate, months));
    }
}