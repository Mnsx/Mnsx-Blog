package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mnsx.annotation.SystemLog;
import top.mnsx.domain.ResponseResult;
import top.mnsx.service.LinkService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @SystemLog
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
