package com.vizzibl.velocityservice.controller;

import com.vizzibl.velocityservice.request.OfferRequest;
import com.vizzibl.velocityservice.response.ResponseObject;
import com.vizzibl.velocityservice.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/velocity")
public class VelocityController {



    @Autowired
    OfferService offerService;

    @RequestMapping("/offer/create")
    public ResponseEntity<ResponseObject> createOffer(@RequestBody OfferRequest offerRequest){
        return ResponseEntity.ok(offerService.createOffer(offerRequest));

    }
}
