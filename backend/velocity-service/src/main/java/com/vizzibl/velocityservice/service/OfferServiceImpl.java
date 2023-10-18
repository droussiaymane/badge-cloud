package com.vizzibl.velocityservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vizzibl.velocityservice.request.OfferRequest;
import com.vizzibl.velocityservice.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
public class OfferServiceImpl implements OfferService{

    @Autowired
    MailService mailService;
    @Override
    public ResponseObject createOffer(OfferRequest offerRequest) {

        try{
            createBadgeJsonFile(offerRequest);
            log.info("BadgeJson file created successfully...");
        }catch (IOException e) {
            log.error("Error creating badgeJsonFile : "+e.getMessage());
        }

        createCSVFile(offerRequest);
        log.info("CSV file created successfully...");



        // create the qr code

        try{
            createQrCode();
            log.info("Qr code created successfully");
        }
        catch (Exception e) {
            log.error("Error creating qrcode : "+e.getMessage());
        }



        //ClassPathResource qrcodeimage = new ClassPathResource("./velocity-service/tmp/qrcode-generic.png");

        FileSystemResource qrcodeimage = new FileSystemResource(new File("./velocity-service/tmp/qrcode-generic.png"));

        String emailDestination=offerRequest.getUserEmail();
        String objetMessage="Congratulations, your badge is ready!";;
        String message = "Dear " +offerRequest.getFirstName() + " " + offerRequest.getLastName() + ", \n\nAttached to this email is your QR Code for the Velocity Service. Please scan this code to claim your badge. \n\nKind regards, \nYour Velocity Service Team";

        FileSystemResource file = new FileSystemResource(new File("./velocity-service/tmp/qrcode-generic.png"));





        String testMessage="<table style=\"border-collapse:collapse;border:none;\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 467.5pt;border: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-size:19px;font-family:Helvetica;color:#0E101A;\">Own your credentials and use them to prove your skills!</span></strong></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">Congratulations on completing "+offerRequest.getBadgeName()+" at Vizzibl! As part of our innovative learning experience, Vizzibl offers you a tamper-proof, verified, reusable copy of your new c</span><span style=\"font-family:Helvetica;\">redential.</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">Through the power of Vizzibl and Velocity Career Labs, Vizzibl is giving you a safe and secure way to share your success with employers and others. It&apos;s easy and completely free, and you can use it whenever and wherever you are required to prove your skills.&nbsp;</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-size:19px;font-family:Helvetica;color:#0E101A;\">Let&apos;s get started!</span></strong></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 467.5pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-family:Helvetica;color:#0E101A;\">Step 1 of 3</span></strong></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-size:19px;font-family:Helvetica;color:#0E101A;\">Download a Velocity Career Wallet</span></strong></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">To be able to store a verified copy of your credential, you will need a career wallet app installed on your smartphone.</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">The free app enables you to secure your records and share them only with whom you decide to.</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:12.0pt;text-align:right;'><span style=\"font-family:Helvetica;color:#0E101A;\">To learn more about Velocity Career Wallets,&nbsp;</span><a href=\"https://www.velocitynetwork.foundation/\" target=\"_blank\"><span style=\"font-family:Helvetica;color:red;\">CLICK HERE</span></a><span style=\"font-family:Helvetica;color:#0E101A;\">.&nbsp;</span></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 67.25pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;\">\n" +

getDataImgMobile()+

                "            </td>\n" +
                "            <td colspan=\"3\" style=\"width: 400.25pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style=\"font-family:Helvetica;color:#0E101A;\">Grab your smartphone, visit the app store on your device, search for <strong><span style=\"font-family:Helvetica;\">Velocity Career Wallet</span></strong>, and install the app.</span></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 467.5pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-top:6.0pt;margin-right:0cm;margin-bottom:6.0pt;margin-left:  0cm;'><strong><span style=\"font-family:Helvetica;color:#0E101A;\">Step 2 of 3</span></strong></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-size:19px;font-family:Helvetica;color:#0E101A;\">Verify your phone and email</span></strong><span style=\"font-size:19px;font-family:Helvetica;color:#0E101A;\">.</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">Once the app is installed, you will need your email and phone number verified. This allows us to ensure you own the wallet where we will send your copy of the assessment results or credentials.</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-family:  Helvetica;color:#0E101A;\">Important Note!&nbsp;</span></strong><span style=\"font-family:Helvetica;color:#0E101A;\">Make sure to use the email and phone number you have provided to us when filling out your application.</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-family:Helvetica;color:#0E101A;\">Step 3 of 3</span></strong></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><strong><span style=\"font-size:19px;font-family:Helvetica;color:#0E101A;\">Claim a verified copy of your new credential!</span></strong></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">Once your email and phone number are verified, we can issue a verified copy to your wallet.</span></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"2\" style=\"width: 155.8pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;line-height:22.5pt;'><strong><span style=\"font-size:20px;font-family:Helvetica;color:#333333;\">&nbsp;</span></strong></p>\n" +
                "            </td>\n" +
                "            <td style=\"width: 155.85pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;line-height:22.5pt;'><span style=\"font-size:1px;\"><img src='cid:myqrcode' alt=\"A black background with white squaresDescription automatically generated with low confidence\" width=\"138\" height=\"138\"></span></p>\n" +
                "            </td>\n" +
                "            <td style=\"width: 155.85pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;line-height:22.5pt;'><strong><span style=\"font-size:20px;font-family:Helvetica;color:#333333;\">&nbsp;</span></strong></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"4\" style=\"width: 467.5pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;\">\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-top:12.0pt;margin-right:0cm;margin-bottom:6.0pt;margin-left:  0cm;'><span style=\"font-family:Helvetica;color:#0E101A;\">If you are reading this message on your computer, grab your phone, open your career wallet app, and scan this QR code on the screen.</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">If you are using your mobile device,&nbsp;</span><a href=\"https://www.velocitynetwork.foundation/\" target=\"_blank\"><span style=\"font-family:Helvetica;color:red;\">CLICK HERE</span></a><span style=\"font-family:Helvetica;color:#0E101A;\">.&nbsp;</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">&nbsp;If you are experiencing any issues when claiming your records, please connect with us:&nbsp;</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">Our easy-to-follow&nbsp;</span><span style=\"font-family:Helvetica;color:red;\">FAQ</span><span style=\"font-family:Helvetica;color:#0E101A;\">s solve most issues.&nbsp;</span></p>\n" +
                "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;margin-bottom:6.0pt;'><span style=\"font-family:Helvetica;color:#0E101A;\">Our&nbsp;</span><span style=\"font-family:Helvetica;color:red;\">Help Desk</span><span style=\"font-family:Helvetica;color:#0E101A;\">&nbsp;can get you up and running.</span></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"border:none;\"><br></td>\n" +
                "            <td style=\"border:none;\"><br></td>\n" +
                "            <td style=\"border:none;\"><br></td>\n" +
                "            <td style=\"border:none;\"><br></td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "<p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>";
        // send mail to the user with the qr code attached
        mailService.sendEmail(emailDestination,testMessage,objetMessage,qrcodeimage);




        return null;
    }




