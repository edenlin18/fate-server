package com.fate.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fate.server.datastore.Constant;
import com.fate.server.datastore.ForcedMatch;
import com.fate.server.datastore.User;
import com.google.appengine.api.datastore.Entity;

public class testServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		/* output stream */
		PrintWriter out = resp.getWriter();		
		
		if(username.equals("Eden") && password.equals("123123"))
		{
			User.CreateUser("Eden Lin", "12345678", "0000000000", "a, b, c", "I am Eden");
			ForcedMatch.CreateForcedMatch("Eden Lin", "Vivian Chang");
			out.write("In User");
			if(User.DoesUsernameExist("Eden Lin") && ForcedMatch.DoesForcedMatchExist("Eden Lin", "Vivian Chang")) {
				out.write("EXISTS");
			}
			
			out.write("Yes!");			
		}
		else
		{
			out.write("No!");
		}
		
	}
}