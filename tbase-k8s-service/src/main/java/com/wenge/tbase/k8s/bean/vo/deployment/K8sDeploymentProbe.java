package com.wenge.tbase.k8s.bean.vo.deployment;

import com.google.common.collect.Lists;
import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "容器存活探测配置")
public class K8sDeploymentProbe {
    @ApiModelProperty(value = "type,0 tcp, 1 http, 2 command")
    private int type;
    @ApiModelProperty(value = "http探测host")
    private String host;
    @ApiModelProperty(value = "http探测path")
    private String path;
    @ApiModelProperty(value = "port")
    private int port;
    @ApiModelProperty(value = "http探测headers")
    private List<K8SDeploymentHttpProbeHeader> headers;

    @ApiModelProperty(value = "健康阈值")
    protected Integer successThreshold;
    @ApiModelProperty(value = "故障阈值")
    protected Integer failureThreshold;
    @ApiModelProperty(value = "超时检测时间")
    protected Integer timeout;
    @ApiModelProperty(value = "初始等待时间")
    protected Integer initialDelay;
    @ApiModelProperty(value = "检测间隔时间")
    protected Integer period;

    @ApiModelProperty(value = "command")
    private List<String> command;


    private TCPSocketAction tcpSocketAction() {
        TCPSocketAction tcpSocketAction = new TCPSocketAction();
        tcpSocketAction.setHost(host);
        tcpSocketAction.setPort(new IntOrStringBuilder().withIntVal(port).build());
        return tcpSocketAction;
    }

    private HTTPGetAction httpGetAction() {
        HTTPGetAction httpGetAction = new HTTPGetAction();
        httpGetAction.setPath(path);
        httpGetAction.setPort(new IntOrStringBuilder().withIntVal(port).build());
        List<HTTPHeader> httpHeaders = Lists.newArrayList();
        if (headers != null) {
            for (K8SDeploymentHttpProbeHeader header : headers) {
                httpHeaders.add(header.httpHeader());
            }
            httpGetAction.setHttpHeaders(httpHeaders);
        }
        return httpGetAction;
    }

    private ExecAction execAction() {
        ExecAction execAction = new ExecAction();
        execAction.setCommand(command);
        return execAction;
    }

    public Probe probe() {
        ProbeBuilder probeBuilder = new ProbeBuilder();
        if (type == 0) {
            probeBuilder.withTcpSocket(tcpSocketAction());
        } else if (type == 1) {
            probeBuilder.withHttpGet(httpGetAction());
        } else {
            probeBuilder.withExec(execAction());
        }
        probeBuilder.withSuccessThreshold(successThreshold);
        probeBuilder.withFailureThreshold(failureThreshold);
        probeBuilder.withTimeoutSeconds(timeout);
        probeBuilder.withInitialDelaySeconds(initialDelay);
        probeBuilder.withPeriodSeconds(period);
        return probeBuilder.build();
    }


    public Lifecycle lifecycle(boolean isPre) {
        HandlerBuilder handlerBuilder = new HandlerBuilder();
        if (type == 0) {
            handlerBuilder.withTcpSocket(tcpSocketAction());
        } else if (type == 1) {
            handlerBuilder.withHttpGet(httpGetAction());
        } else {
            handlerBuilder.withExec(execAction());
        }
        LifecycleBuilder lifecycleBuilder = new LifecycleBuilder();
        if (isPre) {
            lifecycleBuilder.withPreStop(handlerBuilder.build());
        } else {
            lifecycleBuilder.withPostStart(handlerBuilder.build());
        }
        return lifecycleBuilder.build();
    }
}
