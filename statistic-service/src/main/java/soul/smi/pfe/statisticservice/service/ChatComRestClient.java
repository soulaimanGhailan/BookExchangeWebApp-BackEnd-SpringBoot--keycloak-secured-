package soul.smi.pfe.statisticservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ChatCom-service")
public interface ChatComRestClient {
    @GetMapping("comments/totalNumber")
    public Long getNumberOfComments();
}
