package com.andreymasiero.chatbot.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andreymasiero.chatbot.chat.Chat;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@WebServlet(urlPatterns = "/chat")
public class MessageServlet extends HttpServlet{

	
	private static final long serialVersionUID = 6093948662123015033L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String message = req.getParameter("question");
		Map context = new HashMap();
		MessageResponse response = this.conversationAPI(message, context);
		Chat chat = new Chat();
		chat.addUserMessage(message);
		chat.addBotMessage(response.getOutput().getText().get(0));
		context = response.getContext();
		resp.setContentType("application/json");
		resp.getWriter().write(new Gson().toJson(chat.getChat()));
	}
	
	private MessageResponse conversationAPI(String input,Map context){
		Conversation service = new Conversation("2017-02-03");
		//service.setApiKey("duYeDMHQMeROhz0AF0tAv0-bmW9iBKcIkja3r91Ojevn");
		service.setUsernameAndPassword("5d1b2e46-8836-4260-a163-7d7844383de8", "PJTsAUqUfHEf");
		InputData inputData = new InputData.Builder().text(input).build();
		MessageRequest newMessage = new MessageRequest();
		newMessage.setInput(inputData);
		String workspaceId = "3efa7301-f199-46e6-a999-a3e167faa97a";
		MessageOptions options = new MessageOptions.Builder(workspaceId).input(inputData).build();
		MessageResponse response = service. message(options).execute();
		return response;
	} 

}
