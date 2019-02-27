package com.clientui.proxies;

import com.clientui.beans.ExpeditionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-expedition")
public interface MicroserviceExpeditonProxy {

    @GetMapping(value = "/microservice-expedition/expedition/{id}")
    ExpeditionBean etatExpedition(@PathVariable("id") int id);
}
