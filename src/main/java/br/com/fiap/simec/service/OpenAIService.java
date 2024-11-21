package br.com.fiap.simec.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    public String generateSummary(String prompt) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo");
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "user").put("content", prompt));
        json.put("messages", messages);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JSONObject responseBody = new JSONObject(response.body().string());
                return responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content").trim();
            } else {
                throw new RuntimeException("Erro na API da OpenAI: Código HTTP " + response.code() + " - " + response.message());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar a API da OpenAI", e);
        }
    }
}
