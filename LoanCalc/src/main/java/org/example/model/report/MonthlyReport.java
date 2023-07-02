package org.example.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyReport {

    private double beginningBalance;
    private double endingBalance;
    private double interest;
    private double principal;
    private double totalPerMonth;

}
