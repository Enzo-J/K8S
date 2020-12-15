package com.wenge.tbase.harbor.service;

import com.wenge.tbase.harbor.bean.*;
import com.wenge.tbase.harbor.result.RestResult;

import java.util.List;

public interface HarborWebhookService {

    RestResult<?> getWebhookListService(Integer project_id);

    RestResult<?> addWebhookByProjectIdService(Integer project_id, Webhook webhook);


}
