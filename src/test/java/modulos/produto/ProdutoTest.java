package modulos.produto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do modulo de Produto")
public class ProdutoTest {
    @Test
    @DisplayName("Validar os limites proibidos do valor do Produto")
    public void testeValidarLimitesProibidosValorProduto() {
        //Configurando os dados da API Rest da Lojinha

        //Rest Assured

        //Endereço do servidor. Ele deve ser inserido com aspas duplas, pois é esperado um String.
        baseURI = "http://165.227.93.41";

        //Abaixo inserimos o número da porta onde aplicação está rondando,aqui é esperado número.
        //port = 8080;

        //basePath é o caminho inicial da aplicação
        basePath = "/lojinha";


        //Obter o token do usuário
        // Iremos armazenar o token enviado da requisição dentro da variável tokke
                   // Dado que
        String token = given()
                //Cabeçalho da requisição aqui é parecido com o que fazemos no Postman
                .contentType(ContentType.JSON)

                //Body da requisição, ou seja, corpo da requisição que é necessário.
                .body("{\n" +
                        "  \"usuarioLogin\": \"eltondutradias1\",\n" +
                        "  \"usuarioSenha\": \"123456\"\n" +
                    "}") // Neste ponto damos um shift + tab para demonstrar que o given acabou e inciaremos o when.

                //Quando
                .when()
                     //Colocamos aqui o método que utilizaremos eo caminho que vem depois do baseUrl.
                    .post("/v2/login")


                //Então. Neste ponto inserimos o que queremos que aconteça depois que já enviamos a requisição
                .then()
                     //abaixo iremos extratir o que veio dentro do corpo da resposta
                    .extract()
                          //Aqui é o caminho dentro do corpo da resposta, o que for extraído de dentro aqui, será armazenado na String token lá no given()
                          .path("data.token");
                    //Abaixo verificamos se o token foi capturado e armazendado corretamente.
        System.out.println(token);




        // Tentar inserir um produto com valor 0.00 e validar que a  mensagem de erro foi apresentada e o

        //Aqui já vamos direto para o teste, no given anteior realizamos a extração do token.
        given()
                //Cabeçalho
                .contentType(ContentType.JSON)
                .header("token", token)

                // Corpo, após copiar do swagger abaixo nós vamos definindo a informações que iremos enviar.
                .body("{\n" +
                        "  \"produtoNome\": \"PS5\",\n" +
                        "  \"produtoValor\": 0.00,\n" +
                        "  \"produtoCores\": [\n" +
                        "   \"preto\" \n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"Controle\",\n" +
                        "      \"componenteQuantidade\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")

        .when()
                .post("/v2/produtos")

        .then()
                //Aqui vamos validar o teste que esperamos que seja realizado
                .assertThat()
                //Aqui temos um asserção. Abaixo temos a validação que quremos realizar.
                  .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))

                // Status Code 422 para validação
                 .statusCode(422);

    }
}
