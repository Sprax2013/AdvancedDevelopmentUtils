package de.sprax2013.advanced_dev_utils.mineskin;

/*
 * Copyright (c) 2016 inventivetalent
 * MIT License (https://github.com/InventivetalentDev/MineskinClient/blob/master/LICENSE)
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import de.sprax2013.advanced_dev_utils.Main;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author InventivetalentDev
 * (https://github.com/InventivetalentDev/MineskinClient)
 * <p>
 * I made some changes to match my needs.
 */
public class MineSkinClient {
    private static final String ID_FORMAT = "http://api.mineskin.org/get/id/%s";
    private static final String URL_FORMAT = "http://api.mineskin.org/generate/url?url=%s&%s";
    private static final String UPLOAD_FORMAT = "http://api.mineskin.org/generate/upload?%s";
    private static final String USER_FORMAT = "http://api.mineskin.org/generate/user/%s?%s";

    private final ExecutorService pool;
    private final String userAgent;

    private final Gson gson = new Gson();

    private long nextRequest = 0;

    public MineSkinClient(String userAgent) {
        this.pool = Executors.newSingleThreadExecutor();
        this.userAgent = checkNotNull(userAgent);

        Main.mineSkinClients.add(this);
    }

    public long getNextRequest() {
        return nextRequest;
    }

    /**
     * Gets data for an existing Skin
     *
     * @param id       Skin-Id
     * @param callback {@link SkinCallback}
     *
     * @throws IllegalStateException When the client has been destroyed by calling
     *                               {@link #destroy()}
     */
    public void getSkin(int id, SkinCallback callback) {
        if (pool.isShutdown()) {
            throw new IllegalStateException("MineSkinClient has already been destroyed!");
        }

        checkNotNull(callback);
        pool.execute(() -> {
            try {
                handleResponse(
                        Jsoup.connect(String.format(ID_FORMAT, id)).userAgent(userAgent).method(Connection.Method.GET)
                                .ignoreContentType(true).ignoreHttpErrors(true).timeout(10000).execute().body(),
                        callback);
            } catch (Exception e) {
                callback.exception(e);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    /**
     * Generates skin data from an URL
     *
     * @param url      URL
     * @param options  {@link SkinOptions}
     * @param callback {@link SkinCallback}
     *
     * @throws IllegalStateException When the client has been destroyed by calling
     *                               {@link #destroy()}
     */
    public void generateUrl(String url, SkinOptions options, SkinCallback callback) {
        if (pool.isShutdown()) {
            throw new IllegalStateException("MineSkinClient has already been destroyed!");
        }

        checkNotNull(url);
        checkNotNull(options);
        checkNotNull(callback);

        pool.execute(() -> {
            try {
                if (System.currentTimeMillis() < nextRequest) {
                    long delay = (nextRequest - System.currentTimeMillis());
                    callback.waiting(delay);
                    Thread.sleep(delay + 1000);
                }

                callback.uploading();

                handleResponse(Jsoup.connect(String.format(URL_FORMAT, url, options.toUrlParam())).userAgent(userAgent)
                        .method(Connection.Method.POST).ignoreContentType(true).ignoreHttpErrors(true).timeout(10000)
                        .execute().body(), callback);
            } catch (Exception e) {
                callback.exception(e);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    /**
     * Uploads and generates skin data from a local file
     *
     * @param file     File to upload
     * @param options  {@link SkinOptions}
     * @param callback {@link SkinCallback}
     *
     * @throws IllegalStateException When the client has been destroyed by calling
     *                               {@link #destroy()}
     */
    public void generateUpload(File file, SkinOptions options, SkinCallback callback) {
        if (pool.isShutdown()) {
            throw new IllegalStateException("MineSkinClient has already been destroyed!");
        }

        checkNotNull(file);
        checkNotNull(options);
        checkNotNull(callback);

        pool.execute(() -> {
            FileInputStream in = null;

            try {
                if (System.currentTimeMillis() < nextRequest) {
                    long delay = (nextRequest - System.currentTimeMillis());
                    callback.waiting(delay);
                    Thread.sleep(delay + 1000);
                }

                callback.uploading();

                in = new FileInputStream(file);

                handleResponse(Jsoup.connect(String.format(UPLOAD_FORMAT, options.toUrlParam())).userAgent(userAgent)
                        .method(Connection.Method.POST).data("file", file.getName(), in).ignoreContentType(true)
                        .ignoreHttpErrors(true).timeout(10000).execute().body(), callback);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Uploads and generates skin data from a local file
     *
     * @param file    File to upload
     * @param options {@link SkinOptions}
     *
     * @throws IllegalStateException When the client has been destroyed by calling
     *                               {@link #destroy()}
     */
    public Skin generateUpload(File file, SkinOptions options) throws Exception {
        Skin skin = null;

        checkNotNull(file);
        checkNotNull(options);

        try (FileInputStream in = new FileInputStream(file)) {
            long delay = (nextRequest - System.currentTimeMillis());

            if (delay > 0) {
                Thread.sleep(delay + 1000);
            }

            String body = Jsoup.connect(String.format(UPLOAD_FORMAT, options.toUrlParam())).userAgent(userAgent)
                    .method(Connection.Method.POST).data("file", file.getName(), in).ignoreContentType(true)
                    .ignoreHttpErrors(true).timeout(10000).execute().body();

            if (!body.contains("Cloudflare Ray ID:")) {
                skin = handleResponse(body);
            }
        }

        return skin;
    }

    /**
     * Loads skin data from an existing player
     *
     * @param uuid     {@link UUID} of the player
     * @param options  {@link SkinOptions}
     * @param callback {@link SkinCallback}
     *
     * @throws IllegalStateException When the client has been destroyed by calling
     *                               {@link #destroy()}
     */
    public void generateUser(UUID uuid, SkinOptions options, SkinCallback callback) {
        if (pool.isShutdown()) {
            throw new IllegalStateException("MineSkinClient has already been destroyed!");
        }

        checkNotNull(uuid);
        checkNotNull(options);
        checkNotNull(callback);
        pool.execute(() -> {
            try {
                if (System.currentTimeMillis() < nextRequest) {
                    long delay = (nextRequest - System.currentTimeMillis());
                    callback.waiting(delay);
                    Thread.sleep(delay + 1000);
                }

                callback.uploading();

                handleResponse(Jsoup.connect(String.format(USER_FORMAT, uuid.toString(), options.toUrlParam()))
                        .userAgent(userAgent).method(Connection.Method.GET).ignoreContentType(true)
                        .ignoreHttpErrors(true).timeout(10000).execute().body(), callback);
            } catch (Exception e) {
                callback.exception(e);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    Skin handleResponse(String body) throws Exception {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        if (jsonObject.has("error")) {
            throw new Exception(jsonObject.get("error").getAsString());
        }

        Skin skin = gson.fromJson(jsonObject, Skin.class);
        this.nextRequest = System.currentTimeMillis() + ((long) (skin.nextRequest * 1000L));

        return skin;
    }

    void handleResponse(String body, SkinCallback callback) {
        try {
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            if (jsonObject.has("error")) {
                callback.error(jsonObject.get("error").getAsString());
                return;
            }

            Skin skin = gson.fromJson(jsonObject, Skin.class);
            this.nextRequest = System.currentTimeMillis() + ((long) (skin.nextRequest * 1000L));
            callback.done(skin);
        } catch (JsonParseException e) {
            callback.parseException(e, body);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public void destroy() {
        if (!pool.isShutdown()) {
            pool.shutdown();

            try {
                pool.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            pool.shutdownNow();
        }
    }
}