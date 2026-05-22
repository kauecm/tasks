package com.automatizacao.tasks.service;

import java.io.IOException;
import java.net.CookieManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.automatizacao.tasks.dtos.TarefaDTO;

import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class ServiceTasks {

    @Value("${api.auth.url}")
    private String authUrl;

    @Value("${api.auth.usuario}")
    private String usuario;

    @Value("${api.auth.senha}")
    private String senha;

    @Value("${tasks.url.cadastrar}")
    private String urlCadastrar;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .cookieJar(new JavaNetCookieJar(new CookieManager()))
            .followRedirects(true)
            .followSslRedirects(true)
            .build();

    public void autenticar() {

        try {

            RequestBody formBody = new FormBody.Builder()
                    .add("login", usuario)
                    .add("senha", senha)
                    .add("submit", "OK")
                    .build();

            Request request = new Request.Builder()
                    .url(authUrl)
                    .post(formBody)

                    .addHeader(
                            "User-Agent",
                            "Mozilla/5.0"
                    )

                    .addHeader(
                            "Origin",
                            "https://tasks.amlgroup.com.br"
                    )

                    .addHeader(
                            "Referer",
                            "https://tasks.amlgroup.com.br/auth/login?page=/"
                    )

                    .build();

            try (Response response = client.newCall(request).execute()) {

                System.out.println("STATUS LOGIN: " + response.code());

                String html = response.body() != null
                        ? response.body().string()
                        : "";

                System.out.println(html);

                if (html.contains("Esqueci minha senha")) {
                    System.out.println("LOGIN FALHOU");
                } else {
                    System.out.println("LOGIN OK");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrar(TarefaDTO tarefa) {

        try {

            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)

                    .addFormDataPart("id_empresa_grupo", tarefa.idEmpresaGrupo())
                    .addFormDataPart("id_empresa_grupo_area", tarefa.idEmpresaGrupoArea())
                    .addFormDataPart("id_projeto", tarefa.idProjeto())
                    .addFormDataPart("restricao_flg", tarefa.restricaoFlg())

                    .addFormDataPart(
                            "titulo",
                            tarefa.titulo()
                    )

                    .addFormDataPart(
                            "descricao",
                            tarefa.descricao()
                    )

                    // fixo conforme solicitado
                    .addFormDataPart(
                            "anexos[]",
                            "",
                            RequestBody.create(new byte[0])
                    )

                    .addFormDataPart("id_usuario_resp", tarefa.idUsuarioResp())
                    .addFormDataPart("id_status", tarefa.idStatus())
                    .addFormDataPart("id_usuario_para", tarefa.idUsuarioPara())
                    .addFormDataPart("id_prioridade", tarefa.idPrioridade())
                    .addFormDataPart("data_entrega", tarefa.dataEntrega())
                    .addFormDataPart("ordem", tarefa.ordem())
                    .addFormDataPart("horas_estimadas", tarefa.horasEstimadas())

                    // fixo conforme solicitado
                    .addFormDataPart("submit", "Salvar")

                    .build();

            Request request = new Request.Builder()
                    .url(urlCadastrar)
                    .post(body)

                    .addHeader(
                            "Origin",
                            "https://tasks.amlgroup.com.br"
                    )

                    .addHeader(
                            "Referer",
                            "https://tasks.amlgroup.com.br/tarefa/incluir"
                    )

                    .addHeader(
                            "Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
                    )

                    .addHeader(
                            "User-Agent",
                            "Mozilla/5.0"
                    )

                    .build();

            try (Response response = client.newCall(request).execute()) {

                System.out.println("STATUS CADASTRO: " + response.code());

                if (response.body() != null) {
                    System.out.println(response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}