package com;

import model.Payment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payment")
public class PayService {
	Payment PayObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPay() {
		return PayObj.readPay();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPay(
			@FormParam("CustomerName") String CustomerName,
			@FormParam("Address") String Address,
			@FormParam("AccountNo") String AccountNo,
			@FormParam("BillNo") String BillNo,
			@FormParam("Amount") String Amount) {
		String output = PayObj.insertPay(CustomerName, Address, AccountNo, BillNo, Amount);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePay(String paymentData) {
		
		JsonObject PaymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		
		String PaymentID = PaymentObject.get("PaymentID").getAsString();
		String CustomerName = PaymentObject.get("CustomerName").getAsString();
		String Address = PaymentObject.get("Address").getAsString();
		String AccountNo = PaymentObject.get("AccountNo").getAsString();
		String BillNo = PaymentObject.get("BillNo").getAsString();
		String Amount = PaymentObject.get("Amount").getAsString();
		
		String output = PayObj.updatePay(PaymentID, CustomerName, Address, AccountNo, BillNo, Amount);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePay(String paymentData) {
		
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		String PaymentID = doc.select("PaymentID").text();
		String output = PayObj.deletePay(PaymentID);
		return output;
		
	}
}
