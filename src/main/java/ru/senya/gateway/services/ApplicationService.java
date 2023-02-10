package ru.senya.gateway.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.senya.gateway.clients.ApplicationClient;
import ru.senya.gateway.entity.dto.LoanApplicationRequestDTO;
import ru.senya.gateway.entity.dto.LoanOfferDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationClient applicationClient;

    public List<LoanOfferDTO> makePostRequestToGetOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, String applicationsUrl) {
        return applicationClient.createPostRequestToGetOffers(loanApplicationRequestDTO, applicationsUrl);
    }

    public ResponseEntity<?> makePostRequestToChooseOffer(LoanOfferDTO loanOfferDTO, String offersUrl) {

        return applicationClient.createPostRequestToChooseOffer(loanOfferDTO, offersUrl);

    }

}
