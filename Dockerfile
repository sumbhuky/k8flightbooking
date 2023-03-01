FROM isahl/openjdk17:amd64
COPY target/flightBookingApplication-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","/flightBookingApplication-0.0.1-SNAPSHOT.jar"] 