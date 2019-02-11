package br.com.db1.batepontodb1;

import org.junit.Test;

import br.com.db1.batepontodb1.data.PontoManager;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void getTimeFromResponse(){
        PontoManager pontoManager = new PontoManager();
        String[] result = pontoManager.getAllMarkings("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta http-equiv=\"Content-Language\" content=\"pt-br\">\n" +
                "    <title>Comprovante Ponto</title>\n" +
                "    <link href=\"/Content/bootstrap.css\" rel=\"stylesheet\"/>\n" +
                "<link href=\"/Content/site.css\" rel=\"stylesheet\"/>\n" +
                "<link href=\"/Content/background.css\" rel=\"stylesheet\"/>\n" +
                "<link href=\"/Content/pfpbootstrap.css\" rel=\"stylesheet\"/>\n" +
                "\n" +
                "    <script src=\"/Scripts/modernizr-2.6.2.js\"></script>\n" +
                "\n" +
                "</head>\n" +
                "<body class=\"backgroud_site\">\n" +
                "    <script src=\"/Scripts/jquery-1.10.2.js\"></script>\n" +
                "<script src=\"/Scripts/jquery.mask.js\"></script>\n" +
                "<script src=\"/Scripts/jstorage.js\"></script>\n" +
                "\n" +
                "    <script src=\"/Scripts/bootstrap.js\"></script>\n" +
                "<script src=\"/Scripts/respond.js\"></script>\n" +
                "\n" +
                "    <script src=\"/Scripts/es6-promise.js\"></script>\n" +
                "<script src=\"/Scripts/es6-promise.auto.js\"></script>\n" +
                "<script src=\"/Scripts/html2canvas.js\"></script>\n" +
                "<script src=\"/Scripts/html2canvas.svg.js\"></script>\n" +
                "<script src=\"/Scripts/registrador.js\"></script>\n" +
                "\n" +
                "    \n" +
                "\n" +
                "    <div class=\"container body-content\">\n" +
                "        \n" +
                "\n" +
                "<style>\n" +
                "    td {\n" +
                "        font-size: .83em;\n" +
                "        margin-top: 1.67em;\n" +
                "        margin-bottom: 1.67em;\n" +
                "        margin-left: 0;\n" +
                "        margin-right: 0;\n" +
                "        padding-top: 3px;\n" +
                "        padding-bottom: 3px;\n" +
                "        padding-left: 5px;\n" +
                "        padding-right: 5px;\n" +
                "        font-weight: bold;\n" +
                "    }\n" +
                "\n" +
                "    .page {\n" +
                "        -webkit-transform: rotate(-90deg);\n" +
                "        -moz-transform: rotate(-90deg);\n" +
                "        filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "<div class=\"container container-fluid\">\n" +
                "    <div id=\"divCentralizada\" class=\"col-md-offset-3 col-md-6\">\n" +
                "            <div class=\"panel panel-success\" style=\"width:450px;\">\n" +
                "                <div class=\"panel-heading\">\n" +
                "                    <button type=\"button\" class=\"close\" onclick=\"fecharComprovante()\">&times;</button>\n" +
                "                    <h3 class=\"panel-title\" style=\"text-align:center\">Comprovante de Registro</h3>\n" +
                "                </div>\n" +
                "                <div class=\"panel-body\">\n" +
                "                    <div id=\"divComprovante228907\">\n" +
                "    <div style=\"width:420px; background-color: #f5ecbe; overflow:auto\">\n" +
                "        <div style=\"text-align:center;\">\n" +
                "            <p><b>Comprovante de Registro de Ponto Eletrônico.</b></p>\n" +
                "        </div>\n" +
                "        <div style=\"padding-top:10px\">\n" +
                "            <table>\n" +
                "                <tr>\n" +
                "                    <td>Empresa: </td>\n" +
                "                    <td>DB1 GLOBAL SOFTWARE S/A</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>CNPJ: </td>\n" +
                "                    <td>04.204.018/0001-66</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>CEI: </td>\n" +
                "                    <td></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Nome: </td>\n" +
                "                    <td>DANIEL FELIPE SARTORIO</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>PIS: </td>\n" +
                "                    <td>20767411786</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Data: </td>\n" +
                "                    <td>05/02/2019 &nbsp; &nbsp; Hora: 08:03</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>NSR: </td>\n" +
                "                    <td>228907</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div style=\"text-align:center\">\n" +
                "            <img src=\"data:image/png;base64,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\" />\n" +
                "        </div>\n" +
                "\n" +
                "        <div style='font-family: \"Courier New\", Courier, \"Lucida Sans Typewriter\", \"Lucida Typewriter\", monospace; padding:10px'>\n" +
                "        Chave de Seguran&#231;a:\n" +
                "rMYjVTYpNy+LPDhMCFOMzmW1QaydVzmb+bJ2Se5qN792            <br />\n" +
                "e13eQioci9a6K1Yrt6yix7YuSMXxjjEdZEzXIVuk74WS            <br />\n" +
                "mPYRjNEP1nIg0CRf24kld7YhfhzEXYlHQwqu0+8kGXvd            <br />\n" +
                "oRLHE7/bBfyVCN4iESdUlB86j2SihTJq35pBYbs6wXVk            <br />\n" +
                "ys1Mg94H9lP9jRJuNWBrwrznHfcV7D6f0Ja0JkrSMb3d            <br />\n" +
                "Qp8nWNxZjLkaIDWRhaX0V3BGS/Zy8AC0N3YvI44siXK6            <br />\n" +
                "hihzXdVgrnYZlO0E11+40eaGyhIzpiSL7GcCi5DoY0+4            <br />\n" +
                "wClQ4HraQyPRYjzce3/lkNUZ8WQj/h24131GNEyRxsQb            <br />\n" +
                "mY7icAkHpFHUO5opZ0b7YqxPt+Y7XSneZkFRwoSZ//Ge            <br />\n" +
                "j+kFZAw0Xw==            <br />\n" +
                "            \n" +
                "            \n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"panel-footer\">\n" +
                "                    <button type=\"button\" class=\"btn btn-info\" onclick=\"printDiv('divComprovante'+'228907')\"><span class=\"glyphicon glyphicon-print\"></span> Imprimir</button>\n" +
                "            <button type=\"button\" class=\"btn btn-primary\" onclick=\"location.href='/Comprovante/GetComprovante?EmpresaNome=DB1%20GLOBAL%20SOFTWARE%20S%2FA&amp;EmpresaCNPJ=04.204.018%2F0001-66&amp;FuncionarioNome=DANIEL%20FELIPE%20SARTORIO&amp;FuncionarioPIS=20767411786&amp;Data=05%2F02%2F2019&amp;Hora=08%3A03&amp;NS=228907&amp;ChaveSeguranca=rMYjVTYpNy%2BLPDhMCFOMzmW1QaydVzmb%2BbJ2Se5qN792e13eQioci9a6K1Yrt6yix7YuSMXxjjEdZEzXIVuk74WSmPYRjNEP1nIg0CRf24kld7YhfhzEXYlHQwqu0%2B8kGXvdoRLHE7%2FbBfyVCN4iESdUlB86j2SihTJq35pBYbs6wXVkys1Mg94H9lP9jRJuNWBrwrznHfcV7D6f0Ja0JkrSMb3dQp8nWNxZjLkaIDWRhaX0V3BGS%2FZy8AC0N3YvI44siXK6hihzXdVgrnYZlO0E11%2B40eaGyhIzpiSL7GcCi5DoY0%2B4wClQ4HraQyPRYjzce3%2FlkNUZ8WQj%2Fh24131GNEyRxsQbmY7icAkHpFHUO5opZ0b7YqxPt%2BY7XSneZkFRwoSZ%2F%2FGej%2BkFZAw0Xw%3D%3D&amp;ChaveSegurancaView=System.Collections.Generic.List%601%5BSystem.String%5D })';return false;\"><span class=\"glyphicon glyphicon-save\"></span> Salvar</button>\n" +
                "\n" +
                "\n" +
                "                    <button type=\"button\" class=\"btn btn-danger\" onclick=\"fecharComprovante()\"><span class=\"glyphicon glyphicon-trash\"></span> Fechar</button>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"panel panel-success\" style=\"width:450px;\">\n" +
                "                <div class=\"panel-heading\">\n" +
                "                    <button type=\"button\" class=\"close\" onclick=\"fecharComprovante()\">&times;</button>\n" +
                "                    <h3 class=\"panel-title\" style=\"text-align:center\">Comprovante de Registro</h3>\n" +
                "                </div>\n" +
                "                <div class=\"panel-body\">\n" +
                "                    <div id=\"divComprovante228616\">\n" +
                "    <div style=\"width:420px; background-color: #f5ecbe; overflow:auto\">\n" +
                "        <div style=\"text-align:center;\">\n" +
                "            <p><b>Comprovante de Registro de Ponto Eletrônico.</b></p>\n" +
                "        </div>\n" +
                "        <div style=\"padding-top:10px\">\n" +
                "            <table>\n" +
                "                <tr>\n" +
                "                    <td>Empresa: </td>\n" +
                "                    <td>DB1 GLOBAL SOFTWARE S/A</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>CNPJ: </td>\n" +
                "                    <td>04.204.018/0001-66</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>CEI: </td>\n" +
                "                    <td></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Nome: </td>\n" +
                "                    <td>DANIEL FELIPE SARTORIO</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>PIS: </td>\n" +
                "                    <td>20767411786</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Data: </td>\n" +
                "                    <td>04/02/2019 &nbsp; &nbsp; Hora: 17:59</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>NSR: </td>\n" +
                "                    <td>228616</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div style=\"text-align:center\">\n" +
                "            <img src=\"data:image/png;base64,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\" />\n" +
                "        </div>\n" +
                "\n" +
                "        <div style='font-family: \"Courier New\", Courier, \"Lucida Sans Typewriter\", \"Lucida Typewriter\", monospace; padding:10px'>\n" +
                "        Chave de Seguran&#231;a:\n" +
                "rMYjVTYpNy+LPDhMCFOMzmW1QaydVzmb+bJ2Se5qN792            <br />\n" +
                "e13eQioci9a6K1Yrt6yix7YuSMXxjjEdZEzXIVuk74WS            <br />\n" +
                "mPYRjNEP1nIg0CRf24kld7YhfhzEXYlHQwqu0+8kGXvd            <br />\n" +
                "oRLHE7/bBfyVCN4iESdUlB86j2SihTJq35pBYbs6wXVk            <br />\n" +
                "ys1Mg94H9lP9jRJuNWBrwrznHfcV7D6f0Ja0JkrSMb3d            <br />\n" +
                "Qp8nWNxZjLkaIDUx8WnbKLsVxyvCvnhtW5mHh6b/uTKj            <br />\n" +
                "ldvBWfsYR3wRWajgK/Wf7m43NGZ5SHTwzJjx+CTq/jhh            <br />\n" +
                "Gf4rKVjARGpdJDtlTE6cDeoSoFwlTVb9jv0sh2pah8z+            <br />\n" +
                "XyeqN9wJx3TClUmy3RbigMGhzd2+urUYW1ldfNstWLKs            <br />\n" +
                "ESWVISU+Lw==            <br />\n" +
                "            \n" +
                "            \n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"panel-footer\">\n" +
                "                    <button type=\"button\" class=\"btn btn-info\" onclick=\"printDiv('divComprovante'+'228616')\"><span class=\"glyphicon glyphicon-print\"></span> Imprimir</button>\n" +
                "            <button type=\"button\" class=\"btn btn-primary\" onclick=\"location.href='/Comprovante/GetComprovante?EmpresaNome=DB1%20GLOBAL%20SOFTWARE%20S%2FA&amp;EmpresaCNPJ=04.204.018%2F0001-66&amp;FuncionarioNome=DANIEL%20FELIPE%20SARTORIO&amp;FuncionarioPIS=20767411786&amp;Data=04%2F02%2F2019&amp;Hora=17%3A59&amp;NS=228616&amp;ChaveSeguranca=rMYjVTYpNy%2BLPDhMCFOMzmW1QaydVzmb%2BbJ2Se5qN792e13eQioci9a6K1Yrt6yix7YuSMXxjjEdZEzXIVuk74WSmPYRjNEP1nIg0CRf24kld7YhfhzEXYlHQwqu0%2B8kGXvdoRLHE7%2FbBfyVCN4iESdUlB86j2SihTJq35pBYbs6wXVkys1Mg94H9lP9jRJuNWBrwrznHfcV7D6f0Ja0JkrSMb3dQp8nWNxZjLkaIDUx8WnbKLsVxyvCvnhtW5mHh6b%2FuTKjldvBWfsYR3wRWajgK%2FWf7m43NGZ5SHTwzJjx%2BCTq%2FjhhGf4rKVjARGpdJDtlTE6cDeoSoFwlTVb9jv0sh2pah8z%2BXyeqN9wJx3TClUmy3RbigMGhzd2%2BurUYW1ldfNstWLKsESWVISU%2BLw%3D%3D&amp;ChaveSegurancaView=System.Collections.Generic.List%601%5BSystem.String%5D })';return false;\"><span class=\"glyphicon glyphicon-save\"></span> Salvar</button>\n" +
                "\n" +
                "\n" +
                "                    <button type=\"button\" class=\"btn btn-danger\" onclick=\"fecharComprovante()\"><span class=\"glyphicon glyphicon-trash\"></span> Fechar</button>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"panel panel-success\" style=\"width:450px;\">\n" +
                "                <div class=\"panel-heading\">\n" +
                "                    <button type=\"button\" class=\"close\" onclick=\"fecharComprovante()\">&times;</button>\n" +
                "                    <h3 class=\"panel-title\" style=\"text-align:center\">Comprovante de Registro</h3>\n" +
                "                </div>\n" +
                "                <div class=\"panel-body\">\n" +
                "                    <div id=\"divComprovante228406\">\n" +
                "    <div style=\"width:420px; background-color: #f5ecbe; overflow:auto\">\n" +
                "        <div style=\"text-align:center;\">\n" +
                "            <p><b>Comprovante de Registro de Ponto Eletrônico.</b></p>\n" +
                "        </div>\n" +
                "        <div style=\"padding-top:10px\">\n" +
                "            <table>\n" +
                "                <tr>\n" +
                "                    <td>Empresa: </td>\n" +
                "                    <td>DB1 GLOBAL SOFTWARE S/A</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>CNPJ: </td>\n" +
                "                    <td>04.204.018/0001-66</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>CEI: </td>\n" +
                "                    <td></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Nome: </td>\n" +
                "                    <td>DANIEL FELIPE SARTORIO</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>PIS: </td>");
    }
}