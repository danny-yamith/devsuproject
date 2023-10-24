package com.devsu.AccountMoveService.service;

import java.time.LocalDate;

public interface IReportService {

    byte[] generateAccountStatementReport(LocalDate startDate, LocalDate endDate);
}
