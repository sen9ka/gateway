package ru.senya.gateway.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.senya.gateway.clients.DealClient;
import ru.senya.gateway.entity.dto.CreditDTO;
import ru.senya.gateway.entity.dto.FinishRegistrationRequestDTO;
import ru.senya.gateway.entity.dto.ScoringDataDTO;
import ru.senya.gateway.entity.models.Credit;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealClient dealClient;

    public CreditDTO makePostRequestToCalculateCredit(FinishRegistrationRequestDTO finishRegistrationRequestDTO, String calculationsUrl) {
        return dealClient.createOfferToCalculateCredit(finishRegistrationRequestDTO, calculationsUrl);
    }

    public ResponseEntity<?> makePostRequestToSendDocuments(String sendDocumentsUrl) {
        return dealClient.createOfferToSendDocuments(sendDocumentsUrl);
    }

    public ResponseEntity<?> makePostRequestToSignDocuments(String signDocumentsUrl) {
        return dealClient.createOfferToSignDocuments(signDocumentsUrl);
    }

    public ResponseEntity<?> makePostRequestToSendCode(String codeUrl) {
        return dealClient.createOfferToCode(codeUrl);
    }

}
