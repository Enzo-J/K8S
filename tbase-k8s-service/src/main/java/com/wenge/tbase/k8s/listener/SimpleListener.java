package com.wenge.tbase.k8s.listener;

import io.fabric8.kubernetes.client.dsl.ExecListener;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;

public class SimpleListener implements ExecListener {

    private CompletableFuture<String> data;
    private ByteArrayOutputStream baos;

    public SimpleListener(CompletableFuture<String> data, ByteArrayOutputStream baos) {
        this.data = data;
        this.baos = baos;
    }

    @Override
    public void onOpen(Response response) {
        System.out.println("Reading data... " + response.message());
    }

    @Override
    public void onFailure(Throwable t, Response response) {
        System.err.println(t.getMessage() + " " + response.message());
        data.completeExceptionally(t);
    }

    @Override
    public void onClose(int code, String reason) {
        System.out.println("Exit with: " + code + " and with reason: " + reason);
        data.complete(baos.toString());
    }
}

