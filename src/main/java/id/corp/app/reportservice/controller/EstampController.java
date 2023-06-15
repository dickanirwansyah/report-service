package id.corp.app.reportservice.controller;

import id.corp.app.reportservice.model.StampRequest;
import id.corp.app.reportservice.service.EstampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/estamp")
public class EstampController {

    @Autowired
    private EstampService estampService;

    @PostMapping(value = "/execute")
    public String executeStamping(@RequestBody StampRequest request){
        estampService.stampingPdf(request);
        return "success";
    }
}
