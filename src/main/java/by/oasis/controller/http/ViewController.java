//package by.oasis.controller.http;
//
//import org.apache.commons.io.IOUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@Controller
//public class ViewController {
//
//    @GetMapping("/")
//    public String mainPage(){
//        return "mainPage";
//    }
//
//    @GetMapping("/main/reg")
//    public String registration(){
//        return "registration";
//    }
//
//    @GetMapping("/main/ver")
//    public String verification(){
//        return "verification";
//    }
//
//    @GetMapping("/main/log")
//    public String login(){
//        return "login";
//    }
//
//    @GetMapping("/main/cab")
//    public String cabinet(){
//        return "cabinet";
//    }
//
//
//
//    @GetMapping(value = "/image")
//    public @ResponseBody byte[] getImage() throws IOException {
//        InputStream in = getClass()
//                .getResourceAsStream("/templates/static/images/awdawdad2d2.png");
//        return IOUtils.toByteArray(in);
//    }
//}