    public void createBadgeJsonFile(OfferRequest offerRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.putArray("type").add("OpenBadgeV2.0");

        ObjectNode credentialSubjectNode = rootNode.putObject("credentialSubject");
        credentialSubjectNode.put("vendorUserId", "{{{email}}}");

        ObjectNode hasCredentialNode = credentialSubjectNode.putObject("hasCredential");

        // Set the 'name' and 'image' fields
        hasCredentialNode.put("name", offerRequest.getBadgeName());
        hasCredentialNode.put("image", offerRequest.getBadgeImage());

        hasCredentialNode.put("description", "Digital Badge for the Conference Attendees of Microsoft's Inspire Conference.");
        hasCredentialNode.put("type", "BadgeClass3");

        ObjectNode issuerNode = hasCredentialNode.putObject("issuer");
        issuerNode.put("type", "Profile");
        issuerNode.put("id", "{{{did}}}");
        issuerNode.put("name", offerRequest.getBadgeName());

        hasCredentialNode.put("criteria", "https://www.credly.com/org/talent-path/badge/full-stack-developer");


            objectMapper.writeValue(new File("./velocity-service/batch-issue-in-depth/openbadge.json"), rootNode);

    }


    public void createCSVFile(OfferRequest offerRequest) {
        // the directory where you want to store the CSV file
        String directoryPath = "./velocity-service/batch-issue-in-depth/";
        File directory = new File(directoryPath);
        if (! directory.exists()){
            directory.mkdir();
        }
        String csvFile = directoryPath + "sample_individuals.csv";
        String csvHeader = "email,givenName,familyName";
        String email = offerRequest.getUserEmail();
        String givenName = offerRequest.getFirstName();
        String familyName = offerRequest.getLastName();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append(csvHeader);
            writer.append("\n");
            writer.append(email);
            writer.append(",");
            writer.append(givenName);
            writer.append(",");
            writer.append(familyName);
            writer.append("\n");
            log.info("CSV file created successfully...");
        } catch (IOException e) {
            log.error("An error occurred... on saving csv file");
            e.printStackTrace();
        }
    }


    public void createQrCode(){
        ProcessBuilder processBuilder = new ProcessBuilder(
                "npx",
                "@velocitynetworkfoundation/data-loader",
                "batchissuing",
                "-d",
                "did:ion:EiAOJzQn8mOU7jofLEXfROxG_oR9Fc_JeXs1KzonGNaHpQ",
                "-o",
                "./velocity-service/batch-issue-in-depth/openbadge.json",
                "-c",
                "./velocity-service/batch-issue-in-depth/sample_individuals.csv",
                "-p",
                "./velocity-service/tmp",
                "-t",
                "https://www.example.com/terms.html",
                "-e",
                "http://66.94.108.4:8080",
                "-a",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoidmVsb2NpdHkuYWRtaW5AZXhhbXBsZS5jb20ifQ.vssTp--KThrqP0-E7BOpXfTneXmQUYcv0elKAcXhAVg"
        );

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // handle non-zero exit code
            }
        } catch (IOException | InterruptedException e) {
            // handle exception
            System.out.println(e.getMessage());
        }
    }


    public String getDataImgMobile(){
        String img = "                <p style='margin:0cm;font-size:15px;font-family:\"Calibri\",sans-serif;line-height:22.5pt;'><span style=\"font-size:1px;\"><img width=\"64\" src=\"https://cdn-icons-png.flaticon.com/512/71/71729.png\" alt=\"A picture containing black, darknessDescription automatically generated\"></span></p>\n" ;
        return img;
    }
}

