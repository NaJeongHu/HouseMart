package com.example.publicdatacompetition.Model;

import java.io.Serializable;

public class License implements Serializable {

    private Long id;
    private String certificateURL;
    private String certificationNumber;
    private String self_introduction;

    public License(Long id, String certificateURL, String certificationNumber, String self_introduction) {
        this.id = id;
        this.certificateURL = certificateURL;
        this.certificationNumber = certificationNumber;
        this.self_introduction = self_introduction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateURL() {
        return certificateURL;
    }

    public void setCertificateURL(String certificateURL) {
        this.certificateURL = certificateURL;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getSelf_introduction() {
        return self_introduction;
    }

    public void setSelf_introduction(String self_introduction) {
        this.self_introduction = self_introduction;
    }
}
