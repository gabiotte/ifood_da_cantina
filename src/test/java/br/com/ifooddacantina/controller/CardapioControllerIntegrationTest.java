package br.com.ifooddacantina.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:cardapio-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CardapioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void confirmarPedidoRenderizaPaginaDeConfirmacao() {
        ResponseEntity<String> response = criarPedido();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Pedido confirmado");
    }

    @Test
    void painelDoAtendenteRenderizaPedidos() {
        criarPedido();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/atendente",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(
                "Painel do atendente",
                "Teste",
                "Pendente",
                "data-horario-retirada=\"10:30\"",
                "atualizarPedidosPrioritarios"
        );
    }

    private ResponseEntity<String> criarPedido() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("nomeAluno", "Teste");
        form.add("horarioRetirada", "10:30");
        form.add("itemId", "1");
        form.add("quantidade", "1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return restTemplate.exchange(
                "http://localhost:" + port + "/confirmar-pedido",
                HttpMethod.POST,
                new HttpEntity<>(form, headers),
                String.class
        );
    }
}
