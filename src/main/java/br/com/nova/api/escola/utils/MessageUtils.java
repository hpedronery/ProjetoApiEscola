package br.com.nova.api.escola.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

/**
 * Classe responsável pela busca e manipulação das mensagens do sistema
 */
public final class MessageUtils {

    private MessageUtils() {
    }

    /**
     * Busca uma mensagem no arquivo properties informado através de sua chave
     *
     * @param nomeArquivo   o nome do arquivo que se deseja buscar a mensagem
     * @param chaveMensagem a chave da mensagem
     * @param params        a lista de parâmetros que será injetada na mensagem pelo pattern definido no {@link MessageFormat}
     * @return a mensagem buscada com os parâmetros injetados
     */
    public static String buscarMensagem(final String nomeArquivo, final String chaveMensagem, final Object... params) {
        return recuperarTextoProperties(nomeArquivo, chaveMensagem, params);
    }

    /**
     * Refere-se ao método
     * {@link MessageUtils#buscarMensagem(String, String, Object...)}
     * onde o parâmetro {@code nomeArquivo} padroniza-se como {@code "messages"}
     */
    public static String buscarMensagem(final String chaveMensagem, final Object... params) {
        return recuperarTextoProperties("messages", chaveMensagem, params);
    }

    /**
     * Refere-se ao método
     * {@link MessageUtils#buscarMensagem(String, String, Object...)}
     * onde o parâmetro {@code nomeArquivo} padroniza-se como {@code "ValidationMessages"}
     */
    public static String buscarMensagemValidacao(final String chaveMensagem, final Object... params) {
        return recuperarTextoProperties("ValidationMessages", chaveMensagem, params);
    }

    private static String recuperarTextoProperties(final String nomeArquivo, final String chaveMensagem, final Object params) {
        final ResourceBundle bundle = getBundle(nomeArquivo, getDefault());
        try {
            return MessageFormat.format(bundle.getString(chaveMensagem), params);
        } catch (final MissingResourceException e) {
            return chaveMensagem;
        }
    }
}
