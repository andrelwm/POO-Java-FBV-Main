import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MySquad extends JFrame{

    

    final private Font fontePrincipal = new Font("Arial", Font.BOLD, 18);
    final private Color textoPrincipal = new Color(255, 255, 255);
    final private Color fundoPrincipal = new Color(25, 25, 112);
    private JTextField tfUsuario, tfEmail, pfSenha, pesquisaField, tfNovoUsuario, pfNovaSenha, nomeField, DDDField, telField;
    private JList<String> amigosList, rankingList;
    private JComboBox<String> cbjogos;
    private JComboBox<String> cbregiao;
    private JTable tabelaUsuarios;
    private DefaultTableModel model;
    private ArrayList<String> listaDados;
    private JLabel arquivoEscolhidoCaminho;
    private BufferedImage imagem;
    private static int usuarioLogado;
    private static String nick, imgUrl, caminhoArquivo;


    //JANELA DE LOGIN
    private void Login(){

        //Label e Text Field do usuário
        JLabel lbUsuario = new JLabel("Usuario");
        lbUsuario.setFont(fontePrincipal);
        lbUsuario.setForeground(Color.WHITE);
        lbUsuario.setHorizontalAlignment(SwingConstants.CENTER);

        tfUsuario = new JTextField();
        tfUsuario.setFont(fontePrincipal);

        //Label e Password Field da senha
        JLabel lbSenha = new JLabel("Senha");
        lbSenha.setFont(fontePrincipal);
        lbSenha.setForeground(textoPrincipal);
        lbSenha.setHorizontalAlignment(SwingConstants.CENTER);

        pfSenha = new JPasswordField();
        pfSenha.setFont(fontePrincipal);

        JButton btnEntrar = new JButton("Entrar");
        //btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("./images/teste.png")));
        btnEntrar.setFont(fontePrincipal);
        btnEntrar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            Logar();               
                    
            }

        });

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(fontePrincipal);
        btnCadastrar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MySquad menu = new MySquad();
                menu.Cadastro();
                setVisible(false);
            }
        });        

        JPanel painelBotao  = new JPanel();
        painelBotao.setLayout(new GridLayout(1, 2, 20, 7));
        painelBotao.setPreferredSize(new Dimension(110, 35));
        painelBotao.setOpaque(false);
        painelBotao.add(btnEntrar);
        painelBotao.add(btnCadastrar);
        getRootPane().setDefaultButton(btnEntrar);

        JPanel formPainel = new JPanel();
        formPainel.setLayout(new GridLayout(4, 1, 5, 5));
        formPainel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        formPainel.setOpaque(false);
        formPainel.add(lbUsuario);
        formPainel.add(tfUsuario);
        formPainel.add(lbSenha);
        formPainel.add(pfSenha);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(fundoPrincipal);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        painelPrincipal.add(formPainel, BorderLayout.NORTH);
        painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

        add(painelPrincipal);

        setTitle("MySquad");
        setSize(350, 300);
        setMinimumSize(new Dimension(300, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    //JANELA DE CADASTRO
    private void Cadastro() {

        //Campos do cadastro
        JLabel lbNovoUsuario = new JLabel("Usuário");
        lbNovoUsuario.setFont(fontePrincipal);
        lbNovoUsuario.setForeground(textoPrincipal);
        lbNovoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        tfNovoUsuario = new JTextField();
        tfNovoUsuario.setFont(fontePrincipal);

        JLabel lbNovaSenha = new JLabel("Senha");
        lbNovaSenha.setFont(fontePrincipal);
        lbNovaSenha.setForeground(textoPrincipal);
        lbNovaSenha.setHorizontalAlignment(SwingConstants.CENTER);

        pfNovaSenha = new JPasswordField();
        pfNovaSenha.setFont(fontePrincipal);

        JLabel lbEmail = new JLabel("E-mail");
        lbEmail.setFont(fontePrincipal);
        lbEmail.setForeground(textoPrincipal);
        lbEmail.setHorizontalAlignment(SwingConstants.CENTER);

        tfEmail = new JTextField();
        tfEmail.setFont(fontePrincipal);

        JButton btnConcluiCadastro = new JButton("Cadastrar");
        btnConcluiCadastro.setFont(fontePrincipal);
        btnConcluiCadastro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (Cadastrar()) {
                    MySquad menu = new MySquad();
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "MySquad - Cadastro", JOptionPane.INFORMATION_MESSAGE);
                    menu.Login();
                    setVisible(false);
                }
                
            }
        });  

        JButton btnCancela = new JButton("Cancelar");
        btnCancela.setFont(fontePrincipal);
        btnCancela.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                MySquad menu = new MySquad();
                menu.Login();
                setVisible(false);
                
            }
        });  

        //Painel para os campos do cadastro
        JPanel formPainel = new JPanel();
        formPainel.setLayout(new GridLayout(6, 3, 10, 10));
        formPainel.setOpaque(false);
        formPainel.add(lbNovoUsuario);
        formPainel.add(tfNovoUsuario);
        formPainel.add(lbNovaSenha);
        formPainel.add(pfNovaSenha);
        formPainel.add(lbEmail);
        formPainel.add(tfEmail);

        //Painel do botão de conclusão do cadastro
        JPanel painelBotao  = new JPanel();
        painelBotao.setLayout(new GridLayout(1, 2, 20, 7));
        painelBotao.setPreferredSize(new Dimension(110, 35));
        painelBotao.setOpaque(false);
        painelBotao.add(btnConcluiCadastro);
        painelBotao.add(btnCancela);
        getRootPane().setDefaultButton(btnConcluiCadastro);

        //Painel Principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(fundoPrincipal);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 100, 10, 100));
        painelPrincipal.add(formPainel, BorderLayout.NORTH);
        painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

        setTitle("My Squad - Cadastro");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        add(painelPrincipal);

    }

    //JANELA PRINCIPAL
    private void Principal() {
        setTitle("My Squad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //Barra de Navegação
        JPanel navigationBar = new JPanel();
        GridLayout navigationbarlayout = new GridLayout(1, 6);
        navigationbarlayout.setHgap(20);
        navigationBar.setLayout(navigationbarlayout);
        navigationBar.setPreferredSize(new Dimension(800, 50));
        navigationBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        navigationBar.setBackground(fundoPrincipal);

        // Menu de navegação
        JPanel navigationMenu = new JPanel();
        GridLayout navigationlayout = new GridLayout(1, 4);
        navigationlayout.setHgap(5);
        navigationMenu.setLayout(navigationlayout);
        navigationMenu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        navigationMenu.setBackground(fundoPrincipal);
        JButton homeButton = new JButton("Início");
        homeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                        MySquad menu = new MySquad();
                        menu.Principal();
                        setVisible(false);

                }
            });

        JButton gamesButton = new JButton("Jogos");

        
        JButton profileButton = new JButton("Perfil");
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    MySquad menu = new MySquad();
                    menu.profile();
                    setVisible(false);

            }
        });
        JButton friendsButton = new JButton("Amigos");
        friendsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    MySquad menu = new MySquad();
                    menu.friendsPanel();
                    setVisible(false);

            }
        });
        navigationMenu.add(homeButton);
        navigationMenu.add(profileButton);
        navigationMenu.add(gamesButton);
        navigationMenu.add(friendsButton);
        navigationBar.add(navigationMenu, BorderLayout.WEST);

        //Barra de Pesquisa
        JPanel navigationPesquisa = new JPanel();
        GridLayout pesquisalayout = new GridLayout(1, 3);
        pesquisalayout.setHgap(5);
        navigationPesquisa.setLayout(pesquisalayout);
        navigationPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 5));
        navigationPesquisa.setBackground(fundoPrincipal);
        navigationBar.add(navigationPesquisa, BorderLayout.EAST);
        pesquisaField = new JTextField(20);
        JButton pesquisaButton = new JButton("Pesquisar");
        pesquisaButton.setPreferredSize(new Dimension(50, 30));
        pesquisaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                realizarPesquisa();
                
            }
        });
        navigationPesquisa.add(pesquisaField);
        navigationPesquisa.add(pesquisaButton);

        mainPanel.add(navigationBar, BorderLayout.NORTH);

        // Barra de amigos
        JPanel amigosPanel = new JPanel();
        amigosPanel.setLayout(new BorderLayout());
        JLabel amigosLabel = new JLabel("Amigos Online");
        amigosList = new JList<>(new String[]{"Amigo 1", "Amigo 2", "Amigo 3"});
        amigosPanel.add(amigosLabel, BorderLayout.NORTH);
        amigosPanel.add(new JScrollPane(amigosList), BorderLayout.CENTER);
        mainPanel.add(amigosPanel, BorderLayout.WEST);

        // Ranking
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BorderLayout());
        JLabel rankingLabel = new JLabel("Ranking");
        rankingList = new JList<>(new String[]{"Jogador 1", "Jogador 2", "Jogador 3"});
        rankingPanel.add(rankingLabel, BorderLayout.NORTH);
        rankingPanel.add(new JScrollPane(rankingList), BorderLayout.CENTER);
        mainPanel.add(rankingPanel, BorderLayout.EAST);


        //Painel de resultados        
        tabelaUsuarios = new JTable(model);
        JScrollPane resultados = new JScrollPane(tabelaUsuarios);
        resultados.setPreferredSize(new Dimension(100, 100));
        mainPanel.add(resultados, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }

    //JANELA DE PERFIL
    public void profile() {
        setTitle("My Squad - Perfil");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //Barra de Navegação
        JPanel navigationBar = new JPanel();
        GridLayout navigationbarlayout = new GridLayout(1, 6);
        navigationbarlayout.setHgap(20);
        navigationBar.setLayout(navigationbarlayout);
        navigationBar.setPreferredSize(new Dimension(800, 50));
        navigationBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        navigationBar.setBackground(fundoPrincipal);

        // Menu de navegação
        JPanel navigationMenu = new JPanel();
        GridLayout navigationlayout = new GridLayout(1, 4);
        navigationlayout.setHgap(5);
        navigationMenu.setLayout(navigationlayout);
        navigationMenu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        navigationMenu.setBackground(fundoPrincipal);
        JButton homeButton = new JButton("Início");
        homeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                        MySquad menu = new MySquad();
                        menu.Principal();
                        setVisible(false);

                }
            });

        JButton gamesButton = new JButton("Jogos");

        
        JButton profileButton = new JButton("Perfil");
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    MySquad menu = new MySquad();
                    menu.profile();
                    setVisible(false);

            }
        });
        JButton friendsButton = new JButton("Amigos");
        friendsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    MySquad menu = new MySquad();
                    menu.friendsPanel();
                    setVisible(false);

            }
        });
        navigationMenu.add(homeButton);
        navigationMenu.add(profileButton);
        navigationMenu.add(gamesButton);
        navigationMenu.add(friendsButton);
        navigationBar.add(navigationMenu, BorderLayout.WEST);

        //Barra de Pesquisa
        JPanel navigationPesquisa = new JPanel();
        GridLayout pesquisalayout = new GridLayout(1, 3);
        pesquisalayout.setHgap(5);
        navigationPesquisa.setLayout(pesquisalayout);
        navigationPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 5));
        navigationPesquisa.setBackground(fundoPrincipal);
        navigationBar.add(navigationPesquisa, BorderLayout.EAST);
        pesquisaField = new JTextField(20);
        JButton pesquisaButton = new JButton("Pesquisar");
        pesquisaButton.setPreferredSize(new Dimension(50, 30));
        pesquisaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                realizarPesquisa();
                
            }
        });
        navigationPesquisa.add(pesquisaField);
        navigationPesquisa.add(pesquisaButton);

        mainPanel.add(navigationBar, BorderLayout.NORTH);

        //Painel Lateral
        JPanel lateralPanel = new JPanel();
        GridLayout lateralLayout = new GridLayout(2, 1);
        lateralPanel.setLayout(lateralLayout);
        lateralPanel.setPreferredSize(new Dimension(200, 600));
        lateralLayout.setVgap(15);

        //Painel para a foto
        JPanel fotoPanel = new JPanel();
        GridLayout fotoPanelLayout = new GridLayout(2, 1);
        fotoPanel.setLayout(fotoPanelLayout);
        fotoPanel.setPreferredSize(new Dimension(200, 400));
        fotoPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 20));
        lateralPanel.add(fotoPanel, BorderLayout.NORTH);

        //Label da foto
        JLabel fotoLabel = new JLabel();
        fotoLabel.setPreferredSize(new Dimension(200, 300));
        fotoLabel.setFont(fontePrincipal);
        fotoLabel.setForeground(Color.WHITE);
        fotoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Imagem
        //imgUrl = mostrarDados().get(2);
        //File url = new File(imgUrl);
        //BufferedImage imagem = ImageIO.read(url);
        ImageIcon icone = new ImageIcon(fotoPerfil());
        fotoLabel.setIcon(icone);

        fotoPanel.add(fotoLabel, BorderLayout.NORTH);

        //Painel do botão
        JPanel buttonPanel = new JPanel();
        BorderLayout buttonPanelLayout = new BorderLayout();
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanel.setPreferredSize(new Dimension(100, 50));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(35, 20, 60, 20));
        fotoPanel.add(buttonPanel, BorderLayout.NORTH);
        //lateralPanel.add(buttonPanel, BorderLayout.NORTH);

        //Botão de edição
        JButton editorButton = new JButton("Editar Perfil");
        editorButton.setBounds(10, 10, 100, 50);
        editorButton.setPreferredSize(new Dimension(100, 50));
        editorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MySquad menu = new MySquad();
                menu.ProfileEditor();
                setVisible(false);
                
            }
        });

        buttonPanel.add(editorButton, BorderLayout.CENTER);

        //Painel Central
        JPanel centralPanel = new JPanel();
        GridLayout centralLayout = new GridLayout(2, 1);
        centralPanel.setLayout(centralLayout);
        centralPanel.setPreferredSize(new Dimension(200, 600));

        //Painel para os nomes
        JPanel divisorPanel = new JPanel();
        GridLayout divisorPanelLayout = new GridLayout(5, 1);
        divisorPanel.setLayout(divisorPanelLayout);
        divisorPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 5));
        centralPanel.add(divisorPanel, BorderLayout.NORTH);


        //Painel para os nomes
        JPanel nomePanel = new JPanel();
        GridLayout nomePanelLayout = new GridLayout(2, 1);
        nomePanel.setLayout(nomePanelLayout);
        nomePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        divisorPanel.add(nomePanel, BorderLayout.NORTH);

        //Label do nome
        String nome = mostrarDados().get(0);
        JLabel nomeLabel = new JLabel(nome);
        nomeLabel.setPreferredSize(new Dimension(200, 60));
        nomeLabel.setFont(fontePrincipal);
        nomeLabel.setForeground(Color.BLACK);
        nomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nomeLabel.setVerticalAlignment(SwingConstants.TOP);
        nomePanel.add(nomeLabel, BorderLayout.NORTH);

        //Painel do nickname
        JPanel nickPanel = new JPanel();
        BorderLayout nickPanelLayout = new BorderLayout();
        nickPanel.setLayout(nickPanelLayout);
        nickPanel.setPreferredSize(new Dimension(100, 50));
        //nickPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 60, 20));
        nomePanel.add(nickPanel, BorderLayout.NORTH);

        //Label do nickname
        JLabel nickLabel = new JLabel(nick);
        nickLabel.setPreferredSize(new Dimension(200, 50));
        nickLabel.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 14));
        nickLabel.setForeground(Color.LIGHT_GRAY);
        nickLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nickLabel.setVerticalAlignment(SwingConstants.TOP);
        nickPanel.add(nickLabel, BorderLayout.NORTH);

        divisorPanel.add(new JLabel());
        divisorPanel.add(new JLabel());

        JLabel jogosLabel = new JLabel("JOGOS");
        jogosLabel.setFont(fontePrincipal);
        jogosLabel.setForeground(Color.BLACK);
        divisorPanel.add(jogosLabel);




        add(mainPanel, BorderLayout.NORTH);
        add(lateralPanel, BorderLayout.WEST);
        add(centralPanel, BorderLayout.CENTER);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    //JANELA DE AMIGOS
    public void friendsPanel() {

            setTitle("My Squad - Amigos");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);

            //Barra de Navegação
            JPanel navigationBar = new JPanel();
            GridLayout navigationbarlayout = new GridLayout(1, 6);
            navigationbarlayout.setHgap(20);
            navigationBar.setLayout(navigationbarlayout);
            navigationBar.setPreferredSize(new Dimension(800, 50));
            navigationBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            navigationBar.setBackground(fundoPrincipal);

            // Menu de navegação
            JPanel navigationMenu = new JPanel();
            GridLayout navigationlayout = new GridLayout(1, 4);
            navigationlayout.setHgap(5);
            navigationMenu.setLayout(navigationlayout);
            navigationMenu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
            navigationMenu.setBackground(fundoPrincipal);
            JButton homeButton = new JButton("Início");
            homeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                        MySquad menu = new MySquad();
                        menu.Principal();
                        setVisible(false);

                }
            });

            JButton gamesButton = new JButton("Jogos");

            JButton profileButton = new JButton("Perfil");
            profileButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                        MySquad menu = new MySquad();
                        menu.profile();
                        setVisible(false);

                }
            });

            JButton friendsButton = new JButton("Amigos");
            friendsButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                        MySquad menu = new MySquad();
                        menu.friendsPanel();
                        setVisible(false);

                }
            });

            navigationMenu.add(homeButton);
            navigationMenu.add(profileButton);
            navigationMenu.add(gamesButton);
            navigationMenu.add(friendsButton);
            navigationBar.add(navigationMenu, BorderLayout.WEST);

            //Barra de Pesquisa
            JPanel navigationPesquisa = new JPanel();
            GridLayout pesquisalayout = new GridLayout(1, 3);
            pesquisalayout.setHgap(5);
            navigationPesquisa.setLayout(pesquisalayout);
            navigationPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 5));
            navigationPesquisa.setBackground(fundoPrincipal);
            navigationBar.add(navigationPesquisa, BorderLayout.EAST);
            pesquisaField = new JTextField(20);
            JButton pesquisaButton = new JButton("Pesquisar");
            pesquisaButton.setPreferredSize(new Dimension(50, 30));
            pesquisaButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    realizarPesquisa();
                    
                }
            });
            navigationPesquisa.add(pesquisaField);
            navigationPesquisa.add(pesquisaButton);
    
            JPanel onlinePanel = new JPanel();
            onlinePanel.setBorder(BorderFactory.createTitledBorder("Amigos Online"));
            onlinePanel.setLayout(new BoxLayout(onlinePanel, BoxLayout.Y_AXIS));
    
            JPanel offlinePanel = new JPanel();
            offlinePanel.setBorder(BorderFactory.createTitledBorder("Amigos Offline"));
            offlinePanel.setLayout(new BoxLayout(offlinePanel, BoxLayout.Y_AXIS));
    
            JPanel gamesPanel = new JPanel();
            gamesPanel.setBorder(BorderFactory.createTitledBorder("Jogos em Andamento"));
            gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
    
            JButton addFriendButton = new JButton("Adicionar Amigo");
            addFriendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lógica para adicionar amigos
                }
            });
    
            JButton sendInviteButton = new JButton("Enviar Convite");
            sendInviteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lógica para enviar convites
                }
            });
    
            setLayout(new BorderLayout());
            add(navigationBar, BorderLayout.NORTH);
            add(onlinePanel, BorderLayout.WEST);
            add(offlinePanel, BorderLayout.CENTER);
            add(gamesPanel, BorderLayout.EAST);
    
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(addFriendButton);
            buttonPanel.add(sendInviteButton);
            add(buttonPanel, BorderLayout.SOUTH);
    
            setVisible(true);
            setLocationRelativeTo(null);
    }
    
    //JANELA DE EDIÇÃO DE PERFIL//
    public void ProfileEditor() {

        // Configurações iniciais da janela
        setTitle("My Squad - Editar Perfil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        //Barra de Navegação
        JPanel navigationBar = new JPanel();
        GridLayout navigationbarlayout = new GridLayout(1, 6);
        navigationbarlayout.setHgap(20);
        navigationBar.setLayout(navigationbarlayout);
        navigationBar.setPreferredSize(new Dimension(800, 50));
        navigationBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        navigationBar.setBackground(fundoPrincipal);

        // Menu de navegação
        JPanel navigationMenu = new JPanel();
        GridLayout navigationlayout = new GridLayout(1, 4);
        navigationlayout.setHgap(5);
        navigationMenu.setLayout(navigationlayout);
        navigationMenu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        navigationMenu.setBackground(fundoPrincipal);
        JButton homeButton = new JButton("Início");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    MySquad menu = new MySquad();
                    menu.Principal();
                    setVisible(false);

            }
        });

        JButton gamesButton = new JButton("Jogos");

        JButton profileButton = new JButton("Perfil");
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    MySquad menu = new MySquad();
                    menu.profile();
                    setVisible(false);

            }
        });

        JButton friendsButton = new JButton("Amigos");
        friendsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    MySquad menu = new MySquad();
                    menu.friendsPanel();
                    setVisible(false);

            }
        });

        navigationMenu.add(homeButton);
        navigationMenu.add(profileButton);
        navigationMenu.add(gamesButton);
        navigationMenu.add(friendsButton);
        navigationBar.add(navigationMenu, BorderLayout.WEST);

        //Barra de Pesquisa
        JPanel navigationPesquisa = new JPanel();
        GridLayout pesquisalayout = new GridLayout(1, 3);
        pesquisalayout.setHgap(5);
        navigationPesquisa.setLayout(pesquisalayout);
        navigationPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 5));
        navigationPesquisa.setBackground(fundoPrincipal);
        navigationBar.add(navigationPesquisa, BorderLayout.EAST);
        pesquisaField = new JTextField(20);
        JButton pesquisaButton = new JButton("Pesquisar");
        pesquisaButton.setPreferredSize(new Dimension(50, 30));
        pesquisaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                realizarPesquisa();
                
            }
        });
        navigationPesquisa.add(pesquisaField);
        navigationPesquisa.add(pesquisaButton);

        // Painel para organizar o formulário e botoões
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        //profilePanel.setBackground(fundoPrincipal);

        //Painel para organizar os componentes
        JPanel formPanel = new JPanel();
        GridLayout formLayout = new GridLayout(14, 3);
        formLayout.setVgap(5);
        formPanel.setLayout(formLayout);
        formPanel.setBorder(BorderFactory.createEmptyBorder(35, 250, 20, 250));
        //formPanel.setBackground(fundoPrincipal);
        profilePanel.add(formPanel, BorderLayout.CENTER);

        // Componente para adicionar foto (neste exemplo, apenas um rótulo)
        JLabel fotoLabel = new JLabel("Foto: ");
        fotoLabel.setFont(fontePrincipal);
        //fotoLabel.setForeground(textoPrincipal);
        JButton adicionarFotoButton = new JButton("Selecionar Foto");
        adicionarFotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JFileChooser fc = new JFileChooser();
                int res = fc.showOpenDialog(null);

                if (res == JFileChooser.APPROVE_OPTION) {
                    File arquivo = fc.getSelectedFile();
                    caminhoArquivo = arquivo.getAbsolutePath();
                    
                    try {

                        arquivoEscolhidoCaminho.setText(caminhoArquivo);

                    } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro!");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Voce nao selecionou nenhum arquivo.");
                }
        }
        });

        fotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(fotoLabel);
        formPanel.add(adicionarFotoButton);

        // Campo para editar o nickname
        JLabel nomeLabel = new JLabel("Nome de Exibição: ");
        nomeLabel.setFont(fontePrincipal);
        //nickLabel.setForeground(textoPrincipal);
        nomeField = new JTextField(20);
        nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(nomeLabel);
        formPanel.add(nomeField);

        // Campo para editar o país
        JLabel regiaoLabel = new JLabel("Região: ");
        regiaoLabel.setFont(fontePrincipal);
        //regiaoLabel.setForeground(textoPrincipal);
        cbregiao = new JComboBox<String>();
        cbregiao.addItem("Selecione uma região");
        cbregiao = mostrarRegiao();
        regiaoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(regiaoLabel);
        formPanel.add(cbregiao);

        //Label para o telefone


        // Campo para adicionar DDD
        JLabel DDDLabel = new JLabel("DDD: ");
        DDDLabel.setFont(fontePrincipal);
        DDDField = new JTextField(10);
        DDDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(DDDLabel);
        formPanel.add(DDDField);
        

        JLabel telefoneLabel = new JLabel("Telefone: ");
        telefoneLabel.setFont(fontePrincipal);
        telField = new JTextField(30);
        telefoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(telefoneLabel);
        formPanel.add(telField);

        JLabel arquivoEscolhidoLabel = new JLabel("Foto escolhida: ");
        arquivoEscolhidoLabel.setFont(fontePrincipal);
        arquivoEscolhidoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(arquivoEscolhidoLabel);

        arquivoEscolhidoCaminho = new JLabel();
        arquivoEscolhidoCaminho.setFont(fontePrincipal);
        arquivoEscolhidoCaminho.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(arquivoEscolhidoCaminho);

        // Botão de salvar
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                try {
                    
                    editarPerfil();
                    enviarImagem();
                    MySquad menu = new MySquad();
                    JOptionPane.showMessageDialog(null, "Editado com sucesso!", "MySquad - Cadastro", JOptionPane.INFORMATION_MESSAGE);
                    menu.Principal();
                    setVisible(false);

                } catch (SQLException erro) {

                JOptionPane.showMessageDialog(null, "MySquad.profileEditor.salvarButton: " + erro, "ERRO!", 0);

                }
                
            }
        });

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                try {
                    
                    MySquad menu = new MySquad();
                    menu.profile();
                    setVisible(false);

                } catch (Exception erro) {

                JOptionPane.showMessageDialog(null, "MySquad.profileEditor.cancelarButton: " + erro, "ERRO!", 0);

                }
                
            }
        });

        //Painel para o botão de salvar
        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(1, 2, 20, 7);
        buttonLayout.setHgap(15);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setPreferredSize(new Dimension(100, 35));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 200));
        buttonPanel.setOpaque(false);
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);
        getRootPane().setDefaultButton(salvarButton);
        profilePanel.add(buttonPanel, BorderLayout.SOUTH);
        profilePanel.add(navigationBar, BorderLayout.NORTH);

        

        // Adicione o painel à janela
        add(profilePanel);

        //Centraliza o painel principal
        setLocationRelativeTo(null);

        // Exiba a janela
        setVisible(true);
    }

    private void Logar() {

        try {

                String nomeUsuario = tfUsuario.getText().toString();
                String senha = pfSenha.getText().toString();

                Usuario objUsuario = new Usuario();
                objUsuario.setNm_usuario(nomeUsuario);
                objUsuario.setSenha(senha);

                UsuarioConexao objconexao = new UsuarioConexao();
                ResultSet rsconexao = objconexao.fazerLogin(objUsuario);

                if (rsconexao.next()) {

                    usuarioLogado = objconexao.usuarioLogado(objUsuario);
                    nick = mostrarDados().get(1);
                    MySquad menu = new MySquad();
                    menu.Principal();
                    setVisible(false);

                } else {

                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorreta!", "My Squad - Login", 0);
                    tfUsuario.setText("");
                    pfSenha.setText("");

                }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "MySquad.Logar: "+ erro, "ERRO!", 0);
        }

    }

    private boolean Cadastrar() {

        boolean sucesso = false;
        
        try {

            String novoUsuario = tfNovoUsuario.getText().toString();
            String novaSenha = pfNovaSenha.getText().toString();
            String email = tfEmail.getText().toString();

            Usuario objUsuarioVerifica = new Usuario();
            objUsuarioVerifica.setNm_usuario(novoUsuario);
            objUsuarioVerifica.setEmail(email);

            UsuarioConexao objVerifica = new UsuarioConexao();
            ResultSet rsUsuario = objVerifica.verificaUsuario(objUsuarioVerifica);
            ResultSet rsEmail = objVerifica.verificaEmail(objUsuarioVerifica);

            if(rsUsuario.next()) {

                JOptionPane.showMessageDialog(null, "Nome de Usuário já existe", "MySquad - Cadastro", 0);

            } else if (rsEmail.next()){

                JOptionPane.showMessageDialog(null, "Já existe uma conta cadastrada com o email informado!", "MySquad - Cadastro", 0);

            } else {

                if ((tfNovoUsuario.getText().isEmpty()) | (pfNovaSenha.getText().isEmpty()) | (tfEmail.getText().isEmpty())){

                    JOptionPane.showMessageDialog(null, "Preencha todos os campos, por favor!", "MySquad - Cadastro", 0);
                    
                } else {

                    Usuario objUsuario = new Usuario();
                    objUsuario.setNm_usuario(novoUsuario);
                    objUsuario.setNome(novoUsuario);
                    objUsuario.setSenha(novaSenha);
                    objUsuario.setEmail(email);

                    UsuarioConexao objconexao = new UsuarioConexao();
                    objconexao.fazerCadastro(objUsuario);
                    sucesso = true;

                }
            }

            
        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "MySquad.Cadastrar: "+ erro, "ERRO!", 0);

        }
        return sucesso;
        
    }

    private void realizarPesquisa() {

        try {

            String termoPesquisa = ('%' + pesquisaField.getText() + '%').toString();

            Usuario objUsuarioPesquisa = new Usuario();
            objUsuarioPesquisa.setNm_usuario(termoPesquisa);

            UsuarioConexao objPesquisa = new UsuarioConexao();

            model = new DefaultTableModel();
            model.setNumRows(0);

            ArrayList<Usuario> lista = objPesquisa.pesquisaUsuario(objUsuarioPesquisa);

            //System.out.println();

            for(int num = 0; num < lista.size(); num++){

                model.addRow(new Object[]{
                    lista.get(num).getNm_usuario(),
                    lista.get(num).getNome()
                });  
                
            }

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(null, "MySquad.realizarPesquisa: "+ erro, "ERRO!", 0);
            
        }
        
    }

    private void enviarImagem() {

        try {
            
            Path inputFile = Paths.get(caminhoArquivo);
            String caminho = "C:\\Users\\simpl\\OneDrive\\Desktop\\codigos\\POO-Java-FBV\\src\\images\\usuarios\\";
            Path outputFile = Paths.get(caminho + nick + ".jpg");

            Files.copy(inputFile, outputFile, StandardCopyOption.REPLACE_EXISTING);

         } catch (IOException erro) {
            JOptionPane.showMessageDialog(null, "MySquad.enviarImagem: " + erro, "ERRO!", 0);
         } catch (NullPointerException erro) {
            caminhoArquivo = "C:\\Users\\simpl\\OneDrive\\Desktop\\codigos\\POO-Java-FBV\\src\\images\\usuarios\\" + nick + ".jpg";
         }

    }

    private Image fotoPerfil() {

        try {

            String caminho = "C:\\Users\\simpl\\OneDrive\\Desktop\\codigos\\POO-Java-FBV\\src\\images\\usuarios\\" + nick + ".jpg";
            imgUrl = mostrarDados().get(5);
            File url = new File(imgUrl);
            imagem = ImageIO.read(url);

            if (imgUrl.matches(".*" + nick + ".*")) {
                imagem = ManipularImagem.setImagemDimensao(caminho, 100, 100);
            }             

            return imagem;

        } catch (IOException erro) {
            JOptionPane.showMessageDialog(null, "MySquad.fotoPerfil: " + erro, "ERRO!", 0);
            return null;
        }

    }

    private JComboBox<String> mostrarJogos(){

        try {

            UsuarioConexao objConexao = new UsuarioConexao();
            
            ResultSet rs = objConexao.mostraJogos();

            while(rs.next()) {

                //lista.add(rs.getString(1));
                cbjogos.addItem(rs.getString(1));

            }


        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "MySquad.mostrarJogos: " + erro, "ERRO!", 0);
        }

        return cbjogos;

    }

    private JComboBox<String> mostrarRegiao(){

        try {

            UsuarioConexao objConexao = new UsuarioConexao();
            
            ResultSet rs = objConexao.mostraRegiao();

            while(rs.next()) {

                //lista.add(rs.getString(1));
                cbregiao.addItem(rs.getString(1));

            }


        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "MySquad.mostrarRegiao: " + erro, "ERRO!", 0);
        }

        return cbregiao;

    }

    private void editarPerfil() throws SQLException {
        
        ArrayList<String> dadosAntigos = mostrarDados();
        String nomeUsuario = nomeField.getText().toString();
        if (nomeUsuario.isEmpty()) {
            nomeUsuario = dadosAntigos.get(0);
        }
        String nomeRegiao = (String) cbregiao.getSelectedItem();
        if (nomeRegiao.matches(".*região.*")) {
            nomeRegiao = dadosAntigos.get(4);
            if(nomeRegiao.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Favor informar uma região", "My Squad - Editar Perfil", 0);
            }
        }
        String ddd = DDDField.getText().toString();
        if (ddd.isEmpty()) {
            ddd = dadosAntigos.get(2);
        }
        String telefone = telField.getText().toString();
        if (telefone.isEmpty()) {
            telefone = dadosAntigos.get(3);
        }
        Usuario objUsuarioEdicao = new Usuario();
        objUsuarioEdicao.setNm_usuario(nomeUsuario);
        objUsuarioEdicao.setRegiao(nomeRegiao);
        objUsuarioEdicao.setDdd(ddd);
        objUsuarioEdicao.setTelefone(telefone);
        
        UsuarioConexao objConexao = new UsuarioConexao();
        //System.out.println(usuarioLogado);
        objConexao.editaPerfilUsuario(objUsuarioEdicao, usuarioLogado);

    }

    private ArrayList<String> mostrarDados(){

        try {

            UsuarioConexao objConexao = new UsuarioConexao();
            
            ResultSet rs = objConexao.mostraDados(usuarioLogado);

            listaDados = new ArrayList<>();

            while(rs.next()) {

                listaDados.add(rs.getString(1).toUpperCase());//usuario//
                listaDados.add(rs.getString(2));
                listaDados.add(rs.getString(3));
                listaDados.add(rs.getString(4));
                listaDados.add(rs.getString(5));
                listaDados.add("./src/images/user.png");

            }

            try {

                File dir = new File("./src/images/usuarios/");

                File[] matches = dir.listFiles(new FilenameFilter() {
                    
                    public boolean accept(File dir, String name) {
                        return name.matches(".*" + nick + ".*");
                    }

                });

                for (File f : matches) {

                    listaDados.remove(listaDados.get(5));
                    listaDados.add(f.toString());

                }

            } catch (NullPointerException erro) {
                return listaDados;
            }



        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "MySquad.mostrarDados: " + erro, "ERRO!", 0);
        }

        return listaDados;

    }

    
    public static void main(String[] args) {
        MySquad menuLogin = new MySquad();
        menuLogin.Login();
        }
}
