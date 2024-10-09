package com.senne.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    private String acountNumber;
    private String accountHolderName;
    private String isfeCode;

}
