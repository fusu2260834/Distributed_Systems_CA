package ds.service2;

import java.io.IOException;
import java.net.SocketException;

import ds.service2.RequestMessage;
import ds.service2.ResponseMessage;
import ds.service2.Service2Grpc.Service2ImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;


public class Service2 extends Service2ImplBase{



	public static void main(String[] args) throws InterruptedException, IOException {
		Service2 service1 = new Service2();

		int port = 50052;
Server server;
		
		try
		{
			server = ServerBuilder.forPort(port)
					.addService(service1)
					.build()
					.start();
		}
		catch(IOException e)
		{
			System.out.println("Service-2 failed to connect. Is the port already in use?");
			return;
		}
		

		System.out.println("Service-2 started, listening on " + port);

		try
		{
			server.awaitTermination();
		}
		catch(InterruptedException e)
		{
			System.out.println("Service-2 was interrupted.");
		}

	}

		//server.awaitTermination();
	

	int HeartBeatsPM = 60;

	@Override
	public void service2Do(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {

		//prepare the value to be set back
		int HeartBeatsPM = Integer.parseInt(request.getText());
		String responseMessage = null;
		if(HeartBeatsPM > 60 & HeartBeatsPM < 90)
		{
			responseMessage = "HeartBeats is within range";
		}
		else 
		{
			responseMessage = "HeartBeats is out of range";
		}

		//preparing the response message
		ResponseMessage reply = ResponseMessage.newBuilder().setResponse(responseMessage).build();

		responseObserver.onNext( reply ); 

		responseObserver.onCompleted();
		
		System.out.println("Sent data");

	}
}
