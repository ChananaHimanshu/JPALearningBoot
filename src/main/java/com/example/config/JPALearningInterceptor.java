//package com.example.config;
//
//import java.util.Enumeration;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//@Component
//public class JPALearningInterceptor implements HandlerInterceptor {
//
//	protected PropConfig propConfig;
//
//	@Autowired
//	public JPALearningInterceptor(PropConfig propConfig) {
//		this.propConfig = propConfig;
//	}
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		System.out.println("-----------------------INTERCEPTOR preHandle()------------------------------");
//		Enumeration<String> headers = request.getHeaderNames();
//		while(headers.hasMoreElements()) {
//			System.out.println("header : " + headers.nextElement());
//		}
//		System.out.println("------->");
//		System.out.println(request.getHeader("authorization"));
//		System.out.println(request.getHeader("user-agent"));
//		System.out.println(request.getHeader("postman-token"));
//		System.out.println("User-Agent : " + request.getHeader("User-Agent"));
//		System.out.println("Header Names : " + request.getHeaderNames());
//		System.out.println("Pre Handle method is Calling");
//		System.out.println("getMethod : " + request.getMethod());
//		System.out.println("path-info : " + request.getPathInfo());
//		System.out.println("session id : " + request.getRequestedSessionId());
//		System.out.println("url : " + request.getRequestURL());
//		System.out.println("session : " + request.getSession());
//		System.out.println("--------------------------------------------------------------------------->>");
//		return HandlerInterceptor.super.preHandle(request, response, handler);
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		System.out.println("Post Handle method is Calling");
//		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		System.out.println("Request and Response is completed");
//		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//	}
//
//}
