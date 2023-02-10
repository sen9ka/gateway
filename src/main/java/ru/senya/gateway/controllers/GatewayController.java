package ru.senya.gateway.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.senya.gateway.config.GatewayProperties;
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

    private final ApplicationService applicationService;
    private final DealService dealService;
    private final GatewayProperties gatewayProperties;

    @PostMapping("/application")
    @Operation(summary = "Получение списка из 4 LoanOfferDTO")
    public ResponseEntity<?> getLoanOffers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferDTOList = applicationService.makePostRequestToGetOffers(loanApplicationRequestDTO, gatewayProperties.getApplicationLink());
        return new ResponseEntity<>(loanOfferDTOList, HttpStatus.OK);
    }

    @PostMapping("/offer")
    @Operation(summary = "Отправка выбранного LoanOffer")
    public ResponseEntity<?> chooseLoanOffer(@RequestBody LoanOfferDTO loanOfferDTO) {
        return applicationService.makePostRequestToChooseOffer(loanOfferDTO, gatewayProperties.getOffersLink());
    }

    @PostMapping("/calculation/{applicationId}")
    @Operation(summary = "Получение полностью рассчитанного кредита")
    public ResponseEntity<?> calculateCredit(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable Long applicationId) {
        CreditDTO creditDTO = dealService.makePostRequestToCalculateCredit(finishRegistrationRequestDTO, gatewayProperties.getCalculationLink() + applicationId);
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }

    @PostMapping("/send/{applicationId}")
    @Operation(summary = "Формирование документов по кредиту")
    public ResponseEntity<?> sendDocuments (@PathVariable Long applicationId) {
        return dealService.makePostRequestToSendDocuments(gatewayProperties.getDealDocumentsLink() + applicationId + gatewayProperties.getDealSendDomain());
    }

    @PostMapping("/sign/{applicationId}")
    @Operation(summary = "Запрос на подписание документов, отправка 4-х значного кода")
    public ResponseEntity<?> signDocuments (@PathVariable Long applicationId) {
        return dealService.makePostRequestToSignDocuments(gatewayProperties.getDealDocumentsLink() + applicationId + gatewayProperties.getDealSignDomain());
    }

    @PostMapping("/code/{applicationId}")
    @Operation(summary = "Подписание документов")
    public ResponseEntity<?> sendCode (@PathVariable Long applicationId) {
        return dealService.makePostRequestToSendCode(gatewayProperties.getDealDocumentsLink() + applicationId + gatewayProperties.getDealCodeDomain());
    }

}
