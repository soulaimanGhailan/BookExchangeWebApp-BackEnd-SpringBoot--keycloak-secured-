package soul.smi.pfe.statisticservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
@FeignClient(name = "users-service")
public interface UsersRestClient {
    @GetMapping("users/totalNumber")
    public Long getNumberOfUsers();
}
