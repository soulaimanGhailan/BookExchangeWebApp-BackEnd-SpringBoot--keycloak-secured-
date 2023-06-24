package soul.smi.pfe.statisticservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import soul.smi.pfe.statisticservice.model.UserEntity;

import java.util.List;

@FeignClient(name = "users-service")
public interface UsersRestClient {
    @GetMapping("users/totalNumber")
    public Long getNumberOfUsers();

}
