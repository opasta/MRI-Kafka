package com.example.kafkaMRI.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.kafkaMRI.kafka.Producer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Random;
import java.util.ArrayList;

@RestController
@RequestMapping("/mri/generate")
public class OutputController {
    private Producer Producer;

    public OutputController(Producer Producer) {
        this.Producer = Producer;
    }

    //link: http://localhost:8080/api/v1/kafka/publish?message=helloWorld
    //link: http://localhost:8080/mri/generate/amount?message=helloWorld
    @GetMapping("/amount")
    public ResponseEntity<String> publish(@RequestParam("amount") String message){

        try{
            int amount = Integer.parseInt(message);
            String[] numbers;
            ArrayList arrlist = new ArrayList();
            int avg = 0;
            Random randomNum = new java.util.Random();

            for (int i = 0; i < amount; i++) {
                int nextRandom = randomNum.nextInt(100);
                //int randomrandomNum.nextInt(100);
                avg += nextRandom;
                //myWriter.write(randomNum.nextInt(100) + "\n");

                arrlist.add(randomNum.nextInt(100));
                //avg += avg;
            }

            int sdev = 69;
            String producerMessage = "Gewenste lengte is: " + message;
            String listMessage = "De list: " + arrlist;
            String outputMessage = "Gemiddelde: " + calculateAvg(arrlist) + ", standaarddeviatie: " + calculateSdev(arrlist);

            Producer.sendMessage(producerMessage);
            Producer.sendMessage(listMessage);
            Producer.sendMessage(outputMessage);

            return ResponseEntity.ok(producerMessage + "\n" + listMessage + "\n" + outputMessage);
        } catch (NumberFormatException e){
            return ResponseEntity.ok("Vul een getal in.");
        }
    }

    public static double calculateAvg(ArrayList arrlist){
        double total = 0;
        for (Object o : arrlist) {
            total += (int) o;
        }
        return total / arrlist.size();
    }

    public static double calculateSdev(ArrayList arrlist) {
        double sum = 0, standardDeviation = 0;
        int length = arrlist.size();

        for (Object o : arrlist) {
            sum += (int) o;
        }
        double mean = sum / length;

        for (Object o : arrlist) {
            standardDeviation += Math.pow((int) o - mean, 2);
        }
        return Math.sqrt(standardDeviation / length);
    }
}