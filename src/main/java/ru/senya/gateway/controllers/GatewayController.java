package ru.senya.gateway.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.senya.gateway.entity.dto.CreditDTO;
import ru.senya.gateway.entity.dto.FinishRegistrationRequestDTO;
import ru.senya.gateway.entity.dto.LoanApplicationRequestDTO;
import ru.senya.gateway.entity.dto.LoanOfferDTO;
import ru.senya.gateway.services.ApplicationService;
import ru.senya.gateway.services.DealService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway")
@Tag(name = "Микросервис Gateway")
public class GatewayController {

    @Value("${applicationLink}")
    private String applicationsUrl;

    @Value("${offersLink}")
    private String offersUrl;

    @Value("${calculationLink}")
    private String calculationsUrl;

    @Value("${dealDocumentsLink}")
    private String dealDocumentsUrl;

    @Value("${dealSendDomain}")
    private String dealSendDomain;

    @Value("${dealSignDomain}")
    private String dealSignDomain;

    @Value("${dealCodeDomain}")
    private String dealCodeDomain;

    private final ApplicationService applicationService;
    private final DealService dealService;

    @PostMapping("/application")
    public ResponseEntity<?> getLoanOffers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferDTOList = applicationService.makePostRequestToGetOffers(loanApplicationRequestDTO, applicationsUrl);
        return new ResponseEntity<>(loanOfferDTOList, HttpStatus.OK);
    }

    @PostMapping("/offer")
    public ResponseEntity<?> chooseLoanOffer(@RequestBody LoanOfferDTO loanOfferDTO) {
        return applicationService.makePostRequestToChooseOffer(loanOfferDTO, offersUrl);
    }

    @PostMapping("/calculation/{applicationId}")
    public ResponseEntity<?> calculateCredit(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable Long applicationId) {
        CreditDTO creditDTO = dealService.makePostRequestToCalculateCredit(finishRegistrationRequestDTO, calculationsUrl + applicationId);
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }

    @PostMapping("/send/{applicationId}")
    public ResponseEntity<?> sendDocuments (@PathVariable Long applicationId) {
        return dealService.makePostRequestToSendDocuments(dealDocumentsUrl + applicationId + dealSendDomain);
    }

    @PostMapping("/sign/{applicationId}")
    public ResponseEntity<?> signDocuments (@PathVariable Long applicationId) {
        return dealService.makePostRequestToSignDocuments(dealDocumentsUrl + applicationId + dealSignDomain);
    }

    @PostMapping("/code/{applicationId}")
    public ResponseEntity<?> sendCode (@PathVariable Long applicationId) {
        return dealService.makePostRequestToSendCode(dealDocumentsUrl + applicationId + dealCodeDomain);
    }

}
