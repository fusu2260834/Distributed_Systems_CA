package ds.service3;

import java.io.IOException;

import ds.service3.RequestMessage;
import ds.service3.ResponseMessage;
import ds.service3.Service3Grpc.Service3ImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class Service3 extends Service3ImplBase {

    public static void main(String[] args) throws InterruptedException, IOException {
        Service3 service1 = new Service3();

        int port = 50053;
        Server server;

        try {
            server = ServerBuilder.forPort(port)
                    .addService(service1)
                    .build()
                    .start();
        } catch (IOException e) {
            System.out.println("Service-3 failed to connect. Is the port already in use?");
            return;
        }

        System.out.println("Service-3 started, listening on " + port);

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            System.out.println("Service-3 was interrupted.");
        }
    }

    int BodyTemperature = 36;

    @Override
    public void service3Do(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {

        String responseMessage;

        try {
            // Attempt to parse the BodyTemperature as an integer
            int bodyTemperature = Integer.parseInt(request.getText());

            // Check if the body temperature is within the specified range
            if (bodyTemperature > 35 && bodyTemperature < 38) {
                responseMessage = "BodyTemperature is within range";
            } else {
                responseMessage = "BodyTemperature is out of range, please call the nurse team immediately.";
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format
            responseMessage = "Invalid input: BodyTemperature must be a number.";
        }

        // Prepare the response message
        ResponseMessage reply = ResponseMessage.newBuilder().setResponse(responseMessage).build();

        // Send the response back to the client
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        System.out.println("Sent data");
    }
}
