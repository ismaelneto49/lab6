package sapo.utils;

public abstract class Validacao {

    public static void validarCampoVazio(String campo, String nomeCampo) {
        if (campo == null || campo.isBlank()) {
            String mensagem = "Campo " + nomeCampo.trim() + " não pode ser vazio";
            throw new IllegalArgumentException(mensagem);
        }
    }
}
