package com.devsu.AccountMoveService.controller;

import com.devsu.AccountMoveService.exception.CustomException;
import com.devsu.AccountMoveService.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    @Autowired
    private ReportServiceImpl reportServiceImpl;

    @GetMapping
    public ResponseEntity<?> generateAccountStatementReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate

    ) {
        try {
            // Call the report service to generate the account statement report
            byte[] reportData = reportServiceImpl.generateAccountStatementReport(startDate, endDate);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentDisposition(ContentDisposition.builder("inline").filename("account_statement.json").build());
            return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
        } catch (DateTimeParseException ex) {
            return new ResponseEntity<>("Invalid date format. Please use 'yyyy-MM-dd' format.", HttpStatus.BAD_REQUEST);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
