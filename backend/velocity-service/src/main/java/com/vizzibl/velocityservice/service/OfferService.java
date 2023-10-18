package com.vizzibl.velocityservice.service;

import com.vizzibl.velocityservice.request.OfferRequest;
import com.vizzibl.velocityservice.response.ResponseObject;



public interface OfferService {
    ResponseObject createOffer(OfferRequest offerRequest );


}
