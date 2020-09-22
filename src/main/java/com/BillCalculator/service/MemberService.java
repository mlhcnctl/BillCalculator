package com.BillCalculator.service;

import com.BillCalculator.constant.ErrorCodes;
import com.BillCalculator.dto.RequestModel;
import com.BillCalculator.dto.ResponseData;
import com.BillCalculator.dto.ResponseModel;
import com.BillCalculator.entity.MemberEntity;
import com.BillCalculator.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseModel createMember(RequestModel requestModel) {

        ResponseModel responseModel = new ResponseModel();
        ResponseData responseData = new ResponseData();

        try {
            MemberEntity memberEntity = new MemberEntity();

            memberEntity.setFullName(requestModel.getName());
            memberEntity.setActive(true);
            memberRepository.save(memberEntity);

            responseData.setErrorCode(ErrorCodes.SUCCESS);
            responseData.setErrorExplanation(ErrorCodes.SUCCESS_EXPLANATION);

            responseModel.setResponse(responseData);

        } catch (Exception ex) {
            responseData.setErrorCode(ErrorCodes.FAILED);
            responseData.setErrorExplanation(ErrorCodes.FAILED_EXPLANATION);
            responseModel.setResponse(responseData);
            return  responseModel;
        }

        return responseModel;
    }

}
