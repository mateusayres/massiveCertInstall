package br.com.massivecertinstall.form;

import br.com.massivecertinstall.util.Util;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

//@author github.com/mateusayres
public class TelaPrincipal extends javax.swing.JFrame {

    public TelaPrincipal() {
        initComponents();
        Util.inserirIcone(this);
    }

    public String pastaComCertificados = null; //Caminho OLD TESTE -> "C:\\Users\\SrAyres\\Downloads\\CertSima";
    public String arquivoDeSenhas = null; //Camimho OLD TESTE -> CERT_FOLDER + "\\senhas.txt";
    private boolean interromperProcesso = false;

    private class CertificadoInstaller extends SwingWorker<Void, String> {
        Util util = new Util();
        boolean algumCertificadoEncontrado = false;
        String[] senhas = util.lerSenhas(arquivoDeSenhas);

        @Override
        protected Void doInBackground() {
            File folder = new File(pastaComCertificados);
            File[] arquivosPfx = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pfx"));
            String localDeInstalacao = (rbUsuarioAtual.isSelected()) ? "CurrentUser" : "LocalMachine";
            String isExportavel = (cbExportavel.isSelected()) ? "-Exportable" : "";
            int contadorDaVezDaSenha = 0;
            logArea.setText("");
            barraDeProgresso.setValue(0);
            lbBarraDeProgresso.setText("0 % - Aguarde...");

            if (arquivosPfx != null && senhas.length != 0) {
                int totalArquivos = arquivosPfx.length;
                int totalInstalados = 0;
                int totalNaoInstalados = 0;
                lbTotal.setText("Total : " + totalArquivos);
                publish(" :: v1.4 By: github.com/mateusayres");
                publish("");
                publish(" :: Bem-vindo ao MassiveCertInstall, aguarde a mágica acontecer!");
                publish("");
                publish(" :: CONFIGURAÇÃO DE INSTALAÇÃO:");
                publish(" :: Local -> " + localDeInstalacao);
                publish(" :: Exportável -> " + cbExportavel.isSelected());
                publish(" :: Senhas encontradas (senhas.txt) -> " + senhas.length);
                publish("");
                publish(" :: Pode parecer parado, mas está testando todas as senhas em ordem!");
                publish(" :: LOG_STATUS:");

                for (int i = 0; i < totalArquivos; i++) {

                    if (interromperProcesso || isCancelled()) {
                        publish(" :: Processo interrompido pelo usuário.");
                        break; // Sai do loop externo
                    }

                    File arquivo = arquivosPfx[i];
                    boolean sucesso = false;
                    algumCertificadoEncontrado = true;
                    contadorDaVezDaSenha = 0;

                    for (String senha : senhas) {

                        if (interromperProcesso || isCancelled()) {
                            break; // Sai do loop interno
                        }
                        
                        contadorDaVezDaSenha++;
                        sucesso = util.importarCertificado(arquivo.getPath(), senha, localDeInstalacao, isExportavel);
                        
                        if (sucesso) {
                            publish("[" + (i + 1) + "] :: " + util.getDataPtBr() + " :: [INSTALADO] :: " + arquivo.getName() + " :: Instalado com a senha nº "+ contadorDaVezDaSenha +" -> " + senha);
                            totalInstalados = totalInstalados + 1;
                            break; // Sai do loop interno
                        }
                    }

                    if (!sucesso) {
                        publish("[" + (i + 1) + "] :: " + util.getDataPtBr() + " :: [NÃO INSTALADO] :: " + arquivo.getName() + " :: Nenhuma senha válida encontrada.");
                        totalNaoInstalados = totalNaoInstalados + 1;
                    }

                    // Atualiza status
                    int progress = (i + 1) * 100 / totalArquivos; // Calcula a porcentagem
                    barraDeProgresso.setValue(progress); // Atualiza o progresso
                    lbBarraDeProgresso.setText(progress + " % - Aguarde...");
                    lbInstalados.setText("Instalados : " + totalInstalados);
                    lbNaoInstalados.setText("Não Instalados : " + totalNaoInstalados);
                }
            }

            return null;
        }

        @Override
        protected void process(java.util.List<String> chunks) {
            for (String message : chunks) {
                logArea.append(message + "\n"); // Atualiza o JTextArea com os logs
            }
        }

