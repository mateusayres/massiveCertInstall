package br.com.massivecertinstall.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final String CERT_FOLDER = "C:\\Users\\SrAyres\\Downloads\\CertSima";
    private static final String PASSWORD_FILE = CERT_FOLDER + "\\senhas.txt";

    public static void main(String[] args) {
        String[] senhas = lerSenhas(PASSWORD_FILE);

        File folder = new File(CERT_FOLDER);
        File[] arquivosPfx = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pfx"));

        if (arquivosPfx != null) {
            for (File arquivo : arquivosPfx) {
                boolean sucesso = false;

                for (String senha : senhas) {
                    // Tenta importar o certificado
                    sucesso = importarCertificado(arquivo.getPath(), senha);
                    if (sucesso) {
                        System.out.println("Certificado " + arquivo.getName() + " instalado com sucesso com a senha: " + senha);
                        break; // Sai do loop se a instalação for bem-sucedida
                    } else {
                        System.out.println("Senha incorreta para " + arquivo.getName() + " com a senha: " + senha);
                    }
                }

                if (!sucesso) {
                    System.out.println("Falha ao instalar o certificado " + arquivo.getName() + ": nenhuma senha valida encontrada.");
                }
            }
        }
    }

    private static String[] lerSenhas(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            return reader.lines().toArray(String[]::new);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de senhas: " + e.getMessage());
            return new String[0];
        }
    }

    private static boolean importarCertificado(String arquivoPfx, String senha) {
        try {
            // Comando PowerShell para importar o certificado
            String command = String.format(
                    "powershell -Command \"try { Import-PfxCertificate -FilePath '%s' -CertStoreLocation Cert:\\LocalMachine\\My -Password (ConvertTo-SecureString -String '%s' -Force -AsPlainText) -Exportable; } catch { exit 1; }\"",
                    arquivoPfx, senha);

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            return exitCode == 0; // Retorna verdadeiro se o comando for executado com sucesso
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao executar o comando PowerShell: " + e.getMessage());
            return false;
        }
    }
}
