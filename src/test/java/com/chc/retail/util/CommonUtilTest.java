package com.chc.retail.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CommonUtil.class})
@ActiveProfiles({"DEV"})
@ExtendWith(SpringExtension.class)
class CommonUtilTest {
    @Autowired
    private CommonUtil commonUtil;


    @Test
    void givenMaskedEmailPerformsWhenDifferentTypesOfDataProvided() {
        assertEquals("jan*****@example.org", this.commonUtil.maskEmail("jane.doe@example.org"));
        assertEquals("UUU*", this.commonUtil.maskEmail("UUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("$1*", this.commonUtil.maskEmail("$1*"));
        assertEquals("Ema**", this.commonUtil.maskEmail("Email"));
        assertEquals("jan*****@example.orgjane.doe@example.org",
                this.commonUtil.maskEmail("jane.doe@example.orgjane.doe@example.org"));
        assertEquals("jan*****@example.orgUUUU", this.commonUtil.maskEmail("jane.doe@example.orgUUUU"));
        assertEquals("jan*****@example.org(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("jan*****@example.org$1*", this.commonUtil.maskEmail("jane.doe@example.org$1*"));
        assertEquals("jan*****@example.orgEmail", this.commonUtil.maskEmail("jane.doe@example.orgEmail"));
        assertEquals("jan*****@example.org42", this.commonUtil.maskEmail("jane.doe@example.org42"));
        assertEquals("UUU*********@example.org", this.commonUtil.maskEmail("UUUUjane.doe@example.org"));
        assertEquals("UUU*****", this.commonUtil.maskEmail("UUUUUUUU"));
        assertEquals("UUU*****@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("UUUU(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("UUU****", this.commonUtil.maskEmail("UUUU$1*"));
        assertEquals("UUU******", this.commonUtil.maskEmail("UUUUEmail"));
        assertEquals("UUU***", this.commonUtil.maskEmail("UUUU42"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]jane.doe@example.org",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]UUUU", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]UUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]$1*", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]$1*"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]Email", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]Email"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]42", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]42"));
        assertEquals("$1*********@example.org", this.commonUtil.maskEmail("$1*jane.doe@example.org"));
        assertEquals("$1*****", this.commonUtil.maskEmail("$1*UUUU"));
        assertEquals("$1*****@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("$1*(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("$1****", this.commonUtil.maskEmail("$1*$1*"));
        assertEquals("$1******", this.commonUtil.maskEmail("$1*Email"));
        assertEquals("$1***", this.commonUtil.maskEmail("$1*42"));
        assertEquals("Ema**********@example.org", this.commonUtil.maskEmail("Emailjane.doe@example.org"));
        assertEquals("Ema******", this.commonUtil.maskEmail("EmailUUUU"));
        assertEquals("Ema******@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("Email(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("Ema*****", this.commonUtil.maskEmail("Email$1*"));
        assertEquals("Ema*******", this.commonUtil.maskEmail("EmailEmail"));
        assertEquals("Ema****", this.commonUtil.maskEmail("Email42"));
        assertEquals("42j*******@example.org", this.commonUtil.maskEmail("42jane.doe@example.org"));
        assertEquals("42U***", this.commonUtil.maskEmail("42UUUU"));
        assertEquals("42(***@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("42(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("42$**", this.commonUtil.maskEmail("42$1*"));
        assertEquals("42E****", this.commonUtil.maskEmail("42Email"));
        assertEquals("424*", this.commonUtil.maskEmail("4242"));
        assertEquals("jan*****@example.orgjane.doe@example.orgjane.doe@example.org",
                this.commonUtil.maskEmail("jane.doe@example.orgjane.doe@example.orgjane.doe@example.org"));
        assertEquals("jan*****@example.orgjane.doe@example.orgUUUU",
                this.commonUtil.maskEmail("jane.doe@example.orgjane.doe@example.orgUUUU"));
        assertEquals("jan*****@example.orgjane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("jane.doe@example.orgjane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("jan*****@example.orgjane.doe@example.org$1*",
                this.commonUtil.maskEmail("jane.doe@example.orgjane.doe@example.org$1*"));
        assertEquals("jan*****@example.orgjane.doe@example.orgEmail",
                this.commonUtil.maskEmail("jane.doe@example.orgjane.doe@example.orgEmail"));
        assertEquals("jan*****@example.orgjane.doe@example.org42",
                this.commonUtil.maskEmail("jane.doe@example.orgjane.doe@example.org42"));
        assertEquals("jan*****@example.orgUUUUjane.doe@example.org",
                this.commonUtil.maskEmail("jane.doe@example.orgUUUUjane.doe@example.org"));
        assertEquals("jan*****@example.orgUUUUUUUU", this.commonUtil.maskEmail("jane.doe@example.orgUUUUUUUU"));
        assertEquals("jan*****@example.orgUUUU(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("jane.doe@example.orgUUUU(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("jan*****@example.orgUUUU$1*", this.commonUtil.maskEmail("jane.doe@example.orgUUUU$1*"));
        assertEquals("jan*****@example.orgUUUUEmail", this.commonUtil.maskEmail("jane.doe@example.orgUUUUEmail"));
        assertEquals("jan*****@example.orgUUUU42", this.commonUtil.maskEmail("jane.doe@example.orgUUUU42"));
        assertEquals("jan*****@example.org(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org",
                this.commonUtil.maskEmail("jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org"));
        assertEquals("jan*****@example.org(^[^@]{3}|(?!^)\\G)[^@]UUUU",
                this.commonUtil.maskEmail("jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]UUUU"));
        assertEquals("jan*****@example.org(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("jan*****@example.org(^[^@]{3}|(?!^)\\G)[^@]$1*",
                this.commonUtil.maskEmail("jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]$1*"));
        assertEquals("jan*****@example.org(^[^@]{3}|(?!^)\\G)[^@]Email",
                this.commonUtil.maskEmail("jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]Email"));
        assertEquals("jan*****@example.org(^[^@]{3}|(?!^)\\G)[^@]42",
                this.commonUtil.maskEmail("jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]42"));
        assertEquals("jan*****@example.org$1*jane.doe@example.org",
                this.commonUtil.maskEmail("jane.doe@example.org$1*jane.doe@example.org"));
        assertEquals("jan*****@example.org$1*UUUU", this.commonUtil.maskEmail("jane.doe@example.org$1*UUUU"));
        assertEquals("jan*****@example.org$1*(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("jane.doe@example.org$1*(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("jan*****@example.org$1*$1*", this.commonUtil.maskEmail("jane.doe@example.org$1*$1*"));
        assertEquals("jan*****@example.org$1*Email", this.commonUtil.maskEmail("jane.doe@example.org$1*Email"));
        assertEquals("jan*****@example.org$1*42", this.commonUtil.maskEmail("jane.doe@example.org$1*42"));
        assertEquals("jan*****@example.orgEmailjane.doe@example.org",
                this.commonUtil.maskEmail("jane.doe@example.orgEmailjane.doe@example.org"));
        assertEquals("jan*****@example.orgEmailUUUU", this.commonUtil.maskEmail("jane.doe@example.orgEmailUUUU"));
        assertEquals("jan*****@example.orgEmail(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("jane.doe@example.orgEmail(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("jan*****@example.orgEmail$1*", this.commonUtil.maskEmail("jane.doe@example.orgEmail$1*"));
        assertEquals("jan*****@example.orgEmailEmail", this.commonUtil.maskEmail("jane.doe@example.orgEmailEmail"));
        assertEquals("jan*****@example.orgEmail42", this.commonUtil.maskEmail("jane.doe@example.orgEmail42"));
        assertEquals("jan*****@example.org42jane.doe@example.org",
                this.commonUtil.maskEmail("jane.doe@example.org42jane.doe@example.org"));
        assertEquals("jan*****@example.org42UUUU", this.commonUtil.maskEmail("jane.doe@example.org42UUUU"));
        assertEquals("jan*****@example.org42(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("jane.doe@example.org42(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("jan*****@example.org42$1*", this.commonUtil.maskEmail("jane.doe@example.org42$1*"));
        assertEquals("jan*****@example.org42Email", this.commonUtil.maskEmail("jane.doe@example.org42Email"));
        assertEquals("jan*****@example.org4242", this.commonUtil.maskEmail("jane.doe@example.org4242"));
        assertEquals("UUU*********@example.orgjane.doe@example.org",
                this.commonUtil.maskEmail("UUUUjane.doe@example.orgjane.doe@example.org"));
        assertEquals("UUU*********@example.orgUUUU", this.commonUtil.maskEmail("UUUUjane.doe@example.orgUUUU"));
        assertEquals("UUU*********@example.org(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("UUUUjane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("UUU*********@example.org$1*", this.commonUtil.maskEmail("UUUUjane.doe@example.org$1*"));
        assertEquals("UUU*********@example.orgEmail", this.commonUtil.maskEmail("UUUUjane.doe@example.orgEmail"));
        assertEquals("UUU*********@example.org42", this.commonUtil.maskEmail("UUUUjane.doe@example.org42"));
        assertEquals("UUU*************@example.org", this.commonUtil.maskEmail("UUUUUUUUjane.doe@example.org"));
        assertEquals("UUU*********", this.commonUtil.maskEmail("UUUUUUUUUUUU"));
        assertEquals("UUU*********@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("UUUUUUUU(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("UUU********", this.commonUtil.maskEmail("UUUUUUUU$1*"));
        assertEquals("UUU**********", this.commonUtil.maskEmail("UUUUUUUUEmail"));
        assertEquals("UUU*******", this.commonUtil.maskEmail("UUUUUUUU42"));
        assertEquals("UUU*****@]{3}|(?!^)\\G)[^@]jane.doe@example.org",
                this.commonUtil.maskEmail("UUUU(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org"));
        assertEquals("UUU*****@]{3}|(?!^)\\G)[^@]UUUU", this.commonUtil.maskEmail("UUUU(^[^@]{3}|(?!^)\\G)[^@]UUUU"));
        assertEquals("UUU*****@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("UUUU(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("UUU*****@]{3}|(?!^)\\G)[^@]$1*", this.commonUtil.maskEmail("UUUU(^[^@]{3}|(?!^)\\G)[^@]$1*"));
        assertEquals("UUU*****@]{3}|(?!^)\\G)[^@]Email", this.commonUtil.maskEmail("UUUU(^[^@]{3}|(?!^)\\G)[^@]Email"));
        assertEquals("UUU*****@]{3}|(?!^)\\G)[^@]42", this.commonUtil.maskEmail("UUUU(^[^@]{3}|(?!^)\\G)[^@]42"));
        assertEquals("UUU************@example.org", this.commonUtil.maskEmail("UUUU$1*jane.doe@example.org"));
        assertEquals("UUU********", this.commonUtil.maskEmail("UUUU$1*UUUU"));
        assertEquals("UUU********@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("UUUU$1*(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("UUU*******", this.commonUtil.maskEmail("UUUU$1*$1*"));
        assertEquals("UUU*********", this.commonUtil.maskEmail("UUUU$1*Email"));
        assertEquals("UUU******", this.commonUtil.maskEmail("UUUU$1*42"));
        assertEquals("UUU**************@example.org", this.commonUtil.maskEmail("UUUUEmailjane.doe@example.org"));
        assertEquals("UUU**********", this.commonUtil.maskEmail("UUUUEmailUUUU"));
        assertEquals("UUU**********@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("UUUUEmail(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("UUU*********", this.commonUtil.maskEmail("UUUUEmail$1*"));
        assertEquals("UUU***********", this.commonUtil.maskEmail("UUUUEmailEmail"));
        assertEquals("UUU********", this.commonUtil.maskEmail("UUUUEmail42"));
        assertEquals("UUU***********@example.org", this.commonUtil.maskEmail("UUUU42jane.doe@example.org"));
        assertEquals("UUU*******", this.commonUtil.maskEmail("UUUU42UUUU"));
        assertEquals("UUU*******@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("UUUU42(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("UUU******", this.commonUtil.maskEmail("UUUU42$1*"));
        assertEquals("UUU********", this.commonUtil.maskEmail("UUUU42Email"));
        assertEquals("UUU*****", this.commonUtil.maskEmail("UUUU4242"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]jane.doe@example.orgjane.doe@example.org",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.orgjane.doe@example.org"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]jane.doe@example.orgUUUU",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.orgUUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]jane.doe@example.org$1*",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org$1*"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]jane.doe@example.orgEmail",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.orgEmail"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]jane.doe@example.org42",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org42"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]UUUUjane.doe@example.org",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]UUUUjane.doe@example.org"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]UUUUUUUU", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]UUUUUUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]UUUU(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]UUUU(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]UUUU$1*", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]UUUU$1*"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]UUUUEmail", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]UUUUEmail"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]UUUU42", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]UUUU42"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]UUUU",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]UUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]$1*",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]$1*"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]Email",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]Email"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]42",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]42"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]$1*jane.doe@example.org",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]$1*jane.doe@example.org"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]$1*UUUU", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]$1*UUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]$1*(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]$1*(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]$1*$1*", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]$1*$1*"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]$1*Email", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]$1*Email"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]$1*42", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]$1*42"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]Emailjane.doe@example.org",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]Emailjane.doe@example.org"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]EmailUUUU", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]EmailUUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]Email(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]Email(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]Email$1*", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]Email$1*"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]EmailEmail", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]EmailEmail"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]Email42", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]Email42"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]42jane.doe@example.org",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]42jane.doe@example.org"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]42UUUU", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]42UUUU"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]42(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]42(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]42$1*", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]42$1*"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]42Email", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]42Email"));
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]4242", this.commonUtil.maskEmail("(^[^@]{3}|(?!^)\\G)[^@]4242"));
        assertEquals("$1*********@example.orgjane.doe@example.org",
                this.commonUtil.maskEmail("$1*jane.doe@example.orgjane.doe@example.org"));
        assertEquals("$1*********@example.orgUUUU", this.commonUtil.maskEmail("$1*jane.doe@example.orgUUUU"));
        assertEquals("$1*********@example.org(^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("$1*jane.doe@example.org(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("$1*********@example.org$1*", this.commonUtil.maskEmail("$1*jane.doe@example.org$1*"));
        assertEquals("$1*********@example.orgEmail", this.commonUtil.maskEmail("$1*jane.doe@example.orgEmail"));
        assertEquals("$1*********@example.org42", this.commonUtil.maskEmail("$1*jane.doe@example.org42"));
        assertEquals("$1*************@example.org", this.commonUtil.maskEmail("$1*UUUUjane.doe@example.org"));
        assertEquals("$1*********", this.commonUtil.maskEmail("$1*UUUUUUUU"));
        assertEquals("$1*********@]{3}|(?!^)\\G)[^@]", this.commonUtil.maskEmail("$1*UUUU(^[^@]{3}|(?!^)\\G)[^@]"));
        assertEquals("$1********", this.commonUtil.maskEmail("$1*UUUU$1*"));
        assertEquals("$1**********", this.commonUtil.maskEmail("$1*UUUUEmail"));
        assertEquals("$1*******", this.commonUtil.maskEmail("$1*UUUU42"));
        assertEquals("$1*****@]{3}|(?!^)\\G)[^@]jane.doe@example.org",
                this.commonUtil.maskEmail("$1*(^[^@]{3}|(?!^)\\G)[^@]jane.doe@example.org"));
        assertEquals("$1*****@]{3}|(?!^)\\G)[^@]UUUU", this.commonUtil.maskEmail("$1*(^[^@]{3}|(?!^)\\G)[^@]UUUU"));
        assertEquals("$1*****@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]",
                this.commonUtil.maskEmail("$1*(^[^@]{3}|(?!^)\\G)[^@](^[^@]{3}|(?!^)\\G)[^@]"));
    }
}

