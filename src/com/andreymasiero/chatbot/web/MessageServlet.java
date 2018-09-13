package com.andreymasiero.chatbot.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andreymasiero.chatbot.chat.Chat;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@WebServlet(urlPatterns = "/chat")
public class MessageServlet extends HttpServlet{

	private Context context;
	private static final long serialVersionUID = 6093948662123015033L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String message = req.getParameter("question");
		if (message.isEmpty())  this.context = null;
		MessageResponse response = this.conversationAPI(message, context);
		Chat chat = new Chat();
		chat.addUserMessage(message);
		for(String text : response.getOutput().getText()) {
			chat.addBotMessage(text);
		}
		context = response.getContext();
		resp.setContentType("application/json");
		resp.getWriter().write(new Gson().toJson(response.getOutput().getText()));
	}
	
	private MessageResponse conversationAPI(String input, Context context){
		Conversation service = new Conversation("2018-07-10"); // Data que o workspace foi criado
		service.setUsernameAndPassword("fb72b2fa-5e15-42b1-9046-7e7ab4fb0bc7", "OWOb8XFsJKdc");
		InputData inputData = new InputData.Builder().text(input).build();
		MessageRequest newMessage = new MessageRequest();
		newMessage.setInput(inputData);
		String workspaceId = "bb47bb89-d142-4dec-9c41-ecdfd4fbb630";
		MessageOptions options = new MessageOptions.Builder(workspaceId).input(inputData).context(context).build();
		MessageResponse response = service.message(options).execute();
		return response;
	} 

}








