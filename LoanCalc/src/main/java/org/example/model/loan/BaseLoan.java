package org.example.model.loan;

import org.example.model.enums.LoanType;

public abstract class BaseLoan {

    private LoanType loanType;

    
    protected BaseLoan(LoanType loanType) {
        this.loanType = loanType;
    }

    public LoanType getLoanType(){
        return loanType;
    }

    public abstract double getAmount();
    public abstract int getMonths();
    public abstract double getInterestRate();

}
