package org.example.model.loan;

import org.example.constants.UtilityConstants;
import org.example.model.enums.LoanType;

import lombok.Getter;
import lombok.Setter;

@Setter
public class HousingLoan extends BaseLoan {

    private double amount;
    private int loanTerm;
    private double interestRate;

    public HousingLoan(double amount, int loanTerm, double interestRate) {
        super(LoanType.HOUSING_LOAN);
        this.amount = amount;
        this.loanTerm = loanTerm;
        this.interestRate = interestRate;
    }

    @Override
    public LoanType getLoanType() {
        return LoanType.HOUSING_LOAN;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public int getMonths() {
        return this.loanTerm * UtilityConstants.YEARLY_MONTHS;
    }

}
