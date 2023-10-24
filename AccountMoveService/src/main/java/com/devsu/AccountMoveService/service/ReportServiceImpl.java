package com.devsu.AccountMoveService.service;

import com.devsu.AccountMoveService.config.LocalDateJacksonConfig;
import com.devsu.AccountMoveService.dto.AccountStatementReportDTO;
import com.devsu.AccountMoveService.entity.Account;
import com.devsu.AccountMoveService.entity.Move;
import com.devsu.AccountMoveService.repository.AccountRepository;
import com.devsu.AccountMoveService.repository.MoveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Log4j2
public class ReportServiceImpl implements IReportService {

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LocalDateJacksonConfig localDateJacksonConfig;

    public byte[] generateAccountStatementReport(LocalDate startDate, LocalDate endDate) {
        // Retrieve the customer's associated accounts
        List<Account> accounts = accountRepository.findAll();

        // Create a list to store the report data
        List<AccountStatementReportDTO> reportData = new ArrayList<>();

        for (Account account : accounts) {
            // Retrieve transactions within the specified date range for each account
            List<Move> transactions = moveRepository.findByAccountIdAndDateRange(
                    account.getId(), startDate, endDate);

            // Iterate through the transactions and create a report entry for each
            for (Move move : transactions) {
                AccountStatementReportDTO reportEntry = getAccountStatementReportDTO(account, move);

                reportData.add(reportEntry);
            }
        }

        // Generate the report in JSON format
        return generateReportInJson(reportData);
    }

    private static AccountStatementReportDTO getAccountStatementReportDTO(Account account, Move move) {
        AccountStatementReportDTO reportEntry = new AccountStatementReportDTO();
        reportEntry.setMoveDate(LocalDate.from(move.getDate()));
        reportEntry.setAccountName(account.getName());
        reportEntry.setAccountNumber(account.getNumacc());
        reportEntry.setAccountType(String.valueOf(account.getType()));
        reportEntry.setAccountInitial(account.getInitial());
        reportEntry.setAccountStatus(String.valueOf(account.getStatus()));
        reportEntry.setMoveValor(move.getValor());
        reportEntry.setMoveBalance(move.getBalance());
        return reportEntry;
    }

    private byte[] generateReportInJson(List<AccountStatementReportDTO> reportData) {
        try {
            // Inject the custom ObjectMapper for LocalDate
            ObjectMapper objectMapper = localDateJacksonConfig.localDateObjectMapper(); // Use the custom ObjectMapper

            // Serialize the list of AccountStatementReportDTO objects to JSON
            String jsonReport = objectMapper.writeValueAsString(reportData);

            // Convert the JSON string to bytes
            return jsonReport.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Handle any exceptions that may occur during JSON serialization
            e.printStackTrace();
            return new byte[0]; // Return an empty byte array in case of an error
        }
    }



}