        @Override
        protected void done() {
            barraDeProgresso.setValue(100); // Define o progresso como completo
            rbMaquinaLocal.setEnabled(true);
            rbUsuarioAtual.setEnabled(true);
            cbExportavel.setEnabled(true);
            btEscolherPasta.setEnabled(true);
            btInstalar.setEnabled(true);

            // Verifica se nenhum certificado foi encontrado, e se deve por a mensagem referente a esse erro.
            if (algumCertificadoEncontrado) {
                lbBarraDeProgresso.setText("100 % - Finalizado");
                util.salvarLog(logArea.getText(), txCaminhoPasta.getText());
            } else {
                if (senhas.length != 0) {
                    JOptionPane.showMessageDialog(rootPane, "Nenhum Certificado A1 foi encontrado, por favor selecione outra pasta.");
                }
            }
        }
    }

    public void statusGeral(Boolean status) {
        try {
            CertificadoInstaller certificadoInstaller = new CertificadoInstaller();
            interromperProcesso = status;
            certificadoInstaller.cancel(status); // Cancela a execução do SwingWorker
        } catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        localDoRespositorioGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        sTitle = new javax.swing.JSeparator();
        sOpcoes = new javax.swing.JSeparator();
        lbTitle = new javax.swing.JLabel();
        lbSubTitle1 = new javax.swing.JLabel();
        lbSubSubTitle1 = new javax.swing.JLabel();
        lbBy = new javax.swing.JLabel();
        lbBarraDeProgresso = new javax.swing.JLabel();
        lbNaoInstalados = new javax.swing.JLabel();
        lbInstalados = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        lbOpcoes = new javax.swing.JLabel();
        lbOther = new javax.swing.JLabel();
        lbOther2 = new javax.swing.JLabel();
        lbOther3 = new javax.swing.JLabel();
        barraDeProgresso = new javax.swing.JProgressBar();
        rbUsuarioAtual = new javax.swing.JRadioButton();
        rbMaquinaLocal = new javax.swing.JRadioButton();
        cbChavesPrivadasFortes = new javax.swing.JCheckBox();
        cbExportavel = new javax.swing.JCheckBox();
        cbPropiedadesExtendidas = new javax.swing.JCheckBox();
        btInstalar = new javax.swing.JButton();
        btEscolherPasta = new javax.swing.JButton();
        btParar = new javax.swing.JButton();
        txCaminhoPasta = new javax.swing.JTextField();
        logScrollPane = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        lbOther4 = new javax.swing.JLabel();
        lbTitle1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MassiveCertInstall - Quer Instalar todos os certificados A1 em massa? Este é o lugar certo!");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setAutoscrolls(true);
        jPanel1.setMaximumSize(new java.awt.Dimension(960, 720));
        jPanel1.setMinimumSize(new java.awt.Dimension(960, 720));

        sTitle.setForeground(new java.awt.Color(255, 255, 255));

        sOpcoes.setForeground(new java.awt.Color(255, 255, 255));
        sOpcoes.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTitle.setText("MassiveCertInstall");
        lbTitle.setAutoscrolls(true);
        lbTitle.setPreferredSize(new java.awt.Dimension(294, 60));

        lbSubTitle1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbSubTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lbSubTitle1.setText("Caminho da pasta com \"Certificados A1 (*.pfx)\" e \"senhas.txt\" :");
        lbSubTitle1.setAutoscrolls(true);

        lbSubSubTitle1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lbSubSubTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lbSubSubTitle1.setText("*Dentro da pasta deve conter todos os arquivos para instalação (.pfx) e conter um arquivo \"senhas.txt\" com todas as senhas uma abaixo da outra. ");
        lbSubSubTitle1.setAutoscrolls(true);

        lbBy.setBackground(new java.awt.Color(255, 255, 255));
        lbBy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbBy.setForeground(new java.awt.Color(153, 153, 153));
        lbBy.setText("v1.4 By: github.com/mateusayres");
        lbBy.setAutoscrolls(true);

        lbBarraDeProgresso.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lbBarraDeProgresso.setForeground(new java.awt.Color(255, 255, 255));
        lbBarraDeProgresso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbBarraDeProgresso.setText("0 % - Aguardando iniciar...");
        lbBarraDeProgresso.setAutoscrolls(true);

        lbNaoInstalados.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lbNaoInstalados.setForeground(new java.awt.Color(255, 153, 153));
        lbNaoInstalados.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNaoInstalados.setText("Não Instalados : 0");
        lbNaoInstalados.setAutoscrolls(true);

        lbInstalados.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lbInstalados.setForeground(new java.awt.Color(153, 255, 153));
        lbInstalados.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInstalados.setText("Instalados : 0");
        lbInstalados.setAutoscrolls(true);

        lbTotal.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 255, 255));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotal.setText("Total : 0");
        lbTotal.setAutoscrolls(true);

        lbOpcoes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbOpcoes.setForeground(new java.awt.Color(255, 255, 255));
        lbOpcoes.setText("Opções de instalação do Certificado:");
        lbOpcoes.setAutoscrolls(true);

        lbOther.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbOther.setForeground(new java.awt.Color(255, 255, 255));
        lbOther.setText("Local do Repositório:");
        lbOther.setAutoscrolls(true);

        lbOther2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lbOther2.setForeground(new java.awt.Color(255, 255, 255));
        lbOther2.setText("Parâmetro indisponível via Software. (Padrão: DESABILITADO)");
        lbOther2.setAutoscrolls(true);
        lbOther2.setEnabled(false);

        lbOther3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lbOther3.setForeground(new java.awt.Color(255, 255, 255));
        lbOther3.setText("Isso possibilitará o backup ou o transporte das chaves posteriormente.");
        lbOther3.setAutoscrolls(true);

        barraDeProgresso.setBackground(new java.awt.Color(153, 153, 153));
        barraDeProgresso.setForeground(new java.awt.Color(153, 255, 204));
        barraDeProgresso.setAutoscrolls(true);
        barraDeProgresso.setBorder(null);

        rbUsuarioAtual.setBackground(new java.awt.Color(102, 102, 102));
        localDoRespositorioGroup.add(rbUsuarioAtual);
        rbUsuarioAtual.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbUsuarioAtual.setForeground(new java.awt.Color(255, 255, 255));
        rbUsuarioAtual.setSelected(true);
        rbUsuarioAtual.setText("Usuário Atual");

        rbMaquinaLocal.setBackground(new java.awt.Color(102, 102, 102));
        localDoRespositorioGroup.add(rbMaquinaLocal);
        rbMaquinaLocal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbMaquinaLocal.setForeground(new java.awt.Color(255, 255, 255));
        rbMaquinaLocal.setText("Máquina Local");

        cbChavesPrivadasFortes.setBackground(new java.awt.Color(102, 102, 102));
        cbChavesPrivadasFortes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbChavesPrivadasFortes.setForeground(new java.awt.Color(255, 255, 255));
        cbChavesPrivadasFortes.setText("Habilitar proteção de chaves privadas fortes.");
        cbChavesPrivadasFortes.setEnabled(false);

        cbExportavel.setBackground(new java.awt.Color(102, 102, 102));
        cbExportavel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbExportavel.setForeground(new java.awt.Color(255, 255, 255));
        cbExportavel.setSelected(true);
        cbExportavel.setText("Marcar esta chave como exportável.");

        cbPropiedadesExtendidas.setBackground(new java.awt.Color(102, 102, 102));
        cbPropiedadesExtendidas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbPropiedadesExtendidas.setForeground(new java.awt.Color(255, 255, 255));
        cbPropiedadesExtendidas.setSelected(true);
        cbPropiedadesExtendidas.setText("Incluir todas as propiedades estendidas.");
        cbPropiedadesExtendidas.setEnabled(false);

        btInstalar.setBackground(new java.awt.Color(51, 51, 51));
        btInstalar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btInstalar.setForeground(new java.awt.Color(255, 255, 255));
        btInstalar.setText("Instalar Todos");
        btInstalar.setAutoscrolls(true);
        btInstalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInstalarActionPerformed(evt);
            }
        });

        btEscolherPasta.setBackground(new java.awt.Color(51, 51, 51));
        btEscolherPasta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btEscolherPasta.setForeground(new java.awt.Color(255, 255, 255));
        btEscolherPasta.setText("Escolher Pasta");
        btEscolherPasta.setAutoscrolls(true);
        btEscolherPasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEscolherPastaActionPerformed(evt);
            }
        });

        btParar.setBackground(new java.awt.Color(51, 51, 51));
        btParar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btParar.setForeground(new java.awt.Color(255, 255, 255));
        btParar.setText("Parar");
        btParar.setAutoscrolls(true);
        btParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPararActionPerformed(evt);
            }
        });

        txCaminhoPasta.setEditable(false);
        txCaminhoPasta.setBackground(new java.awt.Color(204, 204, 204));
        txCaminhoPasta.setFocusable(false);

        logScrollPane.setAutoscrolls(true);

        logArea.setEditable(false);
        logArea.setBackground(new java.awt.Color(204, 204, 204));
        logArea.setColumns(20);
        logArea.setRows(5);
        logArea.setMargin(new java.awt.Insets(2, 10, 2, 10));
        logScrollPane.setViewportView(logArea);

        lbOther4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lbOther4.setForeground(new java.awt.Color(255, 255, 255));
        lbOther4.setText("Parâmetro indisponível via Software. (Padrão: HABILITADO)");
        lbOther4.setAutoscrolls(true);
        lbOther4.setEnabled(false);

        lbTitle1.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        lbTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTitle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/massivecertinstall/image/logoMassiveCertInstall_small.png"))); // NOI18N
        lbTitle1.setAutoscrolls(true);
        lbTitle1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbTitle1.setPreferredSize(new java.awt.Dimension(294, 60));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sTitle))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(lbBarraDeProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(lbNaoInstalados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lbInstalados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(barraDeProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btInstalar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btParar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(logScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lbOpcoes)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(lbSubSubTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(91, 91, 91)
                                            .addComponent(btEscolherPasta))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(16, 16, 16)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(rbUsuarioAtual)
                                                .addComponent(rbMaquinaLocal)
                                                .addComponent(lbOther))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(sOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(cbChavesPrivadasFortes)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lbOther2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(cbExportavel)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lbOther3, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(cbPropiedadesExtendidas)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lbOther4, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(txCaminhoPasta, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(lbSubTitle1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbBy)))
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbSubTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txCaminhoPasta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btEscolherPasta)
                    .addComponent(lbSubSubTitle1))
                .addGap(14, 14, 14)
                .addComponent(lbOpcoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lbOther)
                            .addGap(8, 8, 8)
                            .addComponent(rbUsuarioAtual)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rbMaquinaLocal))
                        .addComponent(sOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbChavesPrivadasFortes)
                            .addComponent(lbOther2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbExportavel)
                            .addComponent(lbOther3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbPropiedadesExtendidas)
                            .addComponent(lbOther4))))
                .addGap(18, 18, 18)
                .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btInstalar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbInstalados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNaoInstalados))
                    .addComponent(lbBarraDeProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTotal)
                            .addComponent(barraDeProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(lbBy)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btParar)
                        .addGap(56, 56, 56))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btInstalarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInstalarActionPerformed
        if (!"".equals(txCaminhoPasta.getText())) {

            // Configura o diretório dos certificados e o arquivo de senhas
            pastaComCertificados = txCaminhoPasta.getText();
            arquivoDeSenhas = pastaComCertificados + "\\senhas.txt";

            rbMaquinaLocal.setEnabled(false);
            rbUsuarioAtual.setEnabled(false);
            cbExportavel.setEnabled(false);
            btEscolherPasta.setEnabled(false);
            btInstalar.setEnabled(false);
            lbTotal.setText("Total : 0");
            lbInstalados.setText("Instalados : 0");
            lbNaoInstalados.setText("Não Instalados : 0");
            statusGeral(false);

            // Inicia o SwingWorker para importar os certificados em segundo plano
            new CertificadoInstaller().execute();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma pasta primeiro!");
        }
    }//GEN-LAST:event_btInstalarActionPerformed

    private void btEscolherPastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEscolherPastaActionPerformed
        // Cria um JFileChooser para selecionar uma pasta
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Escolha uma pasta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Permite selecionar apenas pastas

        // Exibe a janela de escolha
        int returnValue = chooser.showOpenDialog(null);

        // Verifica se o usuário selecionou uma pasta
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File pastaSelecionada = chooser.getSelectedFile(); // Obtém a pasta selecionada

            //Seta no campo de texto a pasta selecionada
            txCaminhoPasta.setText(pastaSelecionada.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(rootPane, "Nenhuma pasta foi selecionada!");
        }
    }//GEN-LAST:event_btEscolherPastaActionPerformed

    private void btPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPararActionPerformed
        statusGeral(true);
    }//GEN-LAST:event_btPararActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraDeProgresso;
    private javax.swing.JButton btEscolherPasta;
    private javax.swing.JButton btInstalar;
    private javax.swing.JButton btParar;
    private javax.swing.JCheckBox cbChavesPrivadasFortes;
    private javax.swing.JCheckBox cbExportavel;
    private javax.swing.JCheckBox cbPropiedadesExtendidas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbBarraDeProgresso;
    private javax.swing.JLabel lbBy;
    private javax.swing.JLabel lbInstalados;
    private javax.swing.JLabel lbNaoInstalados;
    private javax.swing.JLabel lbOpcoes;
    private javax.swing.JLabel lbOther;
    private javax.swing.JLabel lbOther2;
    private javax.swing.JLabel lbOther3;
    private javax.swing.JLabel lbOther4;
    private javax.swing.JLabel lbSubSubTitle1;
    private javax.swing.JLabel lbSubTitle1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTitle1;
    private javax.swing.JLabel lbTotal;
    private javax.swing.ButtonGroup localDoRespositorioGroup;
    public javax.swing.JTextArea logArea;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JRadioButton rbMaquinaLocal;
    private javax.swing.JRadioButton rbUsuarioAtual;
    private javax.swing.JSeparator sOpcoes;
    private javax.swing.JSeparator sTitle;
    public javax.swing.JTextField txCaminhoPasta;
    // End of variables declaration//GEN-END:variables
}
