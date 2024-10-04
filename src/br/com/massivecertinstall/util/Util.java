package br.com.massivecertinstall.util;

import br.com.massivecertinstall.form.TelaPrincipal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Util {
    TelaPrincipal telaprincipal = new TelaPrincipal();

    public String[] lerSenhas(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            return reader.lines().toArray(String[]::new);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(telaprincipal, getDataPtBr() + "\n" + "Erro ao ler o arquivo de senhas: " + "\n" + e.getMessage());
            return new String[0];
        }
    }

    public boolean importarCertificado(String arquivoPfx, String senha, String localDeInstalacao, String isExportavel) {
        //Comando Inicial BASE que funciona no powershell: 
        //powershell -Command "try { Import-PfxCertificate -FilePath 'C:\Users\VMTeste\Desktop\BARRA_SERVICOS_E_CONTROLADORIA_LTDA_34991770000165_1695670579665298800.pfx' -CertStoreLocation Cert:\\LocalMachine\\My -Password (ConvertTo-SecureString -String '123456789' -Force -AsPlainText) -Exportable; } catch { exit 1; }"
        
        try {
            String command = String.format(
                    "powershell -Command \"try { Import-PfxCertificate -FilePath '%s' -CertStoreLocation Cert:\\%s\\My -Password (ConvertTo-SecureString -String '%s' -Force -AsPlainText) %s; } catch { exit 1; }\"",
                    arquivoPfx, localDeInstalacao, senha, isExportavel);

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            return exitCode == 0; // Retorna verdadeiro se o comando for executado com sucesso
        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(telaprincipal, getDataPtBr() + "\n" + "Erro ao executar o comando PowerShell: " + "\n" + e.getMessage());
            return false;
        }
    }

    public String getDataPtBr() {
        Date data = new Date();
        String dataFormatada = null;

        SimpleDateFormat formatafor = new SimpleDateFormat("dd/MM/yyyy :: HH:mm:ss");
        dataFormatada = formatafor.format(data);

        return dataFormatada;
    }

    public void salvarLog(String logArea, String txCaminhoPasta) {
        TelaPrincipal telaprincipal = new TelaPrincipal();
        try {
            String filePath = txCaminhoPasta + "\\logFinal_MassiveCertInstall.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(logArea);
            }
            getFinish();
            JOptionPane.showMessageDialog(telaprincipal, "Log Geral salvo em: \n" + filePath);
        } catch (IOException e) {
            getFinish();
            JOptionPane.showMessageDialog(telaprincipal, "Erro ao salvar o log: \n" + e.getMessage());
        }
    }
    
    public void getFinish() {
        JOptionPane.showMessageDialog(telaprincipal, "Finalizado!" + "\n" + getDataPtBr());
    }
    
    public static void inserirIcone(JFrame form){
        try{
            form.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\br\\com\\massivecertinstall\\image\\logoMassiveCertInstall.png"));
        }catch(Exception ex){
            System.out.print(ex.toString());
        }
    }
}
