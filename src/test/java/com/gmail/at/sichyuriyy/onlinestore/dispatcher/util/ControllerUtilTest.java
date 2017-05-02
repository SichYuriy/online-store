package com.gmail.at.sichyuriyy.onlinestore.dispatcher.util;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.ResponseResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.BDDMockito.*;

/**
 * Created by Yuriy on 5/2/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerUtilTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private RequestService reqService;
    private ResponseService respService;

    @Spy
    private Controller controller;

    @Test
    public void serviceGet() {
        given(request.getMethod()).willReturn("GET");

        reqService = new RequestService(request);
        respService = new ResponseService(request, response);

        ControllerUtil.service(controller, reqService, respService);
        verify(controller, times(1)).doGet(any(), any());
        verify(controller, times(1)).doAny(any(), any());
        verify(controller, never()).doPost(any(), any());
        verify(controller, never()).doPut(any(), any());
        verify(controller, never()).doDelete(any(), any());
    }

}