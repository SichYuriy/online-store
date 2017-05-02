package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Yuriy on 5/3/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DispatcherServletTest {


    @Spy
    private Controller controller;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private DispatcherServlet dispatcher;


    @Before
    public void setUp() throws Exception {
        dispatcher = new DispatcherServlet();
        given(request.getSession()).willReturn(session);
        given(request.getRequestDispatcher(any())).willReturn(mock(RequestDispatcher.class));
    }


    @Test
    public void singleUrlMethodMapping() throws Exception {
        given(request.getPathInfo()).willReturn("/path1/path2");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", controller);
        dispatcher.service(request, response);

        verify(controller, times(1)).doGet(any(), any());
        verify(controller, never()).doPost(any(), any());
    }

    @Test
    public void testSingleMethodMappingMiss() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", controller);
        dispatcher.service(request, response);

        verify(controller, never()).doGet(any(), any());
        verify(controller, never()).doAny(any(), any());
    }

    @Test
    public void testSeveralMethodMappingHit() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path2");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", controller);

        dispatcher.service(request, response);
        verify(controller, times(1)).doGet(any(), any());
        verify(controller, never()).doPost(any(), any());

        given(request.getMethod()).willReturn("POST");
        dispatcher.service(request, response);
        verify(controller, times(1)).doGet(any(), any());
        verify(controller, times(1)).doPost(any(), any());
    }
}