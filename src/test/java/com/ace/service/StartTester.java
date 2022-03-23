package com.ace.service;

import nl.altindag.log.LogCaptor;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StartTester {

    @Test
    public void mainRunWithEmpty()
    {
        LogCaptor logCaptor = LogCaptor.forClass(Start.class);
        logCaptor.setLogLevelToInfo();
        String arr[]={};
        new Start().main(arr);

        assertThat(logCaptor.getLogs())
                .containsExactly("please provide json file path");

    }

    @Test
    public void mainRunWithInValidString()
    {
        LogCaptor logCaptor = LogCaptor.forClass(Start.class);
        String arr[]={""};
        Start.main(arr);

        assertThat(logCaptor.getLogs())
                .contains("ERR001 - Provided PATH is wrong");

    }

    @Test
    public void mainRunWithValidString()
    {
        LogCaptor logCaptor = LogCaptor.forClass(Start.class);
        String arr[]={"E:\\Java Development\\ace\\src\\main\\resources\\Sample - Copy.json"};
        Start.main(arr);

    }

}
