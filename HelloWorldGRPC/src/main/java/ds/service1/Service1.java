package ds.service1;

import java.io.IOException;

import ds.service1.Service1Grpc.Service1ImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;


public class Service1 extends Service1ImplBase{



	public static void main(String[] args) throws InterruptedException, IOException {
		Service1 service1 = new Service1();

		int port = 50051;
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
			System.out.println("Service-1 failed to connect. Is the port already in use?");
			return;
		}

		System.out.println("Service-1 started, listening on " + port);

		try
		{
			server.awaitTermination();
		}
		catch(InterruptedException e)
		{
			System.out.println("Service-1 was interrupted.");
		}
	}

	int Roomtemperature = 25;

	@Override
	public void service1Do(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {

		//prepare the value to be set back
		int temperature = Integer.parseInt(request.getText());
		String responseMessage = null;
		if(temperature > 22 & temperature < 28)
		{
			responseMessage = "RoomTemperature is within range";
		}
		else 
		{
			responseMessage = "RoomTemperature is out of range,please check your air conditioner immediatly.";
		}


		//preparing the response message
		ResponseMessage reply = ResponseMessage.newBuilder().setResponse(responseMessage).build();

		responseObserver.onNext( reply ); 

		responseObserver.onCompleted();
		
		System.out.println("Sent data");

	}
}
