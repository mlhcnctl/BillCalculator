package com.BillCalculator.service;

import com.BillCalculator.constant.ErrorCodes;
import com.BillCalculator.dto.ShareholderRequest;
import com.BillCalculator.dto.ResponseData;
import com.BillCalculator.dto.ShareholderResponse;
import com.BillCalculator.entity.ShareholderEntity;
import com.BillCalculator.repository.ShareholderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShareholderService {

    private final ShareholderRepository shareholderRepository;

    public ShareholderResponse createShareholderOfUser(ShareholderRequest shareHolderRequest) {

        ShareholderResponse shareholderResponse = new ShareholderResponse();
        ResponseData responseData = new ResponseData();

        try {
            ShareholderEntity shareholderEntity = new ShareholderEntity();

            shareholderEntity.setFullName(shareHolderRequest.getName());
            shareholderEntity.setActive(true);
            shareholderEntity.setOwnerUsernameOfStakeholder(shareHolderRequest.getOwnerUsernameOfShareHolder());
            shareholderRepository.save(shareholderEntity);

            responseData.setErrorCode(ErrorCodes.SUCCESS);
            responseData.setErrorExplanation(ErrorCodes.SUCCESS_EXPLANATION_SHAREHOLDER);

            shareholderResponse.setResponse(responseData);

        } catch (Exception ex) {
            responseData.setErrorCode(ErrorCodes.FAILED);
            responseData.setErrorExplanation(ErrorCodes.FAILED_EXPLANATION_SHAREHOLDER);
            shareholderResponse.setResponse(responseData);
            return shareholderResponse;
        }

        return shareholderResponse;
    }

}
