package com.vizzibl.constants;

public class EmailConstants {

    public static final String REGISTRATION_CONTENT = """
            Dear %s %s,

            Welcome to the Vizzibl Digital Credentialing Platform. You are recieving this notification because a system administrator has added you to the Platform, 
            thereby making you eligible to earn digital credentials based on individual program criteria. 

            After validating your email address, you will have access to your personal student wallet, where you can review and share earned credentials. 

            Use the login details below to access the student portal.

            username: %s
            Password: %s

            Login: http://dsp.badgecloud.io


            Cheers!
            Vizzibl Credentialing Team


            """;


    public static final String REGISTRATION_TITLE = "Access Your Vizzibl Student Wallet";

    public static final String FROM = "smtp@icommunicate.io";


    public static final String BADGE_CONTENT = "Dear %s %s,\n\n" +
            "Congratulations on your recent accomplishment of earning a credential in %s from %s!\n\n" +
            "Having met the statutory requirements for the %s under the seal of %s and validated by Vizzibl, we are pleased to inform you that you have been awarded a digital badge Certification.\n\n" +
            "The digital badge represents your certification in a secure, validated, shareable digital medium and recognizes the core competencies you have acquired through %s.\n\n" +
            "The digital badge is evidence of your participation and completion of the requirements of the Program, and can be shared directly to LinkedIn.\n" +
            "Use the below link to access your Student Wallet and view your verified achievement:\n\n" +
            "Login URL: http://dsp.badgecloud.io/\n\n" +
            "Looking forward to seeing your displayed %s digital badge across all your social media pages!\n\n" +
            "Cheers!\n" +
            "Vizzibl Credentialing Team";


    public static final String BADGE_TITLE = "New Digital Credential From Vizzibl";
    public static final String TYPE_BADGE = "BADGE";
}
