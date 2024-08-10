package ds.service2;

import java.io.IOException;
import ds.service2.RequestMessage;
import ds.service2.ResponseMessage;
import ds.service2.Service2Grpc.Service2ImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class Service2 extends Service2ImplBase {

    public static void main(String[] args) throws InterruptedException, IOException {
        Service2 service2 = new Service2();

        int port = 50052;
        Server server;

        try {
            server = ServerBuilder.forPort(port)
                    .addService(service2)
                    .build()
                    .start();
        } catch (IOException e) {
            System.out.println("Service-2 failed to connect. Is the port already in use?");
            return;
        }

        System.out.println("Service-2 started, listening on " + port);

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            System.out.println("Service-2 was interrupted.");
        }
    }

    int HeartBeatsPM = 60;

    @Override
    public void service2Do(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {

        String responseMessage;

        try {
            // Attempt to parse the HeartBeatsPM as an integer
            int heartBeatsPM = Integer.parseInt(request.getText());

            // Check if the heartbeats per minute are within the specified range
            if (heartBeatsPM > 60 && heartBeatsPM < 90) {
                responseMessage = "HeartBeats is within range";
            } else {
                responseMessage = "HeartBeats is out of range, please call the nurse team immediately.";
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format
            responseMessage = "Invalid input: HeartBeats must be a number.";
        }

        // Prepare the response message
        ResponseMessage reply = ResponseMessage.newBuilder().setResponse(responseMessage).build();

        // Send the response back to the client
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        System.out.println("Sent data");
    }
}
