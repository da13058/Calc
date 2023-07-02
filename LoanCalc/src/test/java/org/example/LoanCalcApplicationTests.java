package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.commons.math3.util.Precision;
import org.example.model.loan.HousingLoan;
import org.example.model.report.MonthlyReport;
import org.example.services.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class LoanCalcApplicationTests {

    @Autowired
    private LoanService loanService;

    @Value("${loan.type.housing.yearly-interest-rate:}")
    private double yearlyInterestRate;

    @Test
    public void validateMonthlyReport() {
        HousingLoan housingLoan = createTestHousingLoan();

        loanService.createMonthlyReport(housingLoan.getAmount(), housingLoan.getMonths(), housingLoan.getInterestRate());
        List<MonthlyReport> monthlyReportList = loanService.getMonthlyReportList();

        assertEquals(120, monthlyReportList.size());

        MonthlyReport monthlyReportFirst = monthlyReportList.get(0);
        MonthlyReport monthlyReportHundred = monthlyReportList.get(99);
        MonthlyReport monthlyReportLast = monthlyReportList.get(housingLoan.getMonths() - 1);

        assertEquals(41.83, monthlyReportFirst.getPrincipal());
        assertEquals(17.50, monthlyReportFirst.getInterest());
        assertEquals(6000, monthlyReportFirst.getBeginningBalance());

        assertEquals(1206.87, monthlyReportHundred.getBeginningBalance());
        assertEquals(55.81, monthlyReportHundred.getPrincipal());
        assertEquals(3.52, monthlyReportHundred.getInterest());
        assertEquals(59.33, monthlyReportHundred.getTotalPerMonth());
        assertEquals(1151.05, monthlyReportHundred.getEndingBalance());

        assertEquals(59.16, monthlyReportLast.getPrincipal());
        assertEquals(0.17, monthlyReportLast.getInterest());
        assertEquals(0, monthlyReportLast.getEndingBalance());
    }

    @Test
    public void validateMonthlyPayment() {
        HousingLoan housingLoan = createTestHousingLoan();
        double monthlyInterestRate = (housingLoan.getInterestRate() / 100) / 12;

        double monthlyPayment = loanService.calculateMonthlyPayment(
            housingLoan.getAmount(), housingLoan.getMonths(), monthlyInterestRate);
        assertEquals(59.33, Precision.round(monthlyPayment, 2));
    }
    
    private HousingLoan createTestHousingLoan() {
        return new HousingLoan(6000, 10, yearlyInterestRate);
    }
}
