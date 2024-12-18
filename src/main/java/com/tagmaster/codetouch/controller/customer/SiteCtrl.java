package com.tagmaster.codetouch.controller.customer;

import com.tagmaster.codetouch.dto.CreateSiteDTO;
import com.tagmaster.codetouch.entity.customer.Site;
import com.tagmaster.codetouch.service.customer.SiteSvc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/고객")
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public class SiteCtrl {
    private final SiteSvc siteSvc;

    public SiteCtrl(SiteSvc siteSvc) {
        this.siteSvc = siteSvc;
    }
    @PostMapping("/회원/사이트생성")
    public ResponseEntity<String> createSite(@RequestBody CreateSiteDTO createSiteDTO) {
        try{
            boolean test = siteSvc.siteCreate(createSiteDTO);
        if (!test) {
            return ResponseEntity.badRequest().body("사이트 생성에 실패하였습니다. 다시 시도해주세요.");
        }
        return ResponseEntity.ok("사이트 생성에 성공하였습니다!");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("사이트 생성 중 에러가 발생하였습니다.");
        }
    }

    @GetMapping("/회원/사이트정보/{email}")
    public ResponseEntity<Map<String, Object>> getSite(@PathVariable String email) {
        try {
            List<Site> siteList = siteSvc.siteRead(email);
            if (siteList == null) {
                Map<String, Object> result = new HashMap<>();
                result.put("NOTFOUND", "회원의 사이트 정보가 없습니다. 다시 시도해주세요.");
                return ResponseEntity.ok(result);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("SUCCESS", "회원의 사이트 정보 조회에 성공하였습니다.");
            result.put("SiteList", siteList);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("ERROR", "회원의 사이트 정보 조회 중 에러가 발생하였습니다. 다시 시도해주세요.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("/회원/사이트삭제/{email}/{siteId}")
    public ResponseEntity<Map<String, Object>> deleteSite(@PathVariable String email, @PathVariable int siteId) {
        try {
            if (siteSvc.siteDelete(email, siteId)) {
                Map<String, Object> result = new HashMap<>();
                result.put("SUCCESS", "사이트 삭제에 성공하였습니다.");
                return ResponseEntity.ok(result);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("NOTFOUND", "일치하는 회원 혹은 사이트가 없습니다.");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("ERROR", "사이트 삭제 중 에러가 발생했습니다. 다시 시도해주세요.");
            return ResponseEntity.badRequest().body(result);
        }
    }
}
